package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque <T> extends ArrayDeque <T>{
    private Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    /* returns the maximum element as governed
    by the previously given Comparator */
    public T max(){
        if (isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for(int i = 0; i < size(); i++) {
            if (comp.compare(get(i), maxItem) > 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    /* returns the maximum element in the deque as
    government by the parameter Comparator c */
    public T max(Comparator<T> c){
        if (isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for(int i = 0; i < size(); i++) {
            if (c.compare(get(i), maxItem) > 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    public static void main(String[] args) {
        Comparator<Integer> comp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1 < o2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        MaxArrayDeque mad = new MaxArrayDeque(comp);

        int n = 99;

        for (int i = n; i >= 0; i--) {
            mad.addFirst(i);
        }

        System.out.println(mad.max());
        System.out.println(mad.max(comp));

        Comparator<String> comp2 = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };

        MaxArrayDeque<String> mad2 = new MaxArrayDeque<>(comp2);

        mad2.addFirst("front");
        mad2.addLast("middle");
        mad2.addLast("back");


        System.out.println(mad2.max());
        System.out.println(mad2.max(comp2));

    }

}
