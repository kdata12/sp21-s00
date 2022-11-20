package gitlet;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.Date;
import java.util.TreeMap;

public class playground {
    public static void main(String[] args) {
        TreeMap<String, Integer> fun = new TreeMap<>();
        fun.put("dog", 5);
        fun.put("cat", 2);
        fun.put("otter", 3);
        fun.put("cow", 10);

        TreeMap<String, Integer> update = new TreeMap<>();
        update.put("dog", 7);


        fun.putAll(update);

        System.out.println(fun);

    }
}
