import java.util.*;
import java.io.*;

public class tentaizu {

    static final int SIZE = 7;
    static final int STARS = 10;
    static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    static class Board {
        char[][] grid;
        boolean[][] starPlaced;

        Board(char[][] grid) {
            this.grid = grid;
            this.starPlaced = new boolean[SIZE][SIZE];
        }

        boolean inBounds(int x, int y) {
            return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
        }

        boolean isValidStar(int x, int y) {
            if (grid[x][y] != '.') return false;
            return true;
        }

        boolean checkAndPlaceStar(int x, int y) {
            if (isValidStar(x, y)) {
                starPlaced[x][y] = true;
                return true;
            }
            return false;
        }

        void removeStar(int x, int y) {
            starPlaced[x][y] = false;
        }

        // This checks whether a numbered cell's constraints are satisfied
        boolean checkConstraints() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (Character.isDigit(grid[i][j])) {
                        int number = grid[i][j] - '0';
                        int adjacentStars = countAdjacentStars(i, j);
                        if (adjacentStars != number) return false;
                    }
                }
            }
            return true;
        }

        // Count stars around a given cell
        int countAdjacentStars(int x, int y) {
            int count = 0;
            for (int d = 0; d < 8; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (inBounds(nx, ny) && starPlaced[nx][ny]) {
                    count++;
                }
            }
            return count;
        }

        boolean solve(int starsLeft, int index, List<int[]> emptyCells) {
            if (starsLeft == 0) {
                return checkConstraints();
            }
            if (index >= emptyCells.size()) return false;

            // Prune if not enough cells left to place remaining stars
            if (emptyCells.size() - index < starsLeft) return false;

            int[] cell = emptyCells.get(index);
            int x = cell[0], y = cell[1];

            // Try placing a star at (x, y)
            if (checkAndPlaceStar(x, y)) {
                if (solve(starsLeft - 1, index + 1, emptyCells)) {
                    return true;
                }
                removeStar(x, y);  // Backtrack
            }

            // Try without placing a star
            if (solve(starsLeft, index + 1, emptyCells)) {
                return true;
            }

            return false;
        }

        void printSolution(int boardNumber) {
            System.out.println("Tentaizu Board #" + boardNumber + ":");
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (starPlaced[i][j]) {
                        System.out.print('*');
                    } else {
                        System.out.print(grid[i][j]);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String inputFileName = "in.txt";
        File inputFile = new File(inputFileName);

        if (!inputFile.exists()) {
            System.err.println("Error: The file 'in.txt' was not found in the current directory.");
            return;
        }

        try (Scanner scanner = new Scanner(inputFile)) {
            if (!scanner.hasNextInt()) {
                System.err.println("Error: The input file does not start with an integer indicating the number of boards.");
                return;
            }

            int t = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading t

            for (int boardNumber = 1; boardNumber <= t; boardNumber++) {
                char[][] grid = new char[SIZE][SIZE];
                List<int[]> emptyCells = new ArrayList<>();

                // Read 7 lines for each board
                for (int i = 0; i < SIZE; i++) {
                    // Skip any empty lines (if any) before the board starts
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine().trim();
                        if (!line.isEmpty()) {
                            if (line.length() != SIZE) {
                                System.err.println("Error: Each line of the board must contain exactly 7 characters.");
                                return;
                            }
                            for (int j = 0; j < SIZE; j++) {
                                grid[i][j] = line.charAt(j);
                                if (grid[i][j] == '.') {
                                    emptyCells.add(new int[]{i, j});
                                }
                            }
                            break;
                        }
                    }
                }

                // Consume the blank line between boards (if any)
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) {
                        break;
                    } else {
                        // If not an empty line, it might be the start of the next board
                        // Push it back by using a new Scanner with the remaining input
                        // However, Scanner does not support pushback, so we assume proper formatting
                        // As per the problem, each board is separated by a single blank line
                        break;
                    }
                }

                // Solve the board
                Board board = new Board(grid);
                boolean solved = board.solve(STARS, 0, emptyCells);

                if (solved) {
                    board.printSolution(boardNumber);
                } else {
                    // As per the problem statement, each board has a unique solution
                    // But in case no solution is found, handle gracefully
                    System.out.println("Tentaizu Board #" + boardNumber + ":");
                    for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++) {
                            System.out.print(grid[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to open the file 'in.txt'.");
        }
    }
}