package lab5;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class GelfondShanks {

    public static void main(String[] args) {
        System.out.println("Метод Гельфонда-Шенкса");
        System.out.println("log(a)b ");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Основание a = ");
        BigInteger a = new BigInteger(scanner.next());
        System.out.print("Значение b = ");
        BigInteger b = new BigInteger(scanner.next());
        System.out.print("Порядок группы m = ");
        BigInteger m = new BigInteger(scanner.next());
        System.out.println("----------------------------");
        long startTime = System.nanoTime();
        gelfondShanks(a, b, m);
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");

    }

    static void gelfondShanks(BigInteger a, BigInteger b, BigInteger m) {
        BigInteger r = m.sqrt().add(ONE);
        ArrayList<BigInteger[]> pairs = new ArrayList<>();
        for (BigInteger i = ZERO; i.compareTo(r) < 0; i = i.add(BigInteger.ONE)) {
            pairs.add(new BigInteger[]{i, a.modPow(i, m)});;
        }
        pairs.sort(Comparator.comparing(pair -> pair[1]));
        BigInteger a1 = lab2.task2.Inverse.inverse(a.modPow(r, m), m);
        ArrayList<BigInteger> x = new ArrayList<>();
        for (BigInteger i = ZERO; i.compareTo(r.subtract(ONE)) < 0; i = i.add(ONE)) {
            for (int k = 0; k < pairs.size(); k++) {
                BigInteger[] pair = pairs.get(k);
                if (pair[1].compareTo(a1.modPow(i, m).multiply(b).mod(m)) == 0) {
                    x.add(pair[0].add(r.multiply(i).mod(m)));
                }
            }
        }
        System.out.println(x.stream().min(BigInteger::compareTo).get());
    }


}
