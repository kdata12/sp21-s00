package gitlet;

import java.io.File;
import gitlet.Utils.*;

/**
 * Initialize a distributed version control system in CWD.
 * Creates a new .git folder
 */
public class commands {
    private static String date = "00:00:00 UTC, Thursday, 1 January 1970";
    static final File gitlet = new File(".gitlet");
    static final File cwd = new File(System.getProperty("user.dir"));


    /**
     * Create an initial commit and a .gitlet folder.
     */
    public static void init(){
        Commit initial = new Commit("initial commit", date, null);
        gitlet.mkdir();
    }


}
