import java.util.*;

public class Matrix {
    int[][] matrix;
    int amountFull;
    int size;
    boolean hasConflict;

    ArrayList<Position> candidates;

    public Matrix(int size, int filling) {
        this.candidates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                candidates.add(new Position(i, j));
            }
        }

        this.matrix = new int[size][size];
        this.size = size;
        this.amountFull = (int) (((float) (size * size) / 100) * filling);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }

        FillMatrix();
        testConflict();
    }


    private void FillMatrix() {
        Random rand = new Random();
        int candidate;
        for (int i = 0; i < amountFull; i++) {
            Position candidatePos;
            candidate = rand.nextInt(candidates.size());
            candidatePos = candidates.get(candidate);
            matrix[candidatePos.getI()][candidatePos.getJ()] = 1;
            candidates.remove(candidatePos);
        }
    }

    public void testConflict() {
        transformMatrix();
        hasConflict =true;
        for (int i = 0; i < size/2 ; i++) {
            for (int j =size/2; j <size ; j++) {
                if (matrix[i][j]!=0){
                    hasConflict =false;
                }
            }

        }

    }

    public boolean hasConflict() {
        return hasConflict;
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void transformMatrix() {
        int[] tempi = new int[size];
        int[] tempj = new int[size];

        for (int k = 0; k < size; k++) {

            //Step1 sum by line
            int Mini = k;
            for (int i = k; i < size; i++) {
                tempj[i] = 0;
                for (int j = k; j < size; j++) {
                    tempj[i] = tempj[i] + matrix[i][j];
                }
                if (tempj[Mini] > tempj[i]) {
                    Mini = i;
                }
            }


            //Step2 change MinLine with first
            int temp1;
            for (int j = 0; j < size; j++) {
                temp1 = matrix[Mini][j];
                matrix[Mini][j] = matrix[k][j];
                matrix[k][j] = temp1;
            }
            //System.out.println(String.format("Swaped rows %d with %d", k, Mini));


            //Step 3 sum  by column with the first 1
            int Maxj = k;
            for (int j = k; j < size; j++) {
                tempi[j] = 0;
                if (matrix[k][j] == 1) {
                    for (int i = k; i < size; i++) {
                        tempi[j] = tempi[j] + matrix[i][j];
                    }
                    if (tempi[j] > tempi[Maxj]) {
                        Maxj = j;
                    }
                }
            }

            //Step 4 change MaxColumn with first
            int temp2;
            for (int i = 0; i < size; i++) {
                temp2 = matrix[i][Maxj];
                matrix[i][Maxj] = matrix[i][k];
                matrix[i][k] = temp2;
            }
            //System.out.println(String.format("Swaped columns %d with %d", k, Maxj));

        }

    }
}
