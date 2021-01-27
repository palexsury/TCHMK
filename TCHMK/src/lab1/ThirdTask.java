package lab1;

import java.math.BigInteger;
import java.util.Arrays;

public class ThirdTask {

    public static void gauss(int[][] a, int module) {
        int n = a.length;
        int m = a[0].length - 1;
        int[] where = new int[m];
        Arrays.fill(where, -1);
        for (int col = 0, row = 0; col < m && row < n; col++) {
            int sel = row;
            for (int i = row; i < n; i++) {
                if (a[i][col] > Math.abs(a[sel][col])) {
                    sel = i;
                }
            }
            if (a[sel][col] == 0)  {
                continue;
            }
            for (int i = col; i <= m; i++) {
                int temp = a[sel][i];
                a[sel][i] = a[row][i];
                a[row][i] = temp;
            }
            where[col] = row;
            for (int i = 0; i < n; i++) {
                if (i != row) {
                    if (a[row][col] < 0) {
                        a[row][col] += module;
                    }
                    int c = (a[i][col] * ((Euclid.advanced(BigInteger.valueOf(a[row][col]), BigInteger.valueOf(module)).x).intValue() + module) % module) % module;
                    for (int j = col; j <= m; j++) {
                        int temp = (a[row][j] * c) % module;
                        if (a[i][j] - temp < 0) {
                            a[i][j] = a[i][j] - temp + module;
                        }
                        else {
                            a[i][j] = a[i][j] - temp;
                        }
                    }
                }
            }
            row++;
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i][a[0].length - 1] != 0) {
                int k = 0;
                for (int j = 0; j < a[0].length - 1; j++) {
                    if (a[i][j] != 0) {
                        k++;
                    }
                }
                if (k == 0) {
                    System.out.println("Нет решений");
                    return;
                }
            }
        }

        for (int i = 0; i < a.length; i++) {
            if (where[i] != -1) {
                int j = where[i];
                int a1 = a[j][i];
                if (a1 != 0) {
                    for (int k = i; k < a[0].length; k++) {
                        int a2 = Euclid.advanced(BigInteger.valueOf(a1),
                                BigInteger.valueOf(module)).x.intValue();
                        while (a2 < module) {
                            a2 += module;
                        }
                        a[j][k] = a[j][k] * a2 % module;
                    }
                }
            }
        }

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();


        for (int i = 0; i < a.length; i++) {
            int j = 0;
            while (j < a[0].length - 1 && a[i][j] == 0) {
                j++;
            }
            j++;
            if (j != a[0].length) {
                System.out.print("x" + (i + 1) + " = ");
                for (; j < a[0].length - 1; j++) {
                    if (a[i][j] != 0) {
                        String coeff;
                        if (module - a[i][j] == 1) {
                            coeff = "";
                        } else {
                            coeff = String.valueOf((module - a[i][j]));
                        }
                        System.out.print(coeff + "x" + (j + 1) + " + ");
                    }
                }
                System.out.print(a[i][a[0].length - 1]);
            }
            System.out.println();
        }
    }


}
