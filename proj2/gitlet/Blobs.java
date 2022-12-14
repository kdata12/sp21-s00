package gitlet;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.TreeMap;

import static gitlet.Utils.*;

public class Blobs implements Serializable {

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
     * This creates a blob for a file.
     */
    public Blobs(String filename) {
        this.fileName = filename;
        this.fileContent = Utils.readContents(Utils.join(".", filename));
        // file's SHA1, NOT blob's SHA1
        this.blobSHA1 = Utils.sha1(this.fileContent);
    }

    /**
     * This creates a blob object constructor.
     */
    public Blobs(String filename, byte[] fileContent, String blobSHA1) {
        this.blobSHA1 = blobSHA1;
        this.fileContent = fileContent;
        this.fileName = filename;
    }

    /* save blob object. Uses SHA1 as file name
    ex. hello.saveBlob()
     */
    public void saveBlob() throws java.io.IOException{
        File blobFile = new File(Repository.BLOB_FOLDER, this.getBlobSHA1());
        blobFile.createNewFile();
        writeObject(blobFile, this);
    }

    /*
     returns Blobs object from a file
     */
    public static Blobs retrieve(File blobFile) {
        return Utils.readObject(blobFile, Blobs.class);
    }

    public String getFilename() {
        return this.fileName;
    }

    public byte[] getFileContent() {
        return this.fileContent;
    }

    public String getBlobSHA1() {
        return this.blobSHA1;
    }
}
