package lab4;

import lab2.task3.LegendreAndJacoby;

import java.util.*;
import java.math.*;
public class Morrison {

    public BigInteger n;
    private double a;
    private int k;
    private ArrayList<BigInteger> p = new ArrayList<>();
    public BigInteger[] d = new BigInteger[2];

    private static BigDecimal TWO = BigDecimal.valueOf(2);


    public static void main(String[] args) throws Exception {
        System.out.println("Метод Бриллхарта-Моррисона");
        Scanner scanner = new Scanner(System.in);
        System.out.print("n = ");
        BigInteger n = new BigInteger(scanner.next());
        System.out.print("a = ");
        Double a = scanner.nextDouble();
        System.out.println("-------------------------------------------------------");
        long startTime = System.nanoTime();
        System.out.println(new Morrison(n, a));
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");
    }

    // реализация sqrt для BigInteger Java 8
    public static BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
        while(b.compareTo(a) >= 0) {
            BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
            if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
            else a = mid.add(BigInteger.ONE);
        }
        return a.subtract(BigInteger.ONE);
    }

    public boolean isFullSquare(BigInteger number)
    {
        double result = Math.sqrt(number.doubleValue());
        return (result % 1 == 0);
    }


    private double log(BigInteger c) {
        int t = c.bitLength();
        BigDecimal s = new BigDecimal(c).divide(TWO.pow(t));
        return Math.log(s.doubleValue()) + t * Math.log(2);
    }

    private void generateFactorBase() {
        String s = String.format("%14s", "p: -1 ");
        this.p.add(BigInteger.valueOf(-1));
        BigInteger L = BigInteger.valueOf((long) Math.pow(Math.exp(Math.pow(log(this.n) * Math.log(log(n)), 0.5)), this.a));
        for (BigInteger p = BigInteger.valueOf(2); p.compareTo(L) <= 0; p = p.nextProbablePrime()) {
            if (LegendreAndJacoby.jacoby(this.n, p) != -1) {
                this.p.add(p);
                s += p.toString() + " ";
            }
        }
        this.k = this.p.size();
        System.out.println(s);
    }

    private ArrayList<Integer> factorizationBase(BigInteger Qmi, ArrayList<Integer> vStep) {
        ArrayList<Integer> vi = new ArrayList<>();
        BigInteger c = Qmi;
        if (c.compareTo(BigInteger.ZERO) < 0) {
            vi.add(1);
            vStep.add(1);
            c = c.negate();
        }
        else {
            vi.add(0);
            vStep.add(0);
        }
        boolean existNotNull = false;
        for (int i = 1; i < this.p.size(); i++) {
            int a = 0;
            for (; c.mod(this.p.get(i)).compareTo(BigInteger.ZERO) == 0;) {
                a++;
                c = c.divide(this.p.get(i));
            }
            if (a % 2 != 0) {
                existNotNull = true;
            }
            vStep.add(a);
            a %= 2;
            vi.add(a);
        }
        if (!existNotNull || c.compareTo(BigInteger.ONE) != 0) {
            return null;
        }
        return vi;
    }

    private ArrayList<Integer> findSolution(ArrayList<ArrayList<Integer>> v) {
        ArrayList<Integer> x = new ArrayList<>();
        for (int i = 0; i < v.size() - 1; i++) {
            x.add(0);
        }
        x.add(1);
        for (;;) {
            int j;
            for (j = 0; j < v.get(0).size(); j++) {
                int res = 0;
                for (int i = 0; i < v.size(); i++) {
                    res += v.get(i).get(j) * x.get(i);
                    res %= 2;
                }
                if (res != 0) {
                    break;
                }
            }
            if (j == v.get(0).size()) {
                break;
            }
            int l;
            for (l = x.size() - 1; l >= 0 && x.get(l) == 1; l--) {
                x.set(l, 0);
            }
            if (l == -1) {
                return new ArrayList<>();
            }
            x.set(l, 1);
        }
        return x;
    }

    private ArrayList<Integer> findSolution(ArrayList<ArrayList<Integer>> v, ArrayList<Integer> x) {
        int l;
        for (l = x.size() - 1; l >= 0 && x.get(l) == 1; l--) {
            x.set(l, 0);
        }
        if (l == -1) {
            return new ArrayList<>();
        }
        x.set(l, 1);
        for (;;) {
            int j;
            for (j = 0; j < v.get(0).size(); j++) {
                int res = 0;
                for (int i = 0; i < v.size(); i++) {
                    res += v.get(i).get(j) * x.get(i);
                    res %= 2;
                }
                if (res != 0) {
                    break;
                }
            }
            if (j == v.get(0).size()) {
                break;
            }
            for (l = x.size() - 1; l >= 0 && x.get(l) == 1; l--) {
                x.set(l, 0);
            }
            if (l == -1) {
                return new ArrayList<>();
            }
            x.set(l, 1);
        }
        return x;
    }

    public Morrison(BigInteger n, double a) throws Exception {
        this.n = n;
        this.a = a;
        if (n.isProbablePrime(10)) {
            throw new Exception("Число простое!!");
        }
        if (isFullSquare(n)) {
            d[0] = sqrt(n);
            System.out.println("Число является полным квадратом " + sqrt(n));
        } else {
            this.generateFactorBase();
            ChainFactory ch = new ChainFactory(n);
            for (; ; ) {
                ArrayList<ArrayList<Integer>> v = new ArrayList<>();
                ArrayList<ArrayList<Integer>> vStep = new ArrayList<>();
                ArrayList<BigInteger> P = new ArrayList<>();
                ArrayList<BigInteger> Qm = new ArrayList<>();
                for (; v.size() != this.k + 1; ) {
                    BigInteger[] PQ = ch.getLastAndGenerateNext();
                    BigInteger Qmi = PQ[0].pow(2).subtract(this.n.multiply(PQ[1].pow(2)));
                    ArrayList<Integer> viStep = new ArrayList<>();
                    ArrayList<Integer> vi = this.factorizationBase(Qmi, viStep);
                    if (vi == null) {
                        continue;
                    }
                    Qm.add(Qmi);
                    P.add(PQ[0]);
                    v.add(vi);
                    vStep.add(viStep);
                }
                ArrayList<Integer> x = this.findSolution(v);
                if (x.isEmpty()) {
//                System.out.println("Не нашлось ни одного решения уравнения");
                    continue;
                }
                for (; ; ) {
                    x = this.findSolution(v, x);
                    if (x.isEmpty()) {
//                    System.out.println("Не нашлось ни одного решения уравнения");
                        break;
                    }
                    BigInteger X = BigInteger.ONE;
                    BigInteger Y = BigInteger.ONE;
                    for (int i = 0; i <= this.k; i++) {
                        X = X.multiply(P.get(i).pow(x.get(i))).mod(this.n);
                    }
                    for (int j = 0; j < this.k; j++) {
                        int step = 0;
                        for (int i = 0; i < x.size(); i++) {
                            step += x.get(i) * vStep.get(i).get(j);
                        }
                        step /= 2;
                        Y = Y.multiply(this.p.get(j).pow(step)).mod(this.n);
                    }

                    if (X.pow(2).mod(this.n).compareTo(Y.pow(2).mod(this.n)) != 0) {
//                    System.out.println("X и Y не прошли проверку");
                        continue;
                    }
                    BigInteger[] prov = new BigInteger[]{X.add(Y), X.subtract(Y)};
                    for (int i = 0; i < prov.length; i++) {
                        BigInteger gcd = prov[i].gcd(this.n);
                        if (gcd.compareTo(BigInteger.ONE) > 0 && gcd.compareTo(this.n) < 0) {
                            d[0] = gcd;
                            d[1] = this.n.divide(gcd);
                            String s = "";
                            for (int j = 0; j < v.size(); j++) {
                                s += String.format("%10s" ,Qm.get(j).toString()) + ":";
                                for (int l = 0; l < v.get(j).size(); l++) {
                                    int k = p.get(l).toString().length();
                                    String format = "%" + k + "s";
                                    s += String.format(format ,v.get(j).get(l).toString()) + " ";
                                }
                                s += "\n";
                            }
                            s += String.format("%12s" ,"x: ");
                            for (int j : x) {
                                s += j + " ";
                            }
                            System.out.println(s);
                            return;
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        if (this.d[0] == null) {
            return "Разложение не найдено";
        }
        return this.n + " = " + this.d[0].toString() + " * " + (d[1] == null ? d[0] : d[1]).toString();
    }


    public static class ChainFactory {
        public ArrayList<BigInteger> P;
        public ArrayList<BigInteger> Q;
        private BigInteger vi, ui, n, sqrtN;

        private static BigInteger TWO = BigInteger.valueOf(2);

        public ChainFactory(BigInteger n) {
            this.P = new ArrayList<>();
            this.P.add(BigInteger.ZERO);
            this.P.add(BigInteger.ONE);
            this.Q = new ArrayList<>();
            this.Q.add(BigInteger.ONE);
            this.Q.add(BigInteger.ZERO);
            this.n = n;
            this.sqrtN = sqrt(n);
            this.P.add(this.sqrtN.multiply(this.P.get(this.P.size() - 1)).add(this.P.get(this.P.size() - 2)));
            this.Q.add(this.sqrtN.multiply(this.Q.get(this.Q.size() - 1)).add(this.Q.get(this.Q.size() - 2)));
            this.vi = BigInteger.ONE;
            this.ui = this.sqrtN;
            P.remove(0);
            Q.remove(0);
            this.getLastAndGenerateNext();
        }

        public BigInteger[] getLastAndGenerateNext() {
            BigInteger[] ans = new BigInteger[]{this.P.get(this.P.size() - 1), this.Q.get(this.Q.size() - 1)};
            this.vi = this.n.subtract(this.ui.pow(2)).divide(this.vi);
            BigInteger q = this.sqrtN.add(this.ui).divide(this.vi);
            this.P.add(q.multiply(this.P.get(this.P.size() - 1)).add(this.P.get(this.P.size() - 2)));
            this.Q.add(q.multiply(this.Q.get(this.Q.size() - 1)).add(this.Q.get(this.Q.size() - 2)));
            this.ui = q.multiply(this.vi).subtract(this.ui);
            P.remove(0);
            Q.remove(0);
            return ans;
        }

        public static BigInteger sqrt(BigInteger n) {
            BigInteger low = BigInteger.ZERO;
            BigInteger high = n.add(BigInteger.ONE);
            while (high.subtract(low).compareTo(BigInteger.ONE) > 0) {
                BigInteger mid = low.add(high).divide(TWO);
                if (mid.multiply(mid).compareTo(n) <= 0) {
                    low = mid;
                } else {
                    high = mid;
                }
            }
            return low;
        }

        private static String formatMasAnswer(String objName, ArrayList<BigInteger> e) {
            String answer = objName + ":))))" + ": [";
            ArrayList<String> mas = new ArrayList<>();
            for (BigInteger i: e) {
                mas.add(i.toString());
            }
            answer += String.join(", ", mas) + "]\n";
            return answer;
        }

        public String toString() {
            String answer = "";
            answer += formatMasAnswer("P", this.P);
            answer += formatMasAnswer("Q", this.Q);
            return answer;
        }

    }

}