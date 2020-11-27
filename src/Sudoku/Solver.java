package Sudoku;

import java.util.Random;

public class Solver implements SudokuSolver {
	int[][] grid = new int[9][9];

	@Override
	public void clear() {
		grid = new int[9][9];

	}

	@Override
	public void setNumber(int row, int col, int number) {
		grid[row][col] = number;

	}

	@Override
	public boolean trySetNumber(int row, int col, int number) {
		for (int squareRow = 0; squareRow < 3; squareRow++) {
			for (int squareCol = 0; squareCol < 3; squareCol++) {
				int divideRow = row / 3;
				int divideCol = col / 3;
				
				int rowIndex = divideRow * 3 + squareRow;
				int colIndex = divideCol * 3 + squareCol;
				//Don't check with it self
				if (grid[rowIndex][colIndex] == number && !(rowIndex == row && colIndex == col)) {
					return false;
				}
			}
		}
		for (int i = 0; i < grid.length; i++) {
			if (grid[row][i] == number || grid[i][col] == number) {
				//Don't check with it self
				if(!(i == row || i == col)) {
					return false;
				}
			}

		}
		return true;
	}

	@Override
	public int getNumber(int row, int col) {
		return grid[row][col];
	}

	@Override
	public void removeNumber(int row, int col) {
		grid[row][col] = 0;

	}

	@Override
	public boolean solve() {
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid.length; c++) {
				if(getNumber(r,c) != 0 && !trySetNumber(r,c,getNumber(r,c))) {
					return false;
				}
			}
		}
		return solve(0, 0);
	}

	private boolean solve(int r, int c) {
		int nextRow = r;
		int nextCol = c + 1;
		System.out.println(r + " " + c);
		if (nextCol == 9) {
			nextRow = r + 1;
			nextCol = 0;
		}
		if (nextRow == 9 && nextCol == 1) {
			return true;
		}
		if (getNumber(r, c) == 0) {
			for (int nextNumber = 1; nextNumber < 10; nextNumber++) {
				if (trySetNumber(r, c, nextNumber)) {
					setNumber(r, c, nextNumber);
					if (solve(nextRow, nextCol)) {
						return true;
					} else {
						setNumber(r, c, 0);
					}
				}
			}
		} else {
			if (solve(nextRow, nextCol)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public int[][] getNumbers() {
		return grid;
	}

	@Override
	public void setNumbers(int[][] numbers) {
		grid = numbers;
	}

}
