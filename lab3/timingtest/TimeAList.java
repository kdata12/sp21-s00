package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }

    }
    public static void timeAListConstruction() {

        AList<Integer> testAList = new AList<>();

        AList<Double> timesTable = new AList<>();

        AList<Integer> opTable = new AList<>();

        AList<Integer> Random = new AList<>();
        int n = 1000;

        Stopwatch timeTestList = new Stopwatch();
        for (int i = 0; i < 100_000_000; i++) {

            Random.addLast(i);
            double timeInSeconds = timeTestList.elapsedTime();

            if (i == n) {
                testAList.addLast(n);
                timesTable.addLast(timeInSeconds);
                opTable.addLast(i);
                n = n * 2;
            }

        }


        printTimingTable(testAList, timesTable, opTable);
    }

    public static void main(String[] args) {
        timeAListConstruction();

    }


}
