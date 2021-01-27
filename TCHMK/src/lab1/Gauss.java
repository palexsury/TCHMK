package lab1;

import java.math.BigInteger;
import java.util.Scanner;

public class Gauss {
    private BigInteger[][] data = null;
    private int rows = 0, cols = 0;
    private BigInteger p = BigInteger.ZERO;

    public static void main(String[] args){

        System.out.print("module = ");
        Scanner s = new Scanner(System.in);
        int p = s.nextInt();
        System.out.print("m = ");
        int m = s.nextInt();
        System.out.print("n = ");
        int n = s.nextInt();
        BigInteger[][] date = new BigInteger[m][n + 1];
        System.out.println("Матрица А");
        for (int i = 0; i < m; i++) {
            //System.out.println("Коэфициенты при уравнении " + (i + 1));
            for (int j = 0; j < n + 1; j++) {
                date[i][j] = BigInteger.valueOf(s.nextInt());
            }
        }
        long startTime = System.nanoTime();
        Gauss gauss = new Gauss(date, p);
        String[] res = gauss.calc();
        //System.out.println(Arrays.toString(matrix.calc()));

        for (String str :res) {
            if(str != null)
                System.out.println(str);
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Время работы: " + time + " нс");
    }



    public Gauss(int rows, int cols, int p) {
        data = new BigInteger[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.p = BigInteger.valueOf(p);
    }

    public Gauss(BigInteger[][] data, int p) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
        this.p = BigInteger.valueOf(p);
    }
    public void display() {
        //System.out.print("[");
        for (int row = 0; row < rows; ++row) {
            if (row != 0) {
                //System.out.print(" ");
            }

            //System.out.print("[");

            for (int col = 0; col < cols; ++col) {
                System.out.print(data[row][col].toString());

                if (col != cols - 1) {
                    System.out.print(" ");
                }
            }

            //System.out.print("]");

            if (row == rows - 1) {
                //System.out.print("]");
            }

            System.out.println();
        }
    }

    public BigInteger[] getRow(int row){
        if(row < rows && row >= 0) {
            BigInteger [] res = new BigInteger[cols];
            System.arraycopy(data[row], 0, res, 0, cols);
            return res;
        } else
            System.out.println("wrong get row");
        return null;
    }


    public void setRow(BigInteger[] newRows, int row){
        if(newRows.length == cols && row < rows && row >= 0) {
            System.arraycopy(newRows, 0, data[row], 0, cols);
        } else
            System.out.println("wrong set row");
    }

    public void chosePivotElem(int i, int j){
        BigInteger pivot = data[i][j];
        BigInteger[] pivotRow = getRow(i);
        for (int k = 0; k < pivotRow.length; k++) {
            pivotRow[k] = pivotRow[k].multiply(pivot.modInverse(p)).mod(p);
        }
        setRow(pivotRow, i);
        for (int k = 0; k < rows; k++) {
            if(k != i){
                BigInteger a = data[k][j];
                BigInteger[] arr = getRow(k);
                for (int q = 0; q < cols; q++) {
                    arr[q] = arr[q].subtract(pivotRow[q].multiply(a)).mod(p);
                }
                setRow(arr, k);
            }
        }
    }


    public String[] calc(){
        int i = 0, j = 0;
        while(i < rows && j < cols - 1){
            if(data[i][j].compareTo(BigInteger.ZERO) == 0){
                boolean isEmpty = true;
                for (int k = i; k < rows; k++) {
                    if(data[k][j].compareTo(BigInteger.ZERO) != 0){
                        BigInteger[] row = getRow(k);
                        setRow(getRow(i), k);
                        setRow(row, i);
                        isEmpty = false;
                        break;
                    }
                }

                if(isEmpty) {
                    i++;
                    j++;
                    continue;
                }
            }
            chosePivotElem(i, j);
            i++;
            j++;
        }

        System.out.println("------------------------------");
        //System.out.println("получена матрица ");
        this.display();
        System.out.println(" ");
        //System.out.println("ответ ");

        String[] res = new String[cols - 1];
        for (int k = 0; k < rows; k++) {
            BigInteger sum = BigInteger.ZERO;
            for (int t = 0; t < cols - 1; t++) {
                sum = sum.add(data[k][t]);
            }

            StringBuilder s = new StringBuilder("");
            for (int t = k + 1; t < cols - 1; t++) {

                if(data[k][t].compareTo(BigInteger.ZERO) != 0)
//                    if(data[k][t].compareTo(BigInteger.ONE) == 0)
//                        s.append(" + x").append(t + 1);
//                    else
                    s.append(" + (-").append(data[k][t]).append(") * x").append(t + 1);
            }
            s.append(" + ").append(data[k][cols - 1]);
            s = new StringBuilder("x" + (k + 1) + " = " + s.substring(3));


            if(sum.compareTo(BigInteger.ZERO) == 0 && data[k][cols - 1].compareTo(BigInteger.ZERO) != 0) {
                //System.out.println("no solution");
                return new String[]{"Нет решений"};
            }

            if(sum.compareTo(BigInteger.ZERO) == 0 && data[k][cols - 1].compareTo(BigInteger.ZERO) == 0) {
                continue;
            }
//            if(sum.compareTo(BigInteger.ONE) == 0) {
//                res[k] = data[k][cols - 1];
//            }
            res[k] = s.toString();

        }


        return res;

    }

    //    public BigInteger[] getCol(int col){
//        if(col < cols && col > 0) {
//            BigInteger[] res = new BigInteger[rows];
//            for (int row = 0; row < rows; ++row) {
//                res[row] = data[row][col];
//            }
//            return res;
//        } else
//            System.out.println("wrong get col");
//        return null;
//    }
//    public void setCol(BigInteger[] newCols, int col){
//        if(newCols.length == cols && col < cols && col > 0) {
//            for (int row = 0; row < rows; ++row) {
//                data[row][col] = newCols[row];
//            }
//        } else
//            System.out.println("wrong set col");
//    }
//
//    public void divisinAndSubstrRow(int x, int y, BigInteger a){
//        BigInteger[] arrX = getRow(x);
//        BigInteger[] arrY = getRow(y);
//        for (int i = 0; i < arrX.length; i++) {
//            arrY[i] = arrY[i].subtract(arrX[i].multiply(a.modInverse(this.p))).mod(p);
//        }
//        setRow(arrY, y);
//    }


//    public BigInteger[] calc(){
//        int n = rows;
//        for (int k = 0; k < n; k++) {
//            for (int j = k + 1; j < n; j++) {
//                try {
//                    data[k][k].modInverse(p);
//                } catch (ArithmeticException e){
//                    System.out.println("не найдено обратное от " + data[k][k]);
//                    continue;
//                }
//                if(data[k][k].compareTo(data[k][j]) < 0){
//                    BigInteger[] row = getRow(k);
//                    setRow(getRow(j), k);
//                    setRow(row, j);
//                }
//            }
//            if(data[k][k].compareTo(BigInteger.ZERO) < 0){
//                continue;
//            }
//            chosePivotElem(k, k);
//
//        }
//        BigInteger[] x = new BigInteger[n + 1];
//        for (int i = n - 1; i >= 0; i--) {
//            x[i] = data[i][n].multiply(data[i][i].modInverse(p));
//            for (int j = n - 1; j > i; j--) {
//                x[i] = x[i].subtract(data[i][j].multiply(x[j].multiply(data[i][i].modInverse(p))).mod(p));
//            }
//            System.out.println("x" + i + " = " + x[i]);
//        }
//
//        return x;
//    }


//    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
//        Matrix result = new Matrix(matrix.rows - 1, matrix.cols - 1, matrix.p.intValue());
//
//        for (int row = 0, p = 0; row < matrix.rows; ++row) {
//            if (row != exclude_row - 1) {
//                for (int col = 0, q = 0; col < matrix.cols; ++col) {
//                    if (col != exclude_col - 1) {
//                        result.data[p][q] = matrix.data[row][col];
//
//                        ++q;
//                    }
//                }
//
//                ++p;
//            }
//        }
//
//        return result;
//    }


}