import java.util.ArrayList;
import java.util.Random;

public class Matrix {
    ArrayList<ArrayList<Integer>> rows;
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

        this.rows = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            rows.add(row);
        }

        this.size = size;
        this.amountFull = (int) (((float) (size * size) / 100) * filling);
        fill();
        stripExplicit();
        testConflict();
    }


    private void fill() {
        Random rand = new Random();
        int candidate;
        for (int i = 0; i < amountFull; i++) {
            Position candidatePos;
            candidate = rand.nextInt(candidates.size());
            candidatePos = candidates.get(candidate);
            set(candidatePos.getI(), candidatePos.getJ(), 1);
            candidates.remove(candidatePos);
        }
    }

    private void stripExplicit() {
        boolean flag;
        int lastI = 0, lastJ = 0;
        do {
            flag = false;
            for (int i = 0; i < size; i++) {
                int counter = 0;
                for (int j = 0; j < size; j++) {
                    if (get(i, j) == 1) {
                        counter++;
                        lastJ = j;
                    }
                }
                if (counter == 1) {
                    delete(i, lastJ);
                    flag = true;
                }
            }

            for (int j = 0; j < size; j++) {
                int counter = 0;
                for (int i = 0; i < size; i++) {
                    if (get(i, j) == 1) {
                        counter++;
                        lastI = i;
                    }
                }
                if (counter == 1) {
                    delete(lastI, j);
                    flag = true;
                }
            }
        } while (flag);
    }

    public void testConflict() {
        transformMatrix();
        hasConflict = false;
        for (int k = 1; k < size - 1; k++) {
            if (get(k, k) == 1) {
                for (int i = 0; i <= k; i++) {
                    for (int j = k; j < size; j++) {
                        if (get(i, j) == 1 && (i != k || j != k))
                            return;
                    }
                }
                hasConflict = true;
            }
        }
    }

    public boolean hasConflict() {
        return hasConflict;
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(get(i, j) + "  ");
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
                    tempj[i] = tempj[i] + get(i, j);
                }
                if (tempj[Mini] > tempj[i]) {
                    Mini = i;
                }
            }


            //Step2 change MinLine with first
            int temp1;
            for (int j = 0; j < size; j++) {
                temp1 = get(Mini, j);
                set(Mini, j, get(k, j));
                set(k, j, temp1);
            }
            //System.out.println(String.format("Swaped rows %d with %d", k, Mini));


            //Step 3 sum  by column with the first 1
            int Maxj = k;
            for (int j = k; j < size; j++) {
                tempi[j] = 0;
                if (get(k, j) == 1) {
                    for (int i = k; i < size; i++) {
                        tempi[j] = tempi[j] + get(i, j);
                    }
                    if (tempi[j] > tempi[Maxj]) {
                        Maxj = j;
                    }
                }
            }

            //Step 4 change MaxColumn with first
            int temp2;
            for (int i = 0; i < size; i++) {
                temp2 = get(i, Maxj);
                set(i, Maxj, get(i, k));
                set(i, k, temp2);
            }
            //System.out.println(String.format("Swaped columns %d with %d", k, Maxj));

        }

    }

    public int get(int i, int j) {
        return rows.get(i).get(j);
    }

    public void set(int i, int j, int elem) {
        rows.get(i).set(j, elem);
    }

    public void delete(int i, int j) {
        rows.remove(i);
        rows.forEach(row->row.remove(j));
        size--;
    }
}
