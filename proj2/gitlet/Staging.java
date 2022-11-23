package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Staging implements Serializable{
    public static TreeMap<String, String> additionTree = new TreeMap<>();
    public static TreeMap<String, String> removalTree = new TreeMap<>();

    /** Set up the staging area with empty addition and removal
     * treemap.
     */
    public static void setUpStaging() {
        writeObject(STAGE_FOR_ADDITION, additionTree);
        writeObject(STAGE_FOR_REMOVAL, removalTree);
    }

    /* serializes and hash additionTree to STAGE_FOR_ADDITION folder */
    public static void saveAdditionTree() {
        writeObject(STAGE_FOR_ADDITION, additionTree);
    }

    public static void saveRemovalTree(){
        writeObject(STAGE_FOR_ADDITION, removalTree);
    }

    public static TreeMap<String, String> loadAddition(){
        return readObject(STAGE_FOR_ADDITION, TreeMap.class);
    }

    public static TreeMap<String, String> loadRemoval(){
        return readObject(STAGE_FOR_REMOVAL, TreeMap.class);
    }

    /** Check whether if a file in the staging area have the same content.
     *  Returns true if both file content are the same.
     */
    public static boolean checkDuplicate(String fileName) {
        byte[] fileContent = Utils.readContents(Utils.join(".", fileName));
        String fileSHA1 = sha1(fileContent);

        //checks if staged file's SHA1 is the same as this file's SHA1
        if (additionTree.get(fileName).equals(fileSHA1)) {
            return true;
        }
        return false;
    }

    public static void removeFile(String fileName) {
        additionTree.remove(fileName);
    }

}
