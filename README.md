# Tentaizu Solver
This is a Java program that solves Tentaizu puzzles, a logic-based game similar to Minesweeper. Tentaizu is a Japanese game whose name means “celestial map.” The game is played on a 7x7 grid, where 10 stars are hidden. The objective is to locate these stars based on the number clues provided in some cells of the board. This program reads multiple Tentaizu boards from an input file and outputs the solution for each board.

## Features
- Solves multiple 7x7 Tentaizu boards using backtracking.
- Each board is guaranteed to have a unique solution.
- Input is read from a file (in.txt) and the solved boards are printed to the console.

## Input Format
The input file in.txt should be formatted as follows:

- The first line contains an integer t, representing the number of Tentaizu boards.
- Each board is represented by 7 lines, where each line contains exactly 7 characters:
- A number (0-8) indicates how many stars are adjacent (including diagonally) to that cell.
- A . represents an empty cell, which may or may not contain a star.
- Each board is separated by a blank line.

## Output Format
For each Tentaizu board, the program outputs the solved board with stars (*) placed in the correct positions. The output follows this format:
The line Tentaizu Board #x: where x is the board number (starting from 1).
The solved board, with stars (*) representing the hidden stars.
Each solved board is followed by a blank line.

# How to Use

## Requirements
Java Development Kit (JDK) 8 or higher installed on your system.

## Steps to Run:
- Clone or download the repository to your local machine.
- Create an input file named in.txt in the same directory as the program with the appropriate input format.
- Compile the program.

# Code Overview
The core logic for solving the Tentaizu puzzle is implemented using backtracking. The program attempts to place stars on the board while ensuring the number clues are respected. If a placement leads to a dead end, the algorithm backtracks and tries another configuration.

## Key methods:

- solve(): Uses backtracking to find the correct placement of 10 stars.
- checkConstraints(): Ensures the stars placed around numbered cells match the required count.
- printSolution(): Outputs the solved board.

## Customization
You can modify the input file (in.txt) to include more puzzles and test the program with different configurations. Just follow the input format to ensure proper execution.

## Limitations
The program assumes that each puzzle has a unique solution, as per the problem statement.
It only solves 7x7 boards with exactly 10 stars.



