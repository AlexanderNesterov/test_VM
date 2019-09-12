package ru.nest;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static String OK = "IER := 0 Нет ошибок.";
    private static String ERROR_1 = "IER := 1 Нарушение порядка последовательности";
    private static String ERROR_2 = "IER := 2 точка не принадлжеит никакому отрезку";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("d:/java/input test.txt"));
        FileOutputStream fos = new FileOutputStream("d:/java/output test.txt");

        int length = Integer.parseInt(sc.nextLine());
        double[] x = readToArray(length, sc);
        double[] y = readToArray(length, sc);
        double pointValue = Double.parseDouble(sc.nextLine());

        sc.close();

        for (int i = 0; i < length - 1; i++) {
            if (x[i] > x[i + 1]) {
                fos.write(ERROR_1.getBytes());
                fos.close();
                throw new Exception(ERROR_1);
            }
        }

        try {
            int segmentNumber = checkSegment(x, pointValue);
            fos.write((OK + " Номер отрезка: " + segmentNumber).getBytes());
        } catch (Exception e) {
            fos.write(e.getMessage().getBytes());
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }

    private static double[] readToArray(int length, Scanner sc) {
        String[] line = sc.nextLine().split(" ");
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = Double.parseDouble(line[i]);
        }

        return result;
    }

    private static int checkSegment(double[] x, double pointValue) throws Exception {
        for (int i = 0; i < x.length - 1; i++) {
            if (pointValue >= x[i] && pointValue < x[i + 1]) {
                return i + 1;
            }
        }

        throw new Exception(ERROR_2);
    }
}
