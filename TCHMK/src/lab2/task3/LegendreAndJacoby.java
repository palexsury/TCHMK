package lab2.task3;

import lab1.Euclid;

import java.math.BigInteger;
import java.util.Scanner;

import static java.math.BigInteger.*;

public class LegendreAndJacoby {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(a/n):");
        System.out.print("a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("n = ");
        BigInteger n = new BigInteger(scanner.next());
        System.out.println("--------------");
        if (!n.testBit(0)) {
            System.out.println("n - четное");
        }
        else {
            long startTime = System.nanoTime();
            int result = jacoby(a, n);
            long time = System.nanoTime() - startTime;
            System.out.println("Символ Якоби (a/n) = " + result);
            System.out.println("Время работы: " + time + " нс");
        }
        System.out.println("--------------");
        if (!n.isProbablePrime(10000)) {
            System.out.println("n - не простое");
        }
        else {
            long startTime = System.nanoTime();
            int result = legendre(a, n);
            long time = System.nanoTime() - startTime;
            System.out.println("Символ Лежандра (a/n) = " + result);
            System.out.println("Время работы: " + time + " нс");
        }
    }

    public static int legendre(BigInteger a, BigInteger p) {
        if (Euclid.advanced(a, p).r.compareTo(ONE) != 0) {
            return 0;
        }
        if (a.compareTo(BigInteger.ONE) == 0) {
            return 1;
        }
        if (a.mod(TWO).compareTo(ZERO) == 0) {
            int b;
            if (!p.pow(2).subtract(ONE).divide(BigInteger.valueOf(8)).testBit(0)) {
                b = 1;
            }
            else {
                b = -1;
            }
            return legendre(a.divide(TWO), p) * b;
        }
        int b;
        if (!(a.subtract(ONE).multiply(p.subtract(ONE))).divide(BigInteger.valueOf(4)).testBit(0)) {
            b = 1;
        }
        else {
            b = -1;
        }
        return legendre(p.mod(a), a) * b;
    }

    public static int jacoby(BigInteger a, BigInteger n) {
        if (Euclid.advanced(a, n).r.compareTo(ONE) != 0) {
            return 0;
        }
        if (a.compareTo(ZERO) < 0) {
            int b;
            if (!n.subtract(ONE).divide(TWO).testBit(0)) {
                b = 1;
            }
            else {
                b = -1;
            }
            return jacoby(a.negate(), n) * b;
        }
        if (!a.testBit(0)) {
            int b;
            if (!n.multiply(n).subtract(ONE).divide(BigInteger.valueOf(8)).testBit(0)) {
                b = 1;
            }
            else {
                b = -1;
            }
            return jacoby(a.divide(TWO), n) * b;
        }
        if (a.compareTo(ONE) == 0) {
            return 1;
        }
        if (a.compareTo(n) < 0) {
            int b;
            if (!a.subtract(ONE).divide(TWO).multiply(n.subtract(ONE).divide(TWO)).testBit(0)) {
                b = 1;
            }
            else {
                b = -1;
            }
            return jacoby(n, a) * b;
        }
        return jacoby(a.mod(n), n);
    }



}
