package lab1;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //СЛУ
        System.out.print("module = ");
        int module = scanner.nextInt();
        System.out.print("m = ");
        int m = scanner.nextInt();
        System.out.print("n = ");
        int n = scanner.nextInt();
        System.out.println("матрица А:");
        int[][]a = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n + 1; j++) {
                a[i][j] = scanner.nextInt();
            }
        }
        System.out.println("-------------------");
        long startTime = System.nanoTime();
        ThirdTask.gauss(a, module);
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");

    }
}
