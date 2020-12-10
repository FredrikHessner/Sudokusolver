package Sudoku;

public class Solver implements SudokuSolver {
	private int[][] grid;

	public Solver() {
		grid = new int[9][9];
	}

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
		//Get each index of the numbers inside the square belonging to the number inside [row][col]
		for (int squareRow = 0; squareRow < 3; squareRow++) {
			for (int squareCol = 0; squareCol < 3; squareCol++) {
				int divideRow = row / 3;
				int divideCol = col / 3;
				
				int rowIndex = divideRow * 3 + squareRow;
				int colIndex = divideCol * 3 + squareCol;
				// Don't check with it self
				if (grid[rowIndex][colIndex] == number && !(rowIndex == row && colIndex == col)) {
					return false;
				}
			}
		}
		//Test the horizontal and vertical axis
		for (int i = 0; i < grid.length; i++) {
			if ((grid[row][i] == number && i != col) || (grid[i][col] == number && i != row)) {
				return false;
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
		//Check if the board is even solvable with current input
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid.length; c++) {
				if (getNumber(r, c) != 0 && !trySetNumber(r, c, getNumber(r, c))) {
					return false;
				}
			}
		}
		return solve(0, 0);
	}

	private boolean solve(int r, int c) {
		int nextRow = r;
		int nextCol = (c + 1) % 9;
		if (nextCol == 0) {
			nextRow++;
		}
		
		//Finished with the whole board
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
						removeNumber(r, c);
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
