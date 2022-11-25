package gitlet;
import gitlet.Repository;

import java.io.File;
import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(args[1]);
                break;
            case "readinit":
                String hash = args[1];
                // prints out commit file message
                System.out.println(Commit.readMessage(hash));
                break;
            case "readtree":
                break;
            case "commit":
                Repository.commit(args);
                break;
            case "readSHA":
                System.out.println(Commit.readCommitSHA1(args[1]));
                break;
            case "readHEAD":
                System.out.println(Commit.readHEAD(args[1]));
                break;
        }
    }

    /**
     * Oh no, an error has occurred! Abort mission!
     * @param message error message to send to user
     */
    public static void exitWithError(String message) {
        System.out.println(message);
        System.exit(0);
    }
}
