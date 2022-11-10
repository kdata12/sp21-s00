package gitlet;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository implements Serializable {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The head folder containing the head commit */
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    public static void init(){
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        Commit initial = new Commit("initial commit", "00:00:00 UTC, Thursday, 1 January 1970", null);

        GITLET_DIR.mkdir();
        HEAD.mkdir();

        String init_commit_sha1 = sha1(initial);
        File init_commit_file = join(HEAD, init_commit_sha1);
        writeObject(init_commit_file, initial);
    }
}
