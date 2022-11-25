package gitlet;

import com.sun.jdi.Value;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Date today = new Date();
        System.out.println(generateDate(false));
        System.out.println(today);
        System.out.println(generateDate(true));
    }

    public static String generateDate(boolean initial) {
        TimeZone tz = TimeZone.getTimeZone("MST");
        Calendar cal = Calendar.getInstance(tz);
        if (initial == true) {
            cal.setTimeInMillis(0);
        }
        DateFormat sdf = new SimpleDateFormat("EEE LLL d HH:mm:ss y Z");
        sdf.setTimeZone(tz);
        return sdf.format(cal.getTime());
    }


}
