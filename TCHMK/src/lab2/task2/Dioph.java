package lab2.task2;

import lab1.Euclid;
import lab1.GCD;

import java.math.BigInteger;
import java.util.Scanner;

import static java.math.BigInteger.ZERO;

public class Dioph {

    public static void main(String[] args) {
        System.out.println("Линейное диофантовое уравнение: ax + by = c");
        Scanner scanner = new Scanner(System.in);
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("b = ");
        BigInteger b = new BigInteger(scanner.next());
        System.out.print("c = ");
        BigInteger c = new BigInteger(scanner.next());
        System.out.println("-------------------");
        System.out.println("Решение:");
        long startTime = System.nanoTime();
        BigInteger[] result = diophant(a, b, c);
        long time = System.nanoTime() - startTime;
        if (result == null) {
            System.out.println("Решений нет");
        }
        else {
            System.out.println("x = " + result[0] + "\n" + "y = " + result[1]);
        }
        System.out.println("Время работы: " + time + " нс");

    }

    static BigInteger[] diophant(BigInteger a, BigInteger b, BigInteger c) {
        GCD gcd = Euclid.advanced(a, b.abs());
        BigInteger r = gcd.r;
        if (c.remainder(r).compareTo(ZERO) != 0) {
            return null;
        }
        c = c.divide(r);
        BigInteger x0 = gcd.x.multiply(c);
        BigInteger y0 = gcd.y.multiply(c);
        if (a.compareTo(ZERO) < 0) {
            x0 = x0.negate();
        }
        if (b.compareTo(ZERO) < 0) {
            y0 = y0.negate();
        }
        return new BigInteger[]{x0, y0};
    }
}
