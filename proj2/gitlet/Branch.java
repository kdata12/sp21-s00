package gitlet;

/** Represent a branch, a sequence of commit nodes
 *  that has diverged from a parent node. A branch
 *  can be seen as a pointer.
 */
public class Branch {
    private String name;

    private Commit head;

    public Branch(String name, Commit commit) {
        this.name = name;
        this.head = commit;

    }




}
