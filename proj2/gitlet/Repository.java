package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
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

    /** directory that holds commit and blob objects */
    public static final File OBJECTS_FOLDER = join(GITLET_DIR, "objects");

    /** directory for commit object */
    public static final File COMMITS_OBJECT = join(OBJECTS_FOLDER, "commits");

    /** directory for storing the most recent commit hash */
    public static final File REFS_FOLDER = join(OBJECTS_FOLDER, "refs");

    /** file for storing treemap data structure that tracks file:blob */
    public static final File STAGE_FOR_ADDITION = join(STAGING_AREA, "Addition");

    /** file for storing treemap data structure that tracks file name to be removed*/
    public static final File STAGE_FOR_REMOVAL = join(STAGING_AREA, "Removal");

    public static final File BLOB_FOLDER = Utils.join(OBJECTS_FOLDER, "blob");


    public static void setupPersistence() throws IOException {
        GITLET_DIR.mkdir();
        HEAD.mkdir();
        STAGING_AREA.mkdir();
        OBJECTS_FOLDER.mkdir();
        COMMITS_OBJECT.mkdir();
        REFS_FOLDER.mkdir();
        STAGE_FOR_ADDITION.createNewFile();
        STAGE_FOR_REMOVAL.createNewFile();
        BLOB_FOLDER.mkdir();
    }

    /* Initialize a gitlet repository by creating a .gitlet file
    * and create an initial commit to be serialized and hashed
    * into directory HEAD */
    public static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        setupPersistence();
        setUpStaging();

        //TODO: Create a sentinel commit for initial commit???
        Commit init = new Commit("initial commit", "00:00:00 UTC, Thursday, 1 January 1970");

        //new head object
        Head headobject = new Head(init.getSHA1());
        //serialize head object
        headobject.save();
        //save commit to commit object directory
        saveCommit(init, COMMITS_OBJECT);
    }

    /* Takes in a file name and add it to the stage addition
   treemap object in the Addition file */
    public static void add(String file_name) throws IOException {
        //checks if file is in current working directory
        if (!plainFilenamesIn(CWD).contains(file_name)) {
            Main.exitWithError("File does not exist.");
        }
        File newFile = join(".", file_name);
        byte[] newFileContent = readContents(newFile);
        String newFileSHA1 = sha1(newFileContent);

        /* checks if file content matches the Head commit file version
         */
        Commit commit = Head.load();
        if (commit.getSnapshot().containsKey("file_name")) {
            if (commit.getSnapshot().get(file_name).equals(newFileSHA1)) {
                Main.exitWithError("File version is same as current commit");
            }
        }

        TreeMap<String, String> currAddition = Staging.loadAddition();

        /*checks if staging area contains file, this runs
        if the file is new! */
        if (!currAddition.containsKey(file_name)) {
            addHelper(file_name);
            return;
        }

        /* checks if staging area has a duplicate, this runs
        if there is a duplicate file name, and it will check if
        file content needs to be updated. */
        if (!checkDuplicate(file_name)) {
            /* file have different content, remove old file, create new blob */
            removeFile(file_name);
            addHelper(file_name);
        }
    }

    /**
     * Saves a snapshot of tracked files in the current commit and staging area so they can be restored
     * at a later time, creating a new commit. The commit is said to be tracking the saved files. By default,
     * each commit’s snapshot of files will be exactly the same as its parent commit’s snapshot of files; it
     * will keep versions of files exactly as they are, and not update them. A commit will only update the
     * contents of files it is tracking that have been staged for addition at the time of commit, in which
     * case the commit will now include the version of the file that was staged instead of the version it got from its parent.
     * @param args commit message
     * @throws IOException
     */
    public static void commit(String[] args) throws IOException {
        String message = args[1];

        if (Staging.isEmpty()) {
            Main.exitWithError("No changes added to the commit.");
        }

        if (message.isEmpty() || message.isBlank()) {
            Main.exitWithError("Please enter a commit message.");
        }

        Commit headCommit = Head.load();
        String headCommitSHA1 = headCommit.getSHA1();
        TreeMap<String, String> snapshot = Staging.updateSnapshot();

        Commit newCommit = new Commit(message, headCommitSHA1, snapshot);
        //new head object
        Head headobject = new Head(newCommit.getSHA1());
        //serialize head object
        headobject.save();
        //save commit to commit object directory
        saveCommit(newCommit, COMMITS_OBJECT);
        //clear staging area
        Staging.clearAndSave();
    }

    /**
     * Unstage the file if it is currently staged for addition.
     * If the file is tracked in the current commit, stage it
     * for removal and remove the file from the working directory
     * if the user has not already done so (do not remove it unless
     * it is tracked in the current commit).
     * @param filename name of file to be removed
     */
    public static void remove(String filename) {
        Commit headCommit = Head.load();
        TreeMap<String, String> snapshot = headCommit.getSnapshot();
        TreeMap<String, String> stagedForAdditionFiles = Staging.loadAddition();
        HashSet<String> stagedForRemovalFiles = Staging.loadRemoval();

        if (stagedForAdditionFiles.containsKey(filename)) {
            Staging.unstage(filename);
        } else if (snapshot.containsKey(filename)) {
            stagedForRemovalFiles.add(filename);
            File fileToBeRemoved = join(CWD, filename);
            Utils.restrictedDelete(fileToBeRemoved);
        } else {
            Main.exitWithError("No reason to remove the file.");
        }

        Staging.saveAll();
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

    public static void saveCommit(Commit commit, File file) {
        File objectFile = join(file, commit.getSHA1());
        writeObject(objectFile, commit);
    }

    public static String giveSHA1(Serializable object) {
        byte[] bytes = serialize(object);
        return sha1(bytes);
    }


    /** Takes in a file name and add it to the stage addition
     treemap object in the Addition file */
    private static void addHelper(String file_name) throws IOException {
        Blobs blob = new Blobs(file_name);
        blob.saveBlob();

        // save file:blob mapping
        TreeMap<String, String> stagedForAdditionFiles = Staging.loadAddition();
        stagedForAdditionFiles.put(file_name, blob.getBlobSHA1());
        Staging.saveAdditionTree();
    }

    /**
     * Starting at the current head commit, display information about each commit backwards along
     * the commit tree until the initial commit, following the first parent commit links, ignoring
     * any second parents found in merge commits.
     */
    public static void log() {


    }

}
