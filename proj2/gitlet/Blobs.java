package gitlet;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import static gitlet.Utils.*;

public class Blobs implements Serializable {
    /* creates a blob directory inside .gitlet */
    public static final File blob = Utils.join(Repository.GITLET_DIR, "blob");
    /**
     * Name of staged file
     */
    private String fileName;

    /**
     * Content of staged file
     */
    private byte[] fileContent;

    /**
     * SHA1 of blob object
     */
    private String blobSHA1;

    /**
     * This creates a blob for a file
     * @param filename
     */
    public Blobs(String filename) {
        this.fileName = filename;
        this.fileContent = Utils.readContents(Utils.join(".", filename));
        this.blobSHA1 = Utils.sha1(this.fileContent);
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
