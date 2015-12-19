package org.dstadler.jgit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 7.0.0-SNAPSHOT
 */
public class MyGitTest {

    private static final String REMOTE_REPO_URL = "https://stasyukd@stash.barcapint.com:8443/scm/fns_ccl/swan.git";
    private static final String LOCAL_REPO_DIR = "C:/Projects/Tests/jgit/test_repos/swan"; // absolute or relative?

    public static void main(String[] args) {

        /*
         * 0. Initialize repository
         */
        // check if local repo dir exists
        File localRepo = new File(LOCAL_REPO_DIR);
        if (localRepo.exists()) {
            // check that .git dir exists
            File gitDirectory = new File(localRepo, ".git");
            if (!gitDirectory.exists()) {
                System.out.println("No '.git' subdirectory was found for local repo path - " + localRepo.getAbsolutePath());
            }

        } else {
            // make directory
            boolean mkdirs = localRepo.mkdirs();
            if (!mkdirs) {
                throw new RuntimeException("Failed to create a GIT local repo directory for file path - " + localRepo.getAbsolutePath());
            }
        }

        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();


        repositoryBuilder.setMustExist(true);
        repositoryBuilder.setGitDir(gitDirectory);
        Repository repository;
        Ref branchRef;
        try {

            // 1. initialize GIT repo
            repository = repositoryBuilder.build();

            // 2. select the target branch
            //String targetBranchName = "refs/heads/release/Dec-2015";
            String targetBranchName = "refs/heads/feature/testing_deployment_script";
            branchRef = repository.getRef(targetBranchName);
            System.out.println("branchRef = " + branchRef);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Git git = new Git(repository)) {

            // 3. set file path of the target file
            String filePath = "TradeEventStore/java-apps/tes-parent/tes-app-parent/pom2.xml";
            System.out.println("filePath = " + filePath);

            // 4. find the file's latest revision commit id
            String fileRevCommit = findRevCommitForPath(git, branchRef, filePath);
            System.out.println("fileRevCommit = " + fileRevCommit);

            // 5. update the file
            File file = new File(filePath);
            int attempt = 2;
            FileUtils.writeLines(file, Collections.singletonList("test line - " + attempt), true);

            // 6. add the updated file to index
            AddCommand add = git.add();
            add.addFilepattern(filePath).call();

            // 7. commit the updated file
            CommitCommand commit = git.commit();
            commit.setMessage("testing prototype - " + attempt).call();

            // 8. push local commit to remote
            PushCommand push = git.push();
            String username = "stasyukd";
            String pass = "!Oksana1815";
            push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pass));
            push.call();

            listFilesInLastCommit(git, branchRef);


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static String findRevCommitForPath(Git git, Ref ref, String filePath) throws IOException, GitAPIException {

//        final RevWalk walk = new RevWalk(git.getRepository());
//        final RevCommit commit = walk.parseCommit(ref.getObjectId());
//        final RevTree commitTree = commit.getTree();
//
//        final TreeWalk treeWalk = new TreeWalk(git.getRepository());
//        treeWalk.addTree(commitTree);
//        treeWalk.setRecursive(true);
//        PathFilter pathFilter = PathFilter.create(filePath);
//        treeWalk.setFilter(pathFilter);

        String commitStrId = null;
        LogCommand logCommand = git.log().add(git.getRepository().resolve(ref.getName())).addPath(filePath);
        for (RevCommit revCommit : logCommand.call()) {
            ObjectId id = revCommit.getId();
            if (commitStrId == null) {
                commitStrId = id.getName();
            }
            String shortMessage = revCommit.getShortMessage();
//            System.out.println("revCommit = " + revCommit + " : message [" + shortMessage + "]");
        }
        return commitStrId;
    }


    public static void listFilesInLastCommit(Git git, Ref ref) throws IOException, GitAPIException {

//        localRepoMock.commitRandomFile();
//        localRepoMock.commitRandomFile();
//        final String fileName = localRepoMock.commitRandomFile();
//        final Git git = localRepoMock.get();
//        final Map<String, Ref> allRefs = git.getRepository().getAllRefs();

        final RevWalk walk = new RevWalk(git.getRepository());
//        final RevCommit commit = walk.parseCommit(allRefs.get("HEAD").getObjectId());
        ObjectId objectId = ref.getObjectId();
        String lastCommitId = objectId.getName();
        System.out.println("lastCommitId = " + lastCommitId);

        final RevCommit commit = walk.parseCommit(objectId);
        final RevTree tree = commit.getTree();
        final TreeWalk treeWalk = new TreeWalk(git.getRepository());
        treeWalk.addTree(tree);
        for (RevCommit parentCommit : commit.getParents()) {
            final ObjectId parentId = parentCommit.getId();
            final RevTree parentTree = walk.parseCommit(parentId).getTree();
            treeWalk.addTree(parentTree);
        }
        treeWalk.setFilter(TreeFilter.ANY_DIFF);
        treeWalk.setRecursive(true);

        System.out.println("Files in the last commit on branch '" + ref.getName() + "' were:");
        while (treeWalk.next()) {
            String pathString = treeWalk.getPathString();
            System.out.println(pathString);
        }
    }

    public static void listRefs(Git git) {
        Repository repository = git.getRepository();
        Map<String, Ref> allRefs = repository.getAllRefs();
        for (String ref : allRefs.keySet()) {
            System.out.println("ref = " + ref);
        }
    }

}
