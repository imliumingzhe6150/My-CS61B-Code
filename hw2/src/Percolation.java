import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[] open;
    private int openCount;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        open = new boolean[N*N];
        openCount = 0;
        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (open[xyTo1D(row, col)]) {
            return;
        }
        open[xyTo1D(row, col)] = true;
        openCount++;
        if (row == 0) {
            uf.union(xyTo1D(row, col), virtualTop);
        }
        if (row == N - 1) {
            uf.union(xyTo1D(row, col), virtualBottom);
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int neighborRow = row + dr[i];
            int neighborCol = col + dc[i];
            if (neighborRow >= 0 && neighborRow < N && neighborCol >= 0 && neighborCol < N) {
                if (open[xyTo1D(neighborRow, neighborCol)]) {
                    uf.union(xyTo1D(neighborRow, neighborCol), xyTo1D(row, col));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return open[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        return uf.connected(xyTo1D(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openCount;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.connected(virtualTop, virtualBottom);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
