package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Repository.*;
import static gitlet.Staging.*;
import static gitlet.Utils.*;
import static gitlet.Utils.plainFilenamesIn;

/** The Head commit is the commit node at the
 *  very edge of the commit tree. This class
 *  encapsulates the operations for manipulating
 *  the Head directory: retrieve information from
 *  the Head commit, and updating the Head commit.
 */
public class Head implements Serializable {
    private String headSHA1;

    public Head(String commitSHA1) {
        this.headSHA1 = commitSHA1;
    }

    public void save() {
        File headfile = join(HEAD, "Head");
        writeObject(headfile, this);
    }

    /**
     * Method for loading the Head Commit object from 'Head' file.
     * The commits are stored inside COMMITS_OBJECTS, we can find
     * the head commit using the stored SHA1.
     * @return Commit
     */
    public static Commit load() {
        Head headObject = Head.loadHead();
        String headSHA1 = headObject.getHeadSHA1();

        File commit = join(COMMITS_OBJECT, headSHA1);
        return readObject(commit, Commit.class);
    }

    public static Head loadHead () {
        File headfile = join(HEAD, "Head");
        return readObject(headfile, Head.class);
    }

    public String getHeadSHA1() {
        return headSHA1;
    }
}
