import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static final int iterations =300;
    private static final HashSet<Integer> N= new HashSet<>();

    public static void main(String[] args) {
        N.add(10);
        N.add(15);
        N.add(20);
        N.add(25);
        N.add(30);

        int sizeNum;
        while (true) {
            System.out.println("Enter matrix size from the set {10, 15, 20, 25, 30}:");
            Scanner in = new Scanner(System.in);
            sizeNum = in.nextInt();
            if (!N.contains(sizeNum)){
                System.out.print("Error in matrix size\n");
            }else{break;}
        }

        for (int i = 1; i <= 100; i++) {
            int conflictNumber = 0;
            for (int j = 0; j < iterations; j++) {
                Matrix matrix = new Matrix(sizeNum, i);
                if (matrix.hasConflict()) {
                    conflictNumber++;
                }
            }
            System.out.println(String.format("%d. Number of conflict assignments: %d. Per: %.1f%%", i, conflictNumber, (float) conflictNumber / iterations * 100));
        }

    }


}
