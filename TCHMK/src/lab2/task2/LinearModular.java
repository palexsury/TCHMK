package lab2.task2;

import lab1.Euclid;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Scanner;

import static java.math.BigInteger.ZERO;

public class LinearModular {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ax = b (mod m)");
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("b = ");
        BigInteger b = new BigInteger(scanner.next());
        System.out.print("m = ");
        BigInteger m = new BigInteger(scanner.next());
        System.out.println("-------------------");
        long startTime = System.nanoTime();
        BigInteger result = linearModular(a, b, m);
        long time = System.nanoTime() - startTime;
        if (result == null) {
            System.out.println("a и m не взаимно просты");
        }
        else {
            System.out.println("x = " + result);
        }
        System.out.println("Время работы: " + time + " нс");
    }

    public static BigInteger linearModular(BigInteger a, BigInteger b, BigInteger m) {
        if (Euclid.advanced(a, m).r.compareTo(BigInteger.ONE) != 0) {
            return null;
        }
        return Objects.requireNonNull(Dioph.diophant(a, m.negate(), b))[0].remainder(m);
    }

}
