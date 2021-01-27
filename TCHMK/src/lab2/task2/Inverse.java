package lab2.task2;

import lab1.Euclid;
import lab1.GCD;

import java.math.BigInteger;
import java.util.Scanner;

import static java.math.BigInteger.ONE;

public class Inverse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("a^(-1) mod m");
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("m = ");
        BigInteger m = new BigInteger(scanner.next());
        System.out.println("-------------------");
        long startTime = System.nanoTime();
        BigInteger result = inverse(a, m);
        long time = System.nanoTime() - startTime;
        if (result == null) {
            System.out.println("a и m не взаимно просты");
        }
        else {
            System.out.println("x = " + result);
        }
        System.out.println("Время работы: " + time + " нс");
    }

    public static BigInteger inverse(BigInteger a, BigInteger m) {
        GCD GCD = Euclid.advanced(a, m);
        if (GCD.r.compareTo(ONE) != 0) {
            return null;
        }
        return GCD.x.add(m).remainder(m);
    }
}
