package Sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SudokuView {
	Solver sudokuSolver;
	JTextField[][] input;
	JFrame frame;
	SudokuGenerator generator;

	public SudokuView(int width, int height) {
		SwingUtilities.invokeLater(() -> createWindow(width, height));
	}

	/**
	 * Private helper method, to confine all Swing-related work to Swing's Event
	 * Dispatch Thread (EDT).
	 */
	private void createWindow(int width, int height) {
		input = new JTextField[9][9];
		sudokuSolver = new Solver();

		frame = new JFrame("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton upButton = new JButton("Solve");
		upButton.setFocusPainted(false);
		upButton.addActionListener(e -> solveSudoku());

		JButton downButton = new JButton("Clear");
		downButton.setFocusPainted(false);
		downButton.addActionListener(e -> {
			sudokuSolver.clear();
			for (int r = 0; r < input.length; r++) {
				for (int c = 0; c < input.length; c++) {
					input[r][c].setText(null);
				}
			}
		});

		JButton leftButton = new JButton("Generate grid");
		leftButton.setFocusPainted(false);
		leftButton.addActionListener(e -> generateSudoku());

		JPanel commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		commandPanel.add(upButton);
		commandPanel.add(downButton);
		commandPanel.add(leftButton);

		JPanel board = new JPanel();
		board.setPreferredSize(new Dimension(width, height));

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				initTextField(row, col);
				board.add(input[row][col]);
			}
		}

		GridLayout boardGrid = new GridLayout(9, 9);
		boardGrid.setHgap(5);
		boardGrid.setVgap(5);
		board.setLayout(boardGrid);
		board.setBorder(new EmptyBorder(10, 10, 10, 10));

		frame.add(commandPanel, BorderLayout.SOUTH);
		frame.add(board, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
	
	private void solveSudoku() {
		sudokuSolver.clear();
		if (fetchAllNumbers()) {
			if (sudokuSolver.solve()) {
				for (int r = 0; r < sudokuSolver.getNumbers().length; r++) {
					for (int c = 0; c < sudokuSolver.getNumbers().length; c++) {
						input[r][c].setText(String.valueOf(sudokuSolver.getNumber(r, c)));
					}
				}
			} else {
				showDialog("Sorry", "This can't be solved");
			}

		}	
	}
	
	private void generateSudoku() {
		generator = new SudokuGenerator();
		generator.generateGrid();
		int[][] generatedGrid = generator.getGeneratedGrid();
		for (int r = 0; r < generatedGrid.length; r++) {
			for (int c = 0; c < generatedGrid.length; c++) {
				if (generatedGrid[r][c] == 0) {
					input[r][c].setText(null);
				} else {
					input[r][c].setText(String.valueOf(generatedGrid[r][c]));
				}
			}
		}

	}

	private boolean fetchAllNumbers() {
		for (int r = 0; r < sudokuSolver.getNumbers().length; r++) {
			for (int c = 0; c < sudokuSolver.getNumbers().length; c++) {
				if (input[r][c].getText().length() > 0) {
					if(stringToInteger(input[r][c].getText()) != null) {					
						int n = stringToInteger(input[r][c].getText());
						
						if (n > 0 && n < 10) {
							sudokuSolver.setNumber(r, c, n);
						} else {
							showDialog("Almost", "Only numbers between 1-9");
							return false;
						}
						
					} else {
						showDialog("STOP", "Only numbers");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void initTextField(int row, int col) {
		Font font = new Font("SansSerif", Font.BOLD, 20);
		
		input[row][col] = new JTextField();
		input[row][col].setBorder(new EmptyBorder(5, 5, 5, 5));
		input[row][col].setHorizontalAlignment(SwingConstants.CENTER);
		input[row][col].setFont(font);
		input[row][col].setText("");

		// Fill if square index is even
		int divideRow = row / 3;
		int divideCol = col / 3;
		if ((divideRow + divideCol) % 2 == 0) {
			input[row][col].setBackground(Color.DARK_GRAY);
			input[row][col].setForeground(Color.white);
		}
	}
	
	private Integer stringToInteger(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException err) {
			return null;
		}
	}

	private void showDialog(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
