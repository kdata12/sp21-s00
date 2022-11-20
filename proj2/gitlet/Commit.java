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
    private String[] parent;

    private String dateString;

    /** This object's SHA1 */
    private String SHA1;

    /** HashMap with String SHA 1 of staged files and
     *  SHA-1 of blob */
    private TreeMap<String, String> snapshot;

    public static String headSHA1;


    /** COMMIT OBJECT CONSTRUCTOR */

    /** Only for init command */
    public Commit(String message, String dateString, String parent){
        this.message = message;
        this.date = new Date();
        this.dateString = dateString;
        this.parent[0] = parent;
        this.snapshot = new TreeMap<>();
        this.SHA1 = giveSHA1(this);
    }

    /**
     * Instantiate a commit node
     * @param message Commit message
     * @param parent Parent of Commit
     */
    public Commit(String message, String parent, TreeMap<String, String> snap){
        this.message = message;
        this.date = new Date();
        this.dateString = this.date.toString();
        this.parent[0] = parent;
        this.snapshot = snap;
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
        return this.parent[0];
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setParent(String parent) {
        this.parent[0] = parent;
    }

    /** To be used after commiting, this will for setting
     *  the class's Head commit SHA1.
     * @param SHA1
     */
    public static void setHeadSHA1(String SHA1) {
        headSHA1 = SHA1;
    }

    public static String getHeadSHA1() {
        return Commit.headSHA1;
    }

    public String getSHA1() {
        return this.SHA1;
    }

    public TreeMap<String, String> getSnapshot() {
        return this.snapshot;
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
    public void addFile(String fileName, String blobSHA1) {
        this.snapshot.put(fileName, blobSHA1);
    }

    /** Load a commit that was serialized
     * @param sha1 commit SHA-1 hash
     */
    public static Commit load(String sha1) {
        File commitFile = join(HEAD, sha1);
        return readObject(commitFile, Commit.class);
    }



}
