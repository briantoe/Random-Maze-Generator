
package maze;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Brian T
 */
public class main {

    public static void main(String args[]) {
        System.out.print("Enter number of rows and columns: ");
        Scanner in = new Scanner(System.in);
        int rows = in.nextInt();
//        System.out.print("\nEnter number of columns: ");
//        int cols = in.nextInt();
        int cols = rows;
        System.out.println();

        if (rows == 1 && cols == 1) {
            System.out.println("A 1x1 maze cannot be generated.");
            return;
        }
        Cell[] maze1d = new Cell[rows * cols];

        // initialize each cell with their unique coordinates
        //int coordinate = 0;
        for (int i = 0; i < rows * cols; i++) {
            maze1d[i] = new Cell(i);
        }
        DisjSets sets = new DisjSets(rows * cols);

        // while sets are not all in the same set...
        // generate two random numbers and check to see if it is in the set
        // if so, generate a new random number
        // else, union them together
        /*
            WHEN PRINTING : Iterate through each row twice, first time to check the vertical walls, second time to check horizontal walls underneath of the cell

            Marking the walls:
            check to see if the two randomly generated numbers are adjacent
            
            if they are not, generate two numbers again and retry
            if the two numbers are already in the same set, generate two numbers again and retry
        
            if they are adjacent, determine if rand2 is to right, left, above, or below rand1
//                if rand2 is to the right of rand1, diff will be -1. 
//                if rand2 is to the left of rand1, diff will be 1.
//                if rand2 is below rand1, diff will be -rows
//                if rand2 is above rand1, diff will be rows      
         */
        int rand1;
        int rand2;
        final int RIGHT = -1;
        final int LEFT = 1;
        final int BELOW = -rows;
        final int ABOVE = rows;
        int numOfUnions = 0;

        while (((rows * cols) - 1 != numOfUnions) && (sets.find(0) != sets.find((rows * cols) - 1))) // while the entrance and exit are not connected with some path
        {
            // min and max are inclusive
            rand1 = getRandomNumberInRange(0, (rows * cols) - 1);
            rand2 = getRandomNumberInRange(0, (rows * cols) - 1);

            if (rand1 != rand2 && (sets.find(rand1) != sets.find(rand2))) { // if the random numbers are not equal  AND if they are not in the same set

                // literal edge cases
                if (rand1 > rand2) {
                    if (rand1 % cols == 0 && (rand2 + 1) % cols == 0) { // if rand1 is at the leftmost and rand2 is at the rightmost
                        continue;
                    }
                } else {
                    if (rand2 % cols == 0 && (rand1 + 1) % cols == 0) {// if rand2 is at the leftmost and rand1 is at the rightmost
                        continue;
                    }
                }

                int diff = rand1 - rand2;

                if (diff == RIGHT) {    //                if rand2 is to the right of rand1, diff will be -1. 
                    maze1d[rand1].setWall(Wall.VERTICAL);
                    sets.union(sets.find(rand1), sets.find(rand2));
                    numOfUnions++;
                } else if (diff == LEFT) {//                if rand2 is to the left of rand1, diff will be 1.
                    maze1d[rand2].setWall(Wall.VERTICAL);
                    sets.union(sets.find(rand1), sets.find(rand2));
                    numOfUnions++;
                } else if (diff == BELOW) {//                if rand2 is below rand1, diff will be -rows
                    maze1d[rand1].setWall(Wall.HORIZONTAL);
                    sets.union(sets.find(rand1), sets.find(rand2));
                    numOfUnions++;
                } else if (diff == ABOVE) {//                if rand2 is above rand1, diff will be rows
                    maze1d[rand2].setWall(Wall.HORIZONTAL);
                    sets.union(sets.find(rand1), sets.find(rand2));
                    numOfUnions++;
                }
//                } else {
//                    System.out.println("not adjacent");
//                }

            }
        }
//        System.out.println("Size of maze: " + (rows * cols));
//        System.out.println("Number of unions done: " + numOfUnions + "\n");

        Cell maze2d[][] = new Cell[rows][cols];
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze2d[i][j] = new Cell(maze1d[counter]);
                counter++;
            }
        }

        // print the top of the maze
//            System.out.print("-");
//            for(int i = 0; i < cols; i++)
//            {
//                
//            }
//            boolean toggle = true;
//            boolean printType = true;
        // maze
        int width = rows;
        int height = cols;

        System.out.print("-");
        for (int i = 0; i < height; i++) // this prints the top of the maze
        {
            printHorizontalFiller(false);
        }

        // prints the rest of the maze
        boolean firstVertWall = true;
        for (int i = 0; i < width; i++) {
            if (firstVertWall) {
                System.out.print("\n "); // prints entrance
                firstVertWall = false;
            } else {
                System.out.print("\n|"); // prints border wall
            }

            for (int j = 0; j < height; j++) { // printing right walls
                Cell cell = maze2d[i][j];

                Wall wall = cell.getWall();
                printVerticalFiller();
                if (j == height - 1 && i == width - 1) {
                    System.out.print(" "); // prints exit
                    break;
                }
                if (j == height - 1) {
                    System.out.print("|");
                } else {

                    if (wall == Wall.VERTICAL || wall == Wall.BOTH) // if the vertical right wall is deleted
                    {
                        System.out.print(" ");
                    } else // if the vertical right wall remains
                    {
                        System.out.print("|");
                    }
                }

            }

            System.out.print("\n-");
            for (int j = 0; j < height; j++) { // printing bottom walls

                Cell cell = maze2d[i][j];
                Wall wall = cell.getWall();

                if (wall == Wall.HORIZONTAL || wall == Wall.BOTH) // if the bottom wall is deleted
                {
                    printHorizontalFiller(true);    // prints out empty space
                } else// if the bottom wall remains
                {
                    printHorizontalFiller(false);   // prints out the bottom wall
                }

            }
        }
        System.out.println();
    }

    static void printHorizontalFiller(boolean whitespace) {
        String filler;
        if (!whitespace) {
            filler = "-";
        } else {
            filler = " ";
        }
        for (int i = 0; i < 3; i++) {
            System.out.print(filler);
        }
        System.out.print("-"); // print the "corner"
    }

    static void printVerticalFiller() {
        for (int i = 0; i < 3; i++) {
            System.out.print(" ");
        }
    }

    // min and max are inclusive
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
