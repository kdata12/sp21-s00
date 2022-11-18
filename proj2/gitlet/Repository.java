package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Staging.*;
import static gitlet.Utils.*;

/** Represent a gitlet repository. Includes many commands
 *  that controls and navigate your own personal file system!
 *  @author Phuc Vong
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
    /** creates staging area for staged for addition and removal*/
    public static final File STAGING_AREA = join(GITLET_DIR, "STAGING_AREA");

    /* Initialize a gitlet repository by creating a .gitlet file
    * and create an initial commit to be serialized and hashed
    * into directory HEAD */
    public static void init(){
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        Commit init = new Commit("initial commit", "00:00:00 UTC, Thursday, 1 January 1970",
                                    null);
        GITLET_DIR.mkdir();
        HEAD.mkdir();
        STAGING_AREA.mkdir();
        serializeAndHash(init, HEAD);
    }

    public static void commit(String message) {
        //TODO: Construct a commit tree that keeps track of current commit so I
        // can pass it the commit constructor

        //create commit object
        //Commit newCommit = new Commit(message, )

        //deserialize staging treemap to get file name and file content

        //add files in staging to commit object's files hashtable

        //adjust head and master pointer

        //serialize commit object
    }

    /* This function serializes a SERIALIZABLE object, then create a SHA-1 hash
    for the object, saves the SHA-1 hash to a file. */
    public static void serializeAndHash(Serializable object, File file) {
        byte[] bytes = serialize(object);
        String sha1Object = sha1(bytes);
        // file renamed to its SHA-1 hash.
        File objectFile = join(file, sha1Object);
        writeContents(objectFile, bytes);
    }
    public static void serializeAndHashFile(Serializable object, File file) {
        byte[] bytes = serialize(object);
        writeContents(file, bytes);
    }
    public static String giveSHA1(Serializable object) {
        byte[] bytes = serialize(object);
        return sha1(bytes);
    }


    /* Takes in a file name and add it to the stage addition
       treemap object in the Addition file */
    public static void add(String file_name) throws IOException {
        //checks if file is in current working directory
        if (!plainFilenamesIn(CWD).contains(file_name)) {
            System.out.println("File does not exist.");
            return;
        }
        //checks if stating area contains file
        if (!additionTree.containsKey(file_name)) {
            addHelper(file_name);
            return;
        }

        //checks if staging area has a duplicate
        if (!checkDuplicate(file_name)) {
            /* file have different content, remove old file, create new blob */
            removeFile(file_name);
            addHelper(file_name);
        }
    }

    /** Takes in a file name and add it to the stage addition
     treemap object in the Addition file */
    private static void addHelper(String file_name) throws IOException {
        Blobs blob = new Blobs(file_name);
        blob.saveBlob();
        additionTree.put(file_name, blob.getBlobSHA1());
        saveAdditionTree();
    }

}
