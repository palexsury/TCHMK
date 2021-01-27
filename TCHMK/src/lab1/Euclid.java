package lab1;

import java.math.BigInteger;
import java.util.Scanner;

public class Euclid {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("b = ");
        BigInteger b = new BigInteger(scanner.next());
        System.out.println("-------------------");
        System.out.println("Расширенный:");
        long startTime = System.nanoTime();
        System.out.println("НОД(a, b) = " + Euclid.advanced(a, b));
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");
    }

    public static GCD advanced(BigInteger a, BigInteger b) {
        BigInteger q = a.divide(b);
        BigInteger r = a.remainder(b);
        BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO;
        BigInteger y0 = BigInteger.ZERO, y1 = BigInteger.ONE;
        while (r.compareTo(BigInteger.ZERO) > 0) {
            BigInteger x = x1, y = y1;
            x1 = x0.subtract(q.multiply(x1));
            x0 = x;
            y1 = y0.subtract(q.multiply(y1));
            y0 = y;
            a = b;
            b = r;
            q = a.divide(b);
            r = a.remainder(b);
        }
        return new GCD(b, x1, y1);
    }

    public static int gcd(int a, int b) {
        return advanced(BigInteger.valueOf(a), BigInteger.valueOf(b)).r.intValue();
    }

    public static int binary(int a, int b) {
        int g = 1;
        while (a % 2 == 0 && b % 2 == 0) {
            a /= 2;
            b /= 2;
            g *= 2;
        }
        int u = a;
        int v = b;
        while (u != 0) {
            while (u % 2 == 0) {
                u /= 2;
            }
            while (v % 2 == 0) {
                v /= 2;
            }
            if (u >= v) {
                u = u - v;
            }
            else {
                v = v - u;
            }
        }
        return g * v;
    }


}
