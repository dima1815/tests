TEST 12

package org.dstadler.jgit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.Map;

/**
 */
public class MyTest {

//    private static final String REMOTE_URL = "https://pristrom@bitbucket.org/pristrom/jbehave-for-jira-example-project.git";
    private static final String REMOTE_URL = "https://github.com/dima1815/tests";

    public static void main(String[] args) {

        File repoDir = new File("C:/Projects/jgit-cookbook/test_repos/my_test_repo");
        File gitDir = new File(repoDir, ".git");
        Repository repo = null;

        try {

            if (gitDir.exists()) {
                // open
                repo = new FileRepositoryBuilder()
                        .setGitDir(gitDir)
                        .build();
                System.out.println("Repository was opened at - repoDir = " + repoDir);

            } else {
                // create
                Git result = Git.cloneRepository()
                        .setURI(REMOTE_URL)
                        .setDirectory(repoDir)
//                        .setBare(true)
//                        .setNoCheckout(true)
                        .call();
//                repo = FileRepositoryBuilder.create(repoDir);
//                repo.create();
                System.out.println("Repository was cloned into - repoDir = " + repoDir);
            }

            Map<String, Ref> allRefs = repo.getAllRefs();

            Ref master = repo.getRef("master");
            ObjectId masterTip = master.getObjectId();
            ObjectLoader loader = repo.open(masterTip);
            loader.copyTo(System.out);

            // add file
            Git git = new Git(repo);
            File myfile = new File(repoDir, "TestAddedFile.txt");
            myfile.createNewFile();
            FileUtils.write(myfile, "Some sample file contents 3");
            git.add().addFilepattern("TestAddedFile.txt").call();
            // commit
            git.commit().setMessage("Added a new file to repo 3").call();
            // push
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("d.stasyuk@gmail.com", "dimitri1815"))
                    .call();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Program finished");
    }

}
