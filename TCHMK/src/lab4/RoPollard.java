package lab4;

import lab1.Euclid;

import java.util.Random;
import java.util.Scanner;

public class RoPollard {

    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Метод ρ-Полларда");
        Scanner scanner = new Scanner(System.in);
        System.out.print("n = ");
        int n = scanner.nextInt();
        System.out.print("eps = ");
        double eps = scanner.nextDouble();
        System.out.println("---------------------------------------");
        long startTime = System.nanoTime();
        int[] result = roPollard(n, eps);
        long time = System.nanoTime() - startTime;
        System.out.println(resultStr(n, result));
        System.out.println("Время работы: " + time + " нс");
    }

    public static int randomNum(int n) {
        return (int) (Math.random() * n);
    }

    private static int f(int x) {
        return x * x + 1;
    }

    private static String resultStr(int n, int[] result) {
        if (result == null) {
            return "Разложение не найдено";
        }
        return n + " = " + result[0] + " * " + result[1];
    }

    public static int[] roPollard(int n, double eps) {
        int T = (int) Math.ceil(Math.sqrt(2 * Math.sqrt(n) * Math.log(1 / eps))) + 1;
        while (true) {
            int[] x = new int[T + 1];
            x[0] = randomNum(n);
            for (int j = 0; j < T; j++) {
                x[j + 1] = f(x[j]) % n;
            }
            for (int j = 0; j < T; j++) {
                for (int k = 0; k < j; k++) {
                    int d = Euclid.gcd(x[j + 1] - x[k], n);
                    if (d == 1) {
                        continue;
                    }
                    if (d == n) {
                        break;
                    }
                    return new int[]{d, n/d};
                }
            }
        }
    }

}
