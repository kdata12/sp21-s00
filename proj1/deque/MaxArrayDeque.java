package deque;

import java.util.Comparator;

public class MaxArrayDeque <T> extends ArrayDeque <T> {
    public MaxArrayDeque(Comparator<T> c){
        super

    }

    /* returns the maximum element as governed
    by the previously given Comparator */
    public T max(){
        return null;
    }

    /* returns the maximum element in the deque as
    government by the parameter Comparator c */
    public T max(Comparator<T> c){
        if (size() == 0) {
            return null;
        }

        T maxValue = get(0);

        for (int i = 1; i < size(); i++) {
            if (get(i) > get(0)) {
                maxValue = get(i);
            }
        }
    }

    public class numComparator implements Comparator<T>{
        public int compare(T o1, T o2) {

        }
    }


}
