package ru.nest;

import java.io.*;
import java.util.*;

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
        double checkEps = Double.parseDouble(sc.nextLine());

        System.out.println(Arrays.toString(x));
        //System.out.println(Arrays.toString(y));
        //System.out.println(pointValue);

        sc.close();

        for (int i = 0; i < length - 1; i++) {
            if (x[i] > x[i + 1]) {
                fos.write(ERROR_1.getBytes());
                fos.close();
                throw new Exception(ERROR_1);
            }
        }

        int segmentNumber = 0;

        try {
            segmentNumber = checkSegment(x, pointValue);
            fos.write((OK + " Номер отрезка: " + segmentNumber).getBytes());
        } catch (Exception e) {
            fos.write(e.getMessage().getBytes());
            e.printStackTrace();
        } finally {
            fos.close();
        }

        System.out.println("segmentNumber: " + segmentNumber);

        List<Double> xList = new ArrayList<>(length);
        List<Double> yList = new ArrayList<>(length);

        Collections.addAll(xList, x[segmentNumber - 1], x[segmentNumber]);
        Collections.addAll(yList, y[segmentNumber - 1], y[segmentNumber]);

        int leftSegmentPointIndex = segmentNumber - 1;
        int rightSegmentPointIndex = segmentNumber;

        double previousPolynomial = 0.0;
        double currentPolynomial = calculatePolynomial(xList, yList, pointValue);

        double previousEps;
        double currentEps = Double.MAX_VALUE;

        while (true) {

            boolean isLeftChanged = true;
            if (--leftSegmentPointIndex < 0) {
                isLeftChanged = false;
                leftSegmentPointIndex++;
            }

            boolean isRightChanged = true;
            if (++rightSegmentPointIndex >= length) {
                isRightChanged = false;
                rightSegmentPointIndex--;
            }

            if (!isRightChanged && !isLeftChanged) {
                //Error
                System.out.println("Error 11");
                break;
            }

            if (isLeftChanged && isRightChanged) {
                if ((x[leftSegmentPointIndex + 1] - x[leftSegmentPointIndex]) > (x[rightSegmentPointIndex] - x[rightSegmentPointIndex - 1])) {
                    isLeftChanged = false;
                    leftSegmentPointIndex++;
                } else {
                    isRightChanged = false;
                    rightSegmentPointIndex--;
                }
            }

            if (isLeftChanged) {
                xList.add(x[leftSegmentPointIndex]);
                yList.add(y[leftSegmentPointIndex]);
            } else {
                xList.add(x[rightSegmentPointIndex]);
                yList.add(y[rightSegmentPointIndex]);
            }

            previousPolynomial = currentPolynomial;
            currentPolynomial = calculatePolynomial(xList, yList, pointValue);

            previousEps = currentEps;
            currentEps = Math.abs(currentPolynomial - previousPolynomial);

            if (currentEps >= previousEps) {
                //Error
                System.out.println("Error 22");
                break;
            }

            System.out.println(currentEps);
            if (currentEps < checkEps) {
                //Error
                System.out.println("Error 33");
                break;
            }
        }

        System.out.println(currentPolynomial);
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

    private static double calculatePolynomial(List<Double> xList, List<Double> yList, double pointValue) {
        double polynomialValue = 0;
        int degree = xList.size();

        for (int i = 0; i < degree; i++) {
            double multiply = 1;

            for (int j = 0; j < degree; j++) {
                if (i != j) {
                    multiply *= (pointValue - xList.get(j)) / (xList.get(i) - xList.get(j));
                }
            }

            polynomialValue += yList.get(i) * multiply;
        }

        return polynomialValue;
    }
}
