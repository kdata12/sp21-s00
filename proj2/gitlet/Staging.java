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

    /* serializes and hash removalTree to STAGE_FOR_REMOVAL folder */
    public static void saveRemovalTree(){
        writeObject(STAGE_FOR_ADDITION, removalTree);
    }

    public static TreeMap<String, String> loadAddition(){
        return readObject(STAGE_FOR_ADDITION, TreeMap.class);
    }

    public static TreeMap<String, String> loadRemoval(){
        return readObject(STAGE_FOR_REMOVAL, TreeMap.class);
    }

    public static void saveBothTrees() {
        saveAdditionTree();
        saveRemovalTree();
    }

    /** After the commit command, this function will clear
     *  the addition and removal treemap, then save it as an
     *  empty treemap.
     */
    public static void clearAndSave() {
        TreeMap<String, String> currAddition = Staging.loadAddition();
        TreeMap<String, String> currRemoval = Staging.loadRemoval();
        currAddition.clear();
        currRemoval.clear();
        Staging.saveBothTrees();
    }

    /** Check whether if a file in the staging area have the same content.
     *  Returns true if both file content are the same.
     */
    public static boolean checkDuplicate(String fileName) {
        byte[] fileContent = Utils.readContents(Utils.join(".", fileName));
        String fileSHA1 = sha1(fileContent);

        TreeMap<String, String> currAddition = Staging.loadAddition();
        TreeMap<String, String> currRemoval = Staging.loadRemoval();

        //checks if staged file's blob is the same as this file's blob
        if (currAddition.get(fileName).equals(fileSHA1)) {
            //return true if content are the same
            return true;
        }
        //return false if they have different content
        return false;
    }

    public static void removeFile(String fileName) {
        additionTree.remove(fileName);
    }

    /**
     * Check if staging area is empty. If empty return true,
     * otherwise return false.
     * @return
     */
    public static boolean isEmpty() {
        TreeMap<String, String> currAddition = Staging.loadAddition();
        TreeMap<String, String> currRemoval = Staging.loadRemoval();

        return currRemoval.size() == 0 && currAddition.size() == 0;
    }

}
