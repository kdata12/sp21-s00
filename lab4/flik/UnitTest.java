package flik;

import org.junit.Test;
import static org.junit.Assert.*;


public class UnitTest {
    @Test
    public void test1() {
        int j = 0;
        for(int i = 0; i < 500; i++) {
            assertTrue(Flik.isSameNumber(i,j));
            j++;
        }
    }
}
