package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.AdditionOperation.*;
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

    /* This function serializes a commit object, then create a SHA-1 hash
    for the object, saves the SHA-1 hash to a file. */
    public static void serializeAndHash(Serializable object, File file) {
        byte[] bytes = serialize(object);
        String sha1Object = sha1(bytes);
        // file renamed to its SHA-1 hash.
        File objectFile = join(file, sha1Object);
        writeContents(objectFile, bytes);
    }

    /** 
     * This function deserializes the commit object then
     * returns the commit object's message
     * @param hash -> the SHA-1 hash "name" of the file
     * @return the commit object message
     */
    public static String readMessage(String hash) {
        return readCommitMessage(commitFromFile(hash));
    }

    private static Commit commitFromFile(String hash) {
        File test = join(HEAD, hash);
        return readObject(test, Commit.class);
    }

    private static String readCommitMessage(Commit commit){
        return commit.getMessage();
    }

    /* Takes in a file name and add it to the stage addition
       treemap object in the STAGE_FOR_ADDITION directory */
    public static void add(String file_name) {
        blobMaintanence addOperation = new blobMaintanence();
        addOperation.createBlob(file_name, STAGE_FOR_ADDITION);
        mapBlobToAdditionTree();
    }
    public static class blobMaintanence implements Serializable {
        public TreeMap<String, String> blobMapping = new TreeMap<>();

        public void createBlob(String file_name, File file){
            Blobs blob_object = new Blobs(file_name);
            blob_object.saveFile(file);
            blob_object.serializeBlob();

            // puts "hello.txt" = blob_object's SHA-1 hash to treemap
            blobMapping.put(file_name, sha1(blob_object));
            AdditionOperation.additionTree.put(file_name, sha1(blob_object));
        }

    }


}
