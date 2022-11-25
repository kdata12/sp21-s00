package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Staging implements Serializable{
    public static TreeMap<String, String> additionTree = new TreeMap<>();
    public static HashSet<String> removalTree = new HashSet<>();

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
    public static void saveRemovalHashSet(){
        writeObject(STAGE_FOR_ADDITION, removalTree);
    }

    public static TreeMap<String, String> loadAddition(){
        return readObject(STAGE_FOR_ADDITION, TreeMap.class);
    }

    public static HashSet<String> loadRemoval(){
        return readObject(STAGE_FOR_REMOVAL, HashSet.class);
    }

    public static void saveAll() {
        saveAdditionTree();
        saveRemovalHashSet();
    }

    /** After the commit command, this function will clear
     *  the addition and removal treemap, then save it as an
     *  empty treemap.
     */
    public static void clearAndSave() {
        TreeMap<String, String> currAddition = Staging.loadAddition();
        HashSet<String> currRemoval = Staging.loadRemoval();
        currAddition.clear();
        currRemoval.clear();
        Staging.saveAll();
    }

    /** Check whether if a file in the staging area have the same content.
     *  Returns true if both file content are the same.
     */
    public static boolean checkDuplicate(String fileName) {
        byte[] fileContent = Utils.readContents(Utils.join(".", fileName));
        String fileSHA1 = sha1(fileContent);

        TreeMap<String, String> currAddition = Staging.loadAddition();

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
        HashSet<String> currRemoval = Staging.loadRemoval();

        return currRemoval.size() == 0 && currAddition.size() == 0;
    }

    /** Updates the current snapshot using staging files mapping if
     * there were any changes / addition / removal made.
     */
    public static TreeMap<String, String> updateSnapshot() {

        Commit headCommit = Head.load();
        TreeMap<String, String> snapshot = headCommit.getSnapshot();
        TreeMap<String, String> stagedForAdditionFiles = Staging.loadAddition();
        HashSet<String> stagedForRemovalFiles = Staging.loadRemoval();
        /* - Add files to head commit snapshot from the staged for addition treemap.
         * - If file have: same name, same content --> mapping stays the same
         * - If file have: same name, different content --> name (key) will be same, content (value) will be updated
         */
        snapshot.putAll(stagedForAdditionFiles);

        /* - Remove file from the head commit snapshot from the staged for removal treemap.
         * - If file have: same name --> file will be removed from head commit snapshot
         */
        stagedForRemovalFiles.forEach((key) -> {
            if (snapshot.containsKey(key)) {
                snapshot.remove(key);
            }

        });
        return snapshot;
    }

    /**
     * Removes a file from the staged for addition treemap.
     * @param filename the file to be removed
     */
    public static void unstage(String filename){
        TreeMap<String, String> stagedForAdditionFiles = Staging.loadAddition();
        stagedForAdditionFiles.remove(filename);
    }

}
