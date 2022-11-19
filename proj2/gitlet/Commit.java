package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.spi.DateFormatProvider;
import java.util.*;
import java.util.Date;

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
    private Date date;

    /** This object parent's commit's SHA1 */
    private String parent;

    private String dateString;

    /** This object's SHA1 */
    private String SHA1;

    /** Hastable with String fileName and Blobs blob*/
    private Hashtable<String, Blobs> files;



    /** COMMIT OBJECT CONSTRUCTOR */

    /**
     * Instantiate a commit node
     * @param message
     * @param dateString
     * @param parent
     */
    public Commit(String message, String dateString, String parent){
        this.message = message;
        this.date = new Date();
        this.dateString = dateString;
        this.parent = parent;
        this.files = new Hashtable<>();
        this.SHA1 = giveSHA1(this);
    }

    public Commit(String message, String parent){
        this.message = message;
        this.date = new Date();
        this.dateString = this.date.toString();
        this.parent = parent;
        this.files = new Hashtable<>();
        this.SHA1 = giveSHA1(this);
    }



    /* GETTERS AND SETTERS */

    public String getMessage() {
        return this.message;
    }

    public Date getDate() {
        return this.date;
    }

    public String getParent() {
        return this.parent;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }



    /* COMMIT OBJECT OPERATIONS */

    /** Reads commit message */
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

    /** Adds the file and the associated blob object to the
     * commit object hashtable.
     */
    public void addFile(String fileName, Blobs blob) {
        this.files.put(fileName, blob);
    }

    /** Load a commit that was serialized
     * @param sha1 commit SHA-1 hash
     */
    public static Commit load(String sha1) {
        File commitFile = join(HEAD, sha1);
        return readObject(commitFile, Commit.class);
    }

}
