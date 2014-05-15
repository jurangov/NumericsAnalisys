package com.analisis.numericsapp.app.EquationSystemPackage;

/**
 * Created by juancho
 */
public class EquationSystemAuxiliar
{

    public static double[][] AumentedMatrix(double A[][], double b[]) {
        int n = A.length;
        double Ab[][] = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            Ab[i][n] = b[i];
            for (int j = 0; j < n; j++) {
                Ab[i][j] = A[i][j];
            }
        }
        return Ab;
    }

    public static int SearchBigger(double Ab[][], int i, int columna) {
        int n = Ab.length;
        int fila = i;
        double mayor = Math.abs(Ab[i][columna]);
        while (i < n - 1) {
            if (mayor < Math.abs(Ab[i + 1][columna])) {
                mayor = Math.abs(Ab[i + 1][columna]);
                fila = i + 1;
            }
            i++;
        }
        return fila;
    }

    public static double[][] ChangeRow(double Ab[][], int k, int rowM) {
        int n = Ab.length;
        for (int i = 0; i < n + 1; i++) {
            double fAux = Ab[k][i];
            Ab[k][i] = Ab[rowM][i];
            Ab[rowM][i] = fAux;
        }
        return Ab;
    }

    public static double[][] ChangeColumn(double Ab[][], int k, int column) {
        int n = Ab.length;
        for (int i = 0; i < n; i++) {
            double fAux = Ab[i][k];
            Ab[i][k] = Ab[i][column];
            Ab[i][column] = fAux;
        }
        return Ab;
    }

    public static double[] RegresiveSustitutionAB(double ab[][], int n) {
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sumatoria = 0.0;
            for (int p = i + 1; p < n; p++) {
                sumatoria = sumatoria + ab[i][p] * x[p];
            }
            x[i] = (ab[i][n] - sumatoria) / ab[i][i];
        }
        return x;
    }


}
