package ru.nest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class Polynomial {
    private static final String OK = "IER := 0 Нет ошибок";
    private static final String ERROR_1 = "IER := 1 Требуемая точность не достигнута";
    private static final String ERROR_2 = "IER := 2 Требуемая точность не достигается. Модуль разности между\n" +
            "двумя последовательными интерполяционными значениями перестаёт\n" +
            "уменьшаться";
    private static final String ERROR_3 = "IER := 3 Нарушение порядка последовательности";
    private static final String ERROR_4 = "IER := 4 Точка не принадлжеит никакому отрезку";

    public void calculate() throws Exception {

        Scanner sc = new Scanner(new File("d:/java/vm/polyn2.txt"));
        FileOutputStream fos = new FileOutputStream("d:/java/vm/output test.txt");

        int length = Integer.parseInt(sc.nextLine());
        double[] x = readToArray(length, sc);
        double[] y = readToArray(length, sc);
        double pointValue = Double.parseDouble(sc.nextLine());
        double checkEps = Double.parseDouble(sc.nextLine());

        sc.close();

        for (int i = 0; i < length - 1; i++) {
            if (x[i] > x[i + 1]) {
                fos.write(ERROR_3.getBytes());
                fos.close();
                throw new Exception(ERROR_3);
            }
        }

        int segmentNumber = 0;

        try {
            segmentNumber = checkSegment(x, pointValue);
        } catch (Exception e) {
            fos.write(e.getMessage().getBytes());
            fos.close();
            e.printStackTrace();
        }

        try {
            double result = findValue(x, y, pointValue, checkEps, segmentNumber, length);
            fos.write((OK + "\r\n" + result).getBytes());
        } catch (Exception e) {
            fos.write(e.getMessage().getBytes());
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }

    private double findValue(double[] x, double[] y, double pointValue,
                                    double checkEps, int segmentNumber, int length) throws Exception {
        List<Double> xList = new ArrayList<>(length);
        List<Double> yList = new ArrayList<>(length);

        Collections.addAll(xList, x[segmentNumber - 1], x[segmentNumber]);
        Collections.addAll(yList, y[segmentNumber - 1], y[segmentNumber]);

        int leftSegmentPointIndex = segmentNumber - 1;
        int rightSegmentPointIndex = segmentNumber;

        double previousPolynomial = 0.0;
        double currentPolynomial = calculatePolynomial(xList, yList, pointValue);

        if (length == 2) {
            return currentPolynomial;
        }

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
                throw new Exception(ERROR_1);
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

            //System.out.println(xList.size());
            //System.out.println("prevEps = " + previousEps + " currEps = " + currentEps);

/*            if (xList.size() == 4) {
                continue;
            }*/

            if (currentEps >= previousEps) {
                throw new Exception(ERROR_2);
            }

            //System.out.println(currentEps);
            if (currentEps < checkEps) {
                return currentPolynomial;
            }
        }
    }

    private double[] readToArray(int length, Scanner sc) {
        String[] line = sc.nextLine().split(" ");
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = Double.parseDouble(line[i]);
        }

        return result;
    }

    private int checkSegment(double[] x, double pointValue) throws Exception {
        for (int i = 0; i < x.length - 1; i++) {
            if (pointValue >= x[i] && pointValue < x[i + 1]) {
                return i + 1;
            }
        }

        throw new Exception(ERROR_4);
    }

    private double calculatePolynomial(List<Double> xList, List<Double> yList, double pointValue) {
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
