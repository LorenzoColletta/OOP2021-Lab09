package it.unibo.oop.lab.workers02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 *
 */
public class MultiThreadedSumMatrix implements SumMatrix {

    private int nThreads;

    /**
     * 
     * @param nThreads
     */
    public MultiThreadedSumMatrix(final int nThreads) {
        this.nThreads = nThreads;
    }

    private class Worker extends Thread {
        private double[][] matrix;
        private int startPos;
        private int nElements;
        private double res;

        Worker(final double[][] matrix, final int startPos, final int nElements) {
            this.matrix = matrix;
            this.startPos = startPos;
            this.nElements = nElements;
            res = 0;
        }

        public void run() {
            int start = startPos / matrix[0].length;
            for (int i = 0; i < nElements - startPos; i++) {
                res += matrix[start + i / matrix[0].length][(startPos + i) % matrix[0].length];
            }
        }

        public double getResult() {
            return this.res;
        }
    }

    @Override
    public double sum(final double[][] matrix) {
        int matrixLength = 0;

        for (double[] i : matrix) {
            matrixLength += i.length;
        }
        //System.out.println("matrixLength: " + matrixLength);

        final List<Worker> workers = new ArrayList<>(nThreads);
        int elements = 0;
        for (int i = 0; i < nThreads; i++) {
            //System.out.print("start: " + elements);
            workers.add(new Worker(matrix, elements, elements + matrixLength / nThreads + (i < (matrixLength % nThreads) ? 1 : 0)));
            elements += matrixLength / nThreads + (i < (matrixLength % nThreads) ? 1 : 0);
            //System.out.println(" Size: " + elements + " Calculus: " + (matrixLength / nThreads + (i < (matrixLength % nThreads) ? 1 : 0)));

        }

        for (final Thread worker: workers) {
            worker.start();
        }

        double sum = 0;
        for (final Worker w: workers) {
            try {
                w.join();
                sum += w.getResult();
                //System.out.println("GetResult(): " + w.getResult());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        return sum;
    }

}
