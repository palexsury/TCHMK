package lab4;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class RoMinusOnePollard {

    private static BigInteger q;
    private static BigInteger T;
    private static BigInteger k;
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Метод (ρ-1)-Полларда");
        Scanner scanner = new Scanner(System.in);
        System.out.print("n = ");
        BigInteger n = new BigInteger(scanner.next());
        System.out.print("B = ");
        BigInteger B = new BigInteger(scanner.next());
        System.out.println("---------------------------------------");
        long startTime = System.nanoTime();
        BigInteger[] result = roMinusOnePollard(n, B);
        long time = System.nanoTime() - startTime;
        System.out.println(resultStr(n, result));
        System.out.println("Время работы: " + time + " нс");
    }

    public static BigInteger randomNum(BigInteger n) {
        BigInteger result = new BigInteger(n.bitCount(), random);
        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitCount(), random);
        }
        return result;
    }

    private static BigInteger prevPrimary(BigInteger p) {
        if (p.testBit(0)) {
            p = p.subtract(TWO);
        }
        else {
            p = p.subtract(ONE);
        }
        while (true) {
            if (p.isProbablePrime(1000)) {
                return p;
            }
            p = p.subtract(TWO);
        }
    }

    private static void factoryBase(BigInteger n, BigInteger B) {
        T = ONE;
        BigInteger p = TWO;
        while (p.compareTo(B) <= 0) {
            q = p;
            k = BigInteger.valueOf ((int) (Math.log(n.doubleValue()) / Math.log(q.doubleValue())));
            T = T.multiply(q.pow(k.intValue()));
            p = p.nextProbablePrime();
        }
    }

    private static String resultStr(BigInteger n, BigInteger[] result) {
        if (result == null) {
            return "Разложение не найдено";
        }
        return n + " = " + result[0] + " * " + result[1];
    }

    private static BigInteger[] roMinusOnePollard(BigInteger n, BigInteger B) {
        factoryBase(n, B);
        BigInteger a = randomNum(n);
        boolean flag = false;
        while (true) {
            a = a.mod(n);
            BigInteger d = a.gcd(n);
            if (d.compareTo(ONE) > 0 && d.compareTo(n) < 0) {
                return new BigInteger[]{d, n.divide(d)};
            }
            if (d.compareTo(ONE) == 0) {
                BigInteger b = a.modPow(T, n).subtract(ONE);
                BigInteger n1 = b.gcd(n);
                if (n1.compareTo(ONE) == 0) {
                    B = B.nextProbablePrime();
                    q = q.nextProbablePrime();
                    k = BigInteger.valueOf ((int) (Math.log(n.doubleValue()) / Math.log(q.doubleValue())));
                    T = T.multiply(q.pow(k.intValue()));
                    continue;
                }
                if (n1.compareTo(n) == 0) {
                    if (flag) {
                        T = T.divide(q.pow(k.intValue()));
                        B = prevPrimary(B);
                        q = prevPrimary(q);
                        k = BigInteger.valueOf ((long) (Math.log(n.doubleValue()) / Math.log(q.doubleValue())));
                        flag = false;
                        continue;
                    }
                    flag = true;
                    continue;
                }
                return new BigInteger[]{n1, n.divide(n1)};
            }
        }

    }




}
