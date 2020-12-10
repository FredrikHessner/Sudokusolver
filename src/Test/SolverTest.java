package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Sudoku.Solver;

class SolverTest {
	private Solver sudokuBoard;

	@BeforeEach
	void setUp() {
		sudokuBoard = new Solver();
	}

	@AfterEach
	void tearDown() {
		sudokuBoard = null;
	}
	
	@Test
	void testEmptyBoard() {
		assertTrue(sudokuBoard.solve());
	}
	
	@Test
	void testImpossibleBoard() {
		/**
		 * 1 2 3 | 0 0 0 | 0 0 0
		 * 4 5 6 | 0 0 0 | 0 0 0
		 * 0 0 0 | 7 0 0 | 0 0 0
		 * ....
		 */
		sudokuBoard.setNumber(0, 0, 1);
		sudokuBoard.setNumber(0, 1, 2);
		sudokuBoard.setNumber(0, 2, 3);
		sudokuBoard.setNumber(1, 0, 4);
		sudokuBoard.setNumber(1, 1, 5);
		sudokuBoard.setNumber(1, 2, 6);
		sudokuBoard.setNumber(2, 3, 7);
		assertFalse(sudokuBoard.solve());
	}
	
	@Test
	void insertSameNumberRowAndCol() {
		assertTrue(sudokuBoard.trySetNumber(2, 2, 3));
		sudokuBoard.setNumber(2, 2, 3);
		assertFalse(sudokuBoard.trySetNumber(2, 7, 3));
		assertFalse(sudokuBoard.trySetNumber(6, 2, 3));
	}
	
	@Test
	void insertSameNumberSameSquare() {
		sudokuBoard.setNumber(0, 0, 7);
		assertFalse(sudokuBoard.trySetNumber(2, 2, 7));
		sudokuBoard.setNumber(7, 7, 7);
		assertFalse(sudokuBoard.trySetNumber(7, 6, 7));
	}
	
	@Test
	void getAndRemove() {
		int[][] testBoard = new int[9][9];
		testBoard[2][7] = 9;
		testBoard[5][8] = 1;
		sudokuBoard.setNumber(2, 7, 9);
		sudokuBoard.setNumber(5, 8, 1);
		assertArrayEquals(testBoard, sudokuBoard.getNumbers());
		
		testBoard[2][7] = 0;
		sudokuBoard.removeNumber(2, 7);
		assertArrayEquals(testBoard, sudokuBoard.getNumbers());
	}
	
	@Test
	void clear() {
		int[][] testBoard = new int[9][9];
		sudokuBoard.setNumber(2, 7, 9);
		sudokuBoard.setNumber(5, 8, 1);
		sudokuBoard.clear();
		assertArrayEquals(testBoard, sudokuBoard.getNumbers());
	}

}
