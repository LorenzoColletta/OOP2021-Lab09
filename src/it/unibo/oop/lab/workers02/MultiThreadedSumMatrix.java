package it.unibo.oop.lab.workers02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Lorenzo
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
        private long res;

        Worker(final double[][] matrix, final int startPos, final int nElements) {
            this.matrix = matrix;
            this.startPos = startPos;
            this.nElements = nElements;
        }

        public void run() {
            final int start = startPos / matrix[0].length + startPos % matrix[0].length;
            for (int i = 0; i < nElements; i++) {
                res += matrix[startPos / matrix[0].length][(startPos + i) % matrix[0].length];
            }
        }

        public long getResult() {
            return this.res;
        }
    }

    @Override
    public double sum(final double[][] matrix) {
        int matrixLength = 0;

        for (double[] i : matrix) {
            matrixLength += i.length;
        }

        final List<Worker> workers = new ArrayList<>(nThreads);
        int elements = 0;
        for (int i = 0; i < nThreads; i++) {
            workers.add(new Worker(matrix, elements, elements + matrixLength / nThreads + (matrixLength % nThreads) < i ? 1 : 0));
        }

        long sum = 0;
        for (final Worker w: workers) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        return 0;
    }

}
