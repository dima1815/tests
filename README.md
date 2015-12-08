package org.dstadler.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 7.0.0-SNAPSHOT
 */
public class MyGitTest {

    public static void main(String[] args) {

        // set GIT dir
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        repositoryBuilder.setMustExist(true);
        File directory = new File("C:/Projects/Latitude_GIT/swan/.git");
        repositoryBuilder.setGitDir(directory);

        Repository repository;
        try {
            repository = repositoryBuilder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Git git = new Git(repository)) {

            // initialize GIT repo
            System.out.println("repository = " + repository);

            // list refs
//            listRefs(repository);

            // select the branch we want
//            String targetBranchName = "refs/heads/release/Dec-2015";
            String targetBranchName = "refs/heads/feature/testing_deployment_script";
            Ref branchRef = repository.getRef(targetBranchName);
            String filePath = "TradeEventStore/java-apps/tes-parent/tes-app-parent/pom2.xml";

            findRevCommitForPath(git, branchRef, filePath);

            listFilesInLastCommit(git, branchRef);

            //
//            try (Git git = new Git(repository)) {
//                try {
//                    Iterable<RevCommit> logs = git.log().add(repository.resolve(targetBranchName)).call();
//                    int count = 0;
//                    for (RevCommit rev : logs) {
//                        System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
//                        count++;
//                    }
//                    System.out.println("Had " + count + " commits overall on branch - " + targetBranchName);
//                } catch (GitAPIException e) {
//                    throw new RuntimeException(e);
//                }
//            }

//            Ref head = repository.getRef("HEAD");
//
//            Ref leaf = head.getLeaf();
//            String name = head.getName();
//            ObjectId objectId = head.getObjectId();
//            ObjectId peeledObjectId = head.getPeeledObjectId();
//
//            System.out.println("head = " + head);
//
//            Ref target = head.getTarget();
//            System.out.println("target = " + target);


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static void findRevCommitForPath(Git git, Ref ref, String filePath) throws IOException, GitAPIException {

//        final RevWalk walk = new RevWalk(git.getRepository());
//        final RevCommit commit = walk.parseCommit(ref.getObjectId());
//        final RevTree commitTree = commit.getTree();
//
//        final TreeWalk treeWalk = new TreeWalk(git.getRepository());
//        treeWalk.addTree(commitTree);
//        treeWalk.setRecursive(true);
//        PathFilter pathFilter = PathFilter.create(filePath);
//        treeWalk.setFilter(pathFilter);

        LogCommand logCommand = git.log()
                .add(git.getRepository().resolve(Constants.HEAD))
                .addPath(filePath);
        for (RevCommit revCommit : logCommand.call()) {
            String shortMessage = revCommit.getShortMessage();
            System.out.println("revCommit = " + revCommit + " : message [" + shortMessage + "]");
        }

    }


    public static void listFilesInLastCommit(Git git, Ref ref) throws IOException, GitAPIException {
//        localRepoMock.commitRandomFile();
//        localRepoMock.commitRandomFile();
//        final String fileName = localRepoMock.commitRandomFile();
//        final Git git = localRepoMock.get();
//        final Map<String, Ref> allRefs = git.getRepository().getAllRefs();
        final RevWalk walk = new RevWalk(git.getRepository());
//        final RevCommit commit = walk.parseCommit(allRefs.get("HEAD").getObjectId());
        final RevCommit commit = walk.parseCommit(ref.getObjectId());
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

        while(treeWalk.next()) {
            String pathString = treeWalk.getPathString();
            System.out.println("pathString = " + pathString);
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
