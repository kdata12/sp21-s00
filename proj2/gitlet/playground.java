package gitlet;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.Date;
import java.util.TreeMap;

public class playground {

    public static String world;
    public String name;

    public playground(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void setWorld(String world) {
        playground.world = world;
    }

    public static void main(String[] args) {

    }

}
