package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;
import static gitlet.Repository.*;

import static gitlet.Utils.join;
import static gitlet.Utils.sha1;

public class AdditionOperation implements Serializable{
    public static TreeMap<String, String> additionTree = new TreeMap<>();
    public static TreeMap<String, String> removalTree = new TreeMap<>();

    public static final File STAGE_FOR_ADDITION = join(Repository.STAGING_AREA, "Addition");
    public static final File STAGE_FOR_REMOVAL = join(Repository.STAGING_AREA, "Removal");

    /* serializes and hash additionTree to STAGE_FOR_ADDITION folder */
    public static void saveAdditionTree() throws IOException {
        startDirectory();
        serializeAndHashFile(additionTree, STAGE_FOR_ADDITION);
    }

    public static void startDirectory() throws IOException {
        if (!STAGE_FOR_ADDITION.exists() || !STAGE_FOR_REMOVAL.exists()) {
            STAGE_FOR_ADDITION.createNewFile();
            STAGE_FOR_REMOVAL.createNewFile();
        }
    }
    /** Check whether if a file in the staging area have the same content.
     *  Returns true if both file content are the same.
     */
    public static boolean checkDuplicate(String fileName) {
        if (!additionTree.containsKey(fileName)) {
            return false;
        }

        byte[] fileContent = Utils.readContents(Utils.join(".", fileName));
        String fileSHA1 = sha1(fileContent);

        //checks if staged file's SHA1 is the same as this file's SHA1
        if (additionTree.get(fileName).equals(fileSHA1)) {
            return true;
        }
        return false;
    }

    public static void removeOldVersion(String fileName) {
        // both file's content are different
        if (!checkDuplicate(fileName)) {
            additionTree.remove(fileName);
        }
    }




}
