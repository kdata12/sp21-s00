package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class gitletTreeMap implements Serializable {
    public static TreeMap<String, String> add;
    public TreeMap<String, String> remove;

    public gitletTreeMap() {
        add = new TreeMap<>();
        remove = new TreeMap<>();
    }

    public static void addFile(File file, String file_name){

    }
}
