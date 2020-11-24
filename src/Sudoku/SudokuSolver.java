package Sudoku;

public interface SudokuSolver {
	/** Tömmer hela sudokut */
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
	
	/** Kollar om siffran number kan sÃ¤ttas i raden row och kolumnen col, 
	 * om det inte går enligt spelreglerna returneras false 
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is outside the allowed range
	 */
	boolean trySetNumber(int row, int col, int number);
	
	/** Returnerar siffran på raden row och kolumnen col. 
	 * @throws IllegalArgumentException if row or col is outside the allowed range
	 */
	int getNumber(int row, int col);
	
	/** Tar bort siffran på raden row och kolumnen col. 
	 * @throws IllegalArgumentException if row or col is outside the allowed range
	 */
	void removeNumber(int row, int col);

	/** Löser sudokut och returnerar true om sudokut går att läsa. */
	boolean solve();
	
	/** Returnerar siffrorna i sudokut. */
	int[][] getNumbers();
	
	/** Fyller i siffrorna i numbers i sudokut. 
	 * @throws IllegalArgumentException if not all numbers in [0..9]
	 **/
	void setNumbers(int[][] numbers);
}

/* TBD:
 * Numrering av row och col: 0-8 eller 1-9? Senaste förslag 0-8
 * Skriva javadoc-kommentarer (Påbörjat, kommentaren till setNumber är nästan klar.)
 */