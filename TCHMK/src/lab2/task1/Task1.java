package lab2.task1;

import java.math.BigInteger;
import java.util.Scanner;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class Task1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("b = ");
        BigInteger b = new BigInteger(scanner.next());
        System.out.println("-------------------");
        System.out.println("Цепная дробь:");
        long startTime = System.nanoTime();
        System.out.println(task1(a, b));
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");
    }

    public static String task1(BigInteger a, BigInteger b) {
        StringBuilder result = new StringBuilder(a + " / " + b + " = [");
        if (a.compareTo(ZERO) < 0) {
            result.append(a.divide(b).subtract(ONE));
            a = a.negate();
            BigInteger t = a;
            a = b;
            b = b.subtract(t.remainder(b));
        }
        else {
            result.append(a.divide(b));
            BigInteger t = a;
            a = b;
            b = t.remainder(b);
        }
        while(b.compareTo(ZERO) != 0) {
            result.append("; ").append(a.divide(b));
            BigInteger t = a;
            a = b;
            b = t.remainder(b);
        }
        result.append("]");
        return result.toString();
    }

}
