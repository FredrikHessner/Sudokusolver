package Sudoku;

public interface SudokuSolver {
	/** Clears the whole sudoku */
	void clear(); 

	/**
	 * Sets the digit number in the box row, col.
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is outside the allowed range
	 */
	void setNumber(int row, int col, int number);
	
	/**
	 * Checks weather number can be put in the row and col, return false if not
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is outside the allowed range
	 * @return boolean Returns true if number can be placed in row col
	 */
	boolean trySetNumber(int row, int col, int number);
	
	/**
	 * Returns the number in row - col
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @return Integer The digit in row - col
	 * @throws IllegalArgumentException if row or col is outside the allowed range [0..8]
	 */
	int getNumber(int row, int col);
	
	/**
	 * Removes the number in row - col
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @throws IllegalArgumentException if row or col is outside the allowed range [0..8]
	 */
	void removeNumber(int row, int col);

	/**
	 * Solves the sudoku and returns true if possible
	 * @return Returns true if sudoku can be solved
	 */
	boolean solve();
	
	/**
	 * Returns all the numbers in the sudoku grid
	 * 
	 * @return int[][] All numbers in grid 9x9
	 */
	int[][] getNumbers();
	
	/**
	 * Set the numbers in the grid
	 * 
	 * @param numbers[][]	The numbers in the grid
	 * @throws IllegalArgumentException if not all numbers in [1..9]
	 **/
	void setNumbers(int[][] numbers);
}