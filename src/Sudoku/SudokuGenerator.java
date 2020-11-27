package Sudoku;

import java.util.Random;

public class SudokuGenerator {
	private Solver solver;
	private Random rand;

	public SudokuGenerator() {
		solver = new Solver();
		rand = new Random();
	}

	public void generateGrid() {
		for (int rowcol = 0; rowcol < 3; rowcol++) {
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 3; c++) {
					int randInt = rand.nextInt(9) + 1;
					while (!solver.trySetNumber(r + (rowcol * 3), c + (rowcol * 3), randInt)) {
						randInt = rand.nextInt(9) + 1;
					}
					solver.setNumber(r + (rowcol * 3), c + (rowcol * 3), randInt);
				}
			}
		}
		solver.solve();

		for (int removeClues = 0; removeClues < 50; removeClues++) {
			solver.setNumber(rand.nextInt(9), rand.nextInt(9), 0);

		}

	}

	public int[][] getGeneratedGrid() {
		return solver.getNumbers();
	}

}
