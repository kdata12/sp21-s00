package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;
import static gitlet.Repository.*;

import static gitlet.Utils.join;

public class AdditionOperation implements Serializable{
    public static TreeMap<String, String> additionTree = new TreeMap<>();
    public static TreeMap<String, String> removalTree = new TreeMap<>();

    public static final File STAGE_FOR_ADDITION = join(Repository.STAGING_AREA, "STAGING_AREA");
    public static final File STAGE_FOR_REMOVAL = join(Repository.STAGING_AREA, "STAGING_AREA");

    /* serializes and hash additionTree to STAGE_FOR_ADDITION folder */
    public static void mapBlobToAdditionTree(){
        serializeAndHash(additionTree, STAGE_FOR_ADDITION);
    }

}
