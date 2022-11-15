package gitlet;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import static gitlet.Utils.*;

public class Blobs implements Serializable {
    /* creates a blob directory inside .gitlet */
    public static final File blob = Utils.join(Repository.GITLET_DIR, "blob");
    public String filename;
    public String fileContent;

    public Blobs(String name) {
        filename = name;
    }

    /** Reads the file content into byte, create a SHA-1
     * hash name from the file content and saves it to
     * instance variable fileContent */
    public void saveFile(File file) {
        byte[] file_to_byte = readContents(file);
        this.fileContent = sha1(file_to_byte);
    }

    /* serializes the blob object into file blob */
    public void serializeBlob(){
        Repository.serializeAndHash(this, blob);
    }

    public String getFilename() {
        return filename;
    }

    public String getFileContent() {
        return fileContent;
    }
}
