package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represents a gitlet commit object.
 *  Saves a snapshot of tracked files in the current commit and staging area
 *  so they can be restored at a later time, creating a new commit. The commit
 *  is said to be tracking the saved files.
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    /** The exact date of commit */
    private String date;

    /** This object parent's commit's SHA1 */
    private String parent;

    /** This object's SHA1 */
    private String SHA1;

    /** Hastable with String fileName and Blobs blob*/
    private Hashtable<String, Blobs> files;

    public Commit(String message, String date, String parent){
        this.message = message;
        this.date = date;
        this.parent = parent;
        this.files = new Hashtable<>();
        this.SHA1 = giveSHA1(this);
    }

    public void addFile(String fileName, Blobs blob) {
        this.files.put(fileName, blob);
    }



    /* GETTERS AND SETTERS */

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getParent() {
        return parent;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }



    /* COMMIT OBJECT OPERATIONS */

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
}
