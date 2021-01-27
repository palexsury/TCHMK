package lab5;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import com.google.common.math.IntMath;

public class RoPollard {

    public static void main(String[] args) throws Exception {
        //System.out.println(BigInteger.valueOf(251).modPow(BigInteger.valueOf(19257), BigInteger.valueOf(33711)));
        System.out.println("Метод ρ-Полларда");
        System.out.println("log(a)b ");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Основание a = ");
        int a = scanner.nextInt();
        System.out.print("Значение b = ");
        int b = scanner.nextInt();
        System.out.print("Порядок группы m = ");
        int m = scanner.nextInt();
        System.out.print("eps = ");
        double eps = scanner.nextDouble();
        System.out.println("----------------------------");
        long startTime = System.nanoTime();
        roPollard(a, b, m, eps);
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");
    }


    public static int modPow(int a, int b, int m){
        int res = 1;
        for (int i = 0; i < b; i++){
            res*=a;
            res%=m;
        }
        return res;
    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static int[] solveComparison(int a, int b, int m) throws Exception {
        Euclid euclid = new Euclid();
        a = a % m;
        b = b % m;
        int d = euclid.ExpandEvklid(m, a)[0] ;
        if (b % d != 0 )
            throw new Exception("Не могу выполнить сравнение!");
        int aNew = a / d;
        int bNew = b / d;
        int mNew = m / d;
        int[] expandEvklid = euclid.ExpandEvklid(aNew, mNew);
        int q = expandEvklid[1] % m;
        int x0 = (bNew * q) % mNew;
        int[] res = new int[d];
        for (int j = 0; j < d; j++){
            res[j] = x0 + mNew*j;
        }
        return res;
    }

    public static int function(int y, int h, int g, int m){
        if (y <= m / 3)
            return IntMath.mod(h*y, m);
        if ((m / 3) < y && (y <= (2* m) / 3))
            return modPow(y, 2, m);
        if ((2*m)/ 3 < y)
            return IntMath.mod(g*y, m);
        return - 1;
    }

    public static int countAlpha(int y, int m, int a){
        if (y <= m / 3)
            return  IntMath.mod(a+1, m-1);
        if (m / 3 < y &&  (y <= (2* m) / 3))
            return IntMath.mod(2*a, m - 1);
        if ((2*m)/ 3 < y)
            return IntMath.mod(a, m - 1);
        return -1;
    }

    public static int countBetta(int y, int m, int b){
        if (y <= m / 3)
            return IntMath.mod(b, m-1);
        if (m / 3 < y  &&  (y <= (2* m) / 3))
            return IntMath.mod(2*b, m - 1);
        if ((2*m)/ 3 < y)
            return IntMath.mod(b+1, m-1);
        return -1;
    }

    public static void roPollard(int a, int b, int m, double eps) throws Exception {
        Euclid euclid = new Euclid();
        boolean flag = true;
        boolean flags = false;
        int alpha = -1;
        int alpha2 = -1;
        int betta = -1;
        int betta2 = -1;
        int d = -1;
        int subAlpha = -1;
        int subBetta = -1;
        if (eps <= 0 || eps >= 1)
            throw new Exception("Значение e должно быть 0 < e < 1");
        double T = Math.sqrt(2 * m * Math.log(1 / eps)) + 1;
        while (!flags) {
            int i = 1;
            Random random = new Random();
            int s = random.nextInt(m - 2);
            int y = function(modPow(a, s, m), b, a, m);
            alpha = countAlpha(modPow(a, s, m), m, s);
            betta = countBetta(modPow(a, s, m), m, 0);
            int y2 = function(y, b, a, m);
            alpha2 = countAlpha(y, m, alpha);
            betta2 = countBetta(y, m, betta);
            while (true) {
                i++;
                alpha = countAlpha(y, m, alpha);
                betta = countBetta(y, m, betta);
                y = function(y, b, a, m);
                alpha2 = countAlpha(y2, m, alpha2);
                betta2 = countBetta(y2, m, betta2);
                y2 = function(y2, b, a, m);
                alpha2 = countAlpha(y2, m, alpha2);
                betta2 = countBetta(y2, m, betta2);
                y2 = function(y2, b, a, m);
                if (y != y2) {
                    if (i >= T) {
                        System.out.println("Вычислить дискретный логарифм не удалось");
                        flag = false;
                        break;
                    }
                }
                if (y == y2) {
                    flag = true;
                    break;
                }
            }
            if (flag == false){
                System.exit(0);
            }
            subAlpha = IntMath.mod(alpha2 - alpha, m - 1) ;
            subBetta = IntMath.mod(betta - betta2, m - 1);
            d = euclid.CommonEvklid(m - 1, subAlpha);
            if (!(Math.sqrt(m - 1) < d || d < 0  || subBetta % d != 0)) {
                flags = true;
            }
        }
        GelfondShanks.gelfondShanks(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(m));
        /*ArrayList<Integer> x = new ArrayList<>();
        int[] solveArray = solveComparison(subAlpha, subBetta, m - 1);
        if (d == 1){
            System.out.println(solveArray[0]);
        }
        else {
            for (int i = 0; i < solveArray.length; i++) {
                if (modPow(a, solveArray[i], m) == b) {
                    if (solveArray[i] > 0) {
                        x.add(solveArray[i]);
                    }
                }
            }
            if (x.isEmpty()) {
                roPollard(a, b, m, eps);
                return;
            }
            System.out.println();
            return;
        }*/
    }

    public static class Euclid {

        public static int CommonEvklid(int a, int b){
            while (b !=0) {
                int temp = a%b;
                a = b;
                b = temp;
            }
            return a;
        }


        // возвращает массив из трех чисел: НОД, x, y
        public int[] ExpandEvklid(int a, int b){
            int res[] = new int[3];
            int x = 0;
            int y = 1;
            int u = 1;
            int v = 0;
            while (a!=0){
                int q = b / a;
                int r = b % a;
                int m = x - u*q;
                int n = y - v * q;
                b = a;
                a = r;
                x = u;
                y = v;
                u = m;
                v = n;
            }
            res[0] = b;
            res[1] = x;
            res[2] = y;
            return res;
        }

        public static BigInteger[] ExpandEvklid(BigInteger a, BigInteger b){
            BigInteger[] res = new BigInteger[3];
            BigInteger x = BigInteger.valueOf(0);
            BigInteger y = BigInteger.valueOf(1);
            BigInteger u = BigInteger.valueOf(1);
            BigInteger v = BigInteger.valueOf(0);
            while(!a.equals(BigInteger.valueOf(0))){
                BigInteger q = b.divide(a);
                BigInteger r = b.mod(a);
                BigInteger m = x.subtract(u.multiply(q));
                BigInteger n = y.subtract(v.multiply(q));
                b = a;
                a = r;
                x = u;
                y = v;
                u = m;
                v = n;
            }
            res[0] = b;
            res[1] = x;
            res[2] = y;
            return res;
        }

        public void printExpandEvklid(int [] res){
            for (int value:res)
                System.out.print(value + " ");
        }


        // b должно быть строго меньше а и больше 0
        public int BinaryEvklid(int a, int b){
            int g = 1;
            int d = 1;
            while(a % 2 == 0 && b % 2 == 0){
                a = a / 2;
                b = b / 2;
                g = 2 * g;
            }
            int u = a;
            int v = b;
            while(u != 0){
                while(u % 2 == 0){
                    u = u/2;
                }
                while(v % 2 == 0){
                    v = v/2;
                }
                if(u >= v){
                    u = u -v;
                }
                else{
                    v = v - u;
                }
            }
            d = g*v;
            return d;
        }

    }


}
