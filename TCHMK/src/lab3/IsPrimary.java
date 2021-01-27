package lab3;

import lab2.task3.LegendreAndJacoby;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static java.math.BigInteger.*;

public class IsPrimary {

    private static final int t = 10;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("n = ");
        BigInteger n = new BigInteger(scanner.next());
        System.out.println("---------------------------------------");
        long startTime = System.nanoTime();
        boolean result = ferma(n);
        long time = System.nanoTime() - startTime;
        System.out.println("Тест Ферма:             " + resultStr(result));
        System.out.println("Время работы: " + time + " нс");
        System.out.println("---------------------------------------");
        startTime = System.nanoTime();
        result = soloveyStrassen(n);
        time = System.nanoTime() - startTime;
        System.out.println("Тест Соловея-Штрассена: " + resultStr(result));
        System.out.println("Время работы: " + time + " нс");
        System.out.println("---------------------------------------");
        startTime = System.nanoTime();
        result = rabin(n);
        time = System.nanoTime() - startTime;
        System.out.println("Тест Рабина:            " + resultStr(result));
        System.out.println("Время работы: " + time + " нс");
    }

    public static String resultStr(boolean result) {
        if (result) {
            return "вероятно простое";
        }
        return "составное";
    }

    public static BigInteger randomNum(BigInteger n) {
        BigInteger result = ZERO;
        while (result.compareTo(TWO) < 0 || result.compareTo(n.subtract(TWO)) > 0) {
            result = new BigInteger(n.bitCount(), random);
        }
        return result;
    }

    public static boolean soloveyStrassen(BigInteger n) {
        for (int i = 0; i < t; i++) {
            BigInteger a = randomNum(n);
            BigInteger r = a.modPow(n.subtract(ONE).divide(TWO), n);
            if (r.compareTo(ONE) != 0 && r.compareTo(n.subtract(ONE)) != 0) {
                return false;
            }
            BigInteger s;
            if (a.gcd(n).compareTo(ONE) != 0) {
                s = ZERO;
            }
            else {
                s = BigInteger.valueOf(LegendreAndJacoby.jacoby(a, n));
            }
            if ((r.mod(n).compareTo(s.mod(n))) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean ferma(BigInteger n) {
        for (int i = 0; i < t; i++) {
            BigInteger a = randomNum(n);
            BigInteger r = a.modPow(n.subtract(ONE), n);
            if (r.compareTo(ONE) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean rabin(BigInteger n) {
        for (int i = 0; i < t; i++) {
            BigInteger r = n.subtract(ONE);
            int s = 0;
            while (!r.testBit(0)) {
                r = r.divide(TWO);
                s += 1;
            }
            BigInteger a = randomNum(n);
            BigInteger y = a.modPow(r, n);
            if (y.compareTo(ONE) != 0 && y.compareTo(n.subtract(ONE)) != 0) {
                int j = 1;
                while (j <= s - 1 && y.compareTo(n.subtract(ONE)) != 0) {
                    y = y.modPow(TWO, n);
                    if (y.compareTo(ONE) == 0) {
                        return false;
                    }
                    j++;
                }
                if (y.compareTo(n.subtract(ONE)) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
