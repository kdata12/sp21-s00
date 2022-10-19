package deque;

public class LinkedListDeque <Item>{

    private class IntNode {
        public Item item;
        public IntNode next;

        public IntNode (Item i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    /** sentinel node */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null);
        size = 0;
    }

    public LinkedListDeque(Item x){
        sentinel = new IntNode(null, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(Item x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    public Item removeFirst() {
        return null;
    }

    public Item removeLast() {

        return null;
    }

    public int size() {
        return size;
    }

    /** get item at the given index */
    public Item get(int n) {
        int i = 0;
        IntNode p = sentinel;

        while(p.next != null && i != n ) {
            p = p.next;
            i += 1;
        }

        if (i == n) {
            return p.item;
        }
        return null;

    }


}

