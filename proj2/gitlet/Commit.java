package gitlet;

// TODO: any imports you need here
import static gitlet.Utils.*;

import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class

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
    private String date;
    private String parent;


    public Commit(String message, String date, String parent){
        this.message = message;
        this.date = date;
        this.parent = parent;
    }

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
}
