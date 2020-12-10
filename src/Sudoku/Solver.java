package Sudoku;

public class Solver implements SudokuSolver {
	private int[][] grid;

	/** Creates new Solver */
	public Solver() {
		grid = new int[9][9];
	}

	/** Clears the whole sudoku */
	@Override
	public void clear() {
		grid = new int[9][9];

	}

	/**
	 * Sets the digit number in the box row, col.
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is outside the allowed range
	 */
	@Override
	public void setNumber(int row, int col, int number) {
		isValidInput(row, col, number);
		grid[row][col] = number;
	}

	/**
	 * Checks weather number can be put in the row and col, return false if not
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @throws IllegalArgumentException If number not in [1..9] or row or col is outside the allowed range
	 * @return boolean Returns true if number can be placed in row col
	 */
	@Override
	public boolean trySetNumber(int row, int col, int number) {
		isValidInput(row, col, number);
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

	/**
	 * Returns the number in row - col
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @return Integer The digit in row - col
	 * @throws IllegalArgumentException if row or col is outside the allowed range [0..8]
	 */
	@Override
	public int getNumber(int row, int col) {
		isValidInput(row, col);
		return grid[row][col];
	}

	/**
	 * Removes the number in row - col
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @throws IllegalArgumentException if row or col is outside the allowed range [0..8]
	 */
	@Override
	public void removeNumber(int row, int col) {
		isValidInput(row, col);
		grid[row][col] = 0;
	}

	/**
	 * Solves the sudoku and returns true if possible
	 * @return Returns true if sudoku can be solved
	 */
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

	/**
	 * Solves the sudoku by recursion
	 * @param r 	The row
	 * @param c		The col
	 * @return boolean If r & c can be solved it will return true
	 */
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

	/**
	 * Returns all the numbers in the sudoku grid
	 * 
	 * @return int[][] All numbers in grid 9x9
	 */
	@Override
	public int[][] getNumbers() {
		return grid;
	}

	/**
	 * Set the numbers in the grid
	 * 
	 * @param numbers[][]	The numbers in the grid
	 * @throws IllegalArgumentException if not all numbers in [1..9]
	 **/
	@Override
	public void setNumbers(int[][] numbers) {
		for(int r = 0; r < numbers.length; r++) {
			for(int c = 0; c < numbers[r].length; c++) {
				isValidInput(r, c, numbers[r][c]);
			}
		}
		grid = numbers;
	}
	
	/**
	 * Checks if row & col are valid numbers
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @throws IllegalArgumentException if row or col is outside the allowed range [0..8]
	 */
	private void isValidInput(int row, int col) {
		if(row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Row and col can only be between 0-8");
		}
	}
	
	/**
	 * Checks if row, col & number are valid numbers
	 * 
	 * @param row    	The row
	 * @param col    	The column
	 * @param number	The number
	 * @throws IllegalArgumentException if row, col or number are outside the allowed range [0..8]
	 */
	private void isValidInput(int row, int col, int number) {
		if(row < 0 || row > 8 || col < 0 || col > 8 || number < 1 || number > 9) {
			throw new IllegalArgumentException("Row and col can only be between 0-8 and number between 1-9");
		}
	}

}
