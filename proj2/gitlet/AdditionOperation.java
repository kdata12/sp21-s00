package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;
import static gitlet.Repository.*;

import static gitlet.Utils.join;

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

}
