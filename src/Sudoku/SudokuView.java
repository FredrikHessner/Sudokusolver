package Sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SudokuView {
	Solver sudokuSolver;
	JTextField[][] input;

	public SudokuView(int width, int height) {
		SwingUtilities.invokeLater(() -> createWindow(width, height));
	}

	/**
	 * Private helper method, to confine all Swing-related work to Swing's Event
	 * Dispatch Thread (EDT).
	 */
	private void createWindow(int width, int height) {
		int[][] grid = new int[9][9];
		input = new JTextField[9][9];

		JFrame frame = new JFrame("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton upButton = new JButton("Solve");
		upButton.setFocusPainted(false);
		upButton.addActionListener(e -> {
			sudokuSolver = new Solver();
			for (int r = 0; r < sudokuSolver.getNumbers().length; r++) {
				for (int c = 0; c < sudokuSolver.getNumbers().length; c++) {
						if (input[r][c].getText().length() > 0) {
							sudokuSolver.setNumber(r, c, Integer.parseInt(input[r][c].getText()));
						}
					}
						/*JPanel p = new JPanel();
						JTextArea j = new JTextArea(20, 20);
						j.setToolTipText("Ej lösbar");
						p.add(j);*/
				}
			if (sudokuSolver.solve()) {
				for (int r = 0; r < sudokuSolver.getNumbers().length; r++) {
					for (int c = 0; c < sudokuSolver.getNumbers().length; c++) {
						input[r][c].setText(String.valueOf(sudokuSolver.getNumber(r, c)));
					}
				}
			} else {
				System.out.println("Can't be solved");
			}

		});

		JButton downButton = new JButton("Clear");
		downButton.setFocusPainted(false);
		downButton.addActionListener(e -> {
			for (int r = 0; r < sudokuSolver.getNumbers().length; r++) {
				for (int c = 0; c < sudokuSolver.getNumbers().length; c++) {
					input[r][c].setText(null);
				}
			}
		});

		JPanel commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		commandPanel.add(upButton);
		commandPanel.add(downButton);

		JPanel board = new JPanel();
		board.setPreferredSize(new Dimension(width, height));

		Font font = new Font("SansSerif", Font.BOLD, 20);

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
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
}
