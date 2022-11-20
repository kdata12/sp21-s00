package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Repository.HEAD;
import static gitlet.Staging.*;
import static gitlet.Utils.*;

/** The Head commit is the commit node at the
 *  very edge of the commit tree. This class
 *  encapsulates the operations for manipulating
 *  the Head directory: retrieve information from
 *  the Head commit, and updating the Head commit.
 */
public class Head implements Serializable{

    public void saveHead(){

    }

    /** Load the commit object from head directory
     * ex. Head.load() -> return Commit
     */
    public static Commit load() {
        File commit = join(HEAD, Commit.getHeadSHA1());
        return readObject(commit, Commit.class);
    }

    public static void updateHead() {

    }



}
