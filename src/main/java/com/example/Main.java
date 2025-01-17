package com.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Main {
	private static int ROW_COUNT = 3;
	private static int COL_COUNT = 3;
	private static String CELL_STATE_EMPTY = " ";
	private static String CELL_STATE_x = "x";
	private static String CELL_STATE_0 = "0";
	private static String GAME_STATE_x_WON = "x Won";
	private static String GAME_STATE_0_WON = "0 Won";
	private static String GAME_STATE_DRAW = "Draw";
	private static String GAME_STATE_NOT_FINISHED = "Game not finished";
	private static Scanner scanner = new Scanner(System.in);
	private static Random random = new Random();

	public static void main(String[] args) {
		startGameRound();

	}

	public static void startGameRound() {
		String[][] board = createBoard();
		startGameLoop(board);

	}

	public static void startGameLoop(String[][] board) {
		boolean playerTurn = true;

		do {
			if (playerTurn) {
				makePlayerTurn(board);
				printBoard(board);
			} else {
				makeBotTurn(board);
				printBoard(board);
			}
			playerTurn = !playerTurn;
			System.out.println("\n");

			String gameState = checkGameState(board);
			if (gameState != GAME_STATE_NOT_FINISHED) {
				System.out.println(gameState);
				return;
			}
		} while (true);

	}

	public static String[][] createBoard() {
		String[][] board = new String[ROW_COUNT][COL_COUNT];
		for (int row = 0; row < ROW_COUNT; row++) {
			for (int col = 0; col < COL_COUNT; col++) {
				board[row][col] = CELL_STATE_EMPTY;
			}
		}
		return board;
	}

	public static void makePlayerTurn(String[][] board) {
		int[] coordinates = inputCellCoordinates(board);
		board[coordinates[0]][coordinates[1]] = CELL_STATE_x;
	}

	public static int[] inputCellCoordinates(String[][] board) {

		System.out.println("Введите координаты через пробел (0-2):");

		do {
			String[] input = scanner.nextLine().trim().split(" ");
			int row = Integer.parseInt(input[0]);
			int col = Integer.parseInt(input[1]);

			if ((row < 0) || (row >= ROW_COUNT) || (col < 0) || (col >= COL_COUNT)) {
				System.out.println("Введено некорректное значение. Введите значение от 0 до 2:");
			} else if (board[row][col] != CELL_STATE_EMPTY) {
				System.out.println("Даная ячейка уже занята");
			} else {
				return new int[] { row, col };
			}
		} while (true);

	}

	public static void makeBotTurn(String[][] board) {
		int[] coordinates = getRandomEmptyCellCoordinates(board);
		board[coordinates[0]][coordinates[1]] = CELL_STATE_0;
	}

	public static int[] getRandomEmptyCellCoordinates(String[][] board) {
		do {
			int row = random.nextInt(ROW_COUNT);
			int col = random.nextInt(COL_COUNT);
			if (board[row][col] != CELL_STATE_EMPTY) {
				return new int[] { row, col };
			}
		} while (true);
	}

	public static String checkGameState(String[][] board) {
		ArrayList<Integer> sums = new ArrayList<>();

		for (int row = 0; row < ROW_COUNT; row++) {
			int rowSum = 0;
			for (int col = 0; col < COL_COUNT; col++) {
				rowSum += calculateNumValue(board[row][col]);
			}
			sums.add(rowSum);
		}

		for (int col = 0; col < COL_COUNT; col++) {
			int colSum = 0;
			for (int row = 0; row < ROW_COUNT; row++) {
				colSum += calculateNumValue(board[row][col]);
			}
			sums.add(colSum);
		}

		int leftDiagonalSum = 0;
		for (int i = 0; i < ROW_COUNT; i++) {
			leftDiagonalSum += calculateNumValue(board[i][i]);
		}
		sums.add(leftDiagonalSum);

		int rightDiagonalSum = 0;
		for (int i = 0; i < ROW_COUNT; i++) {
			rightDiagonalSum += calculateNumValue(board[i][(ROW_COUNT - 1) - i]);
		}
		sums.add(rightDiagonalSum);

		if (sums.contains(3)) {
			return GAME_STATE_x_WON;
		} else if (sums.contains(-3)) {
			return GAME_STATE_0_WON;
		} else if (areAllCellsTaker(board)) {
			return GAME_STATE_DRAW;
		} else {
			return GAME_STATE_NOT_FINISHED;
		}

	}

	private static int calculateNumValue(String cellState) {
		if (cellState == CELL_STATE_x) {
			return 1;
		} else if (cellState == CELL_STATE_0) {
			return -1;
		} else {
			return 0;
		}

	}

	public static boolean areAllCellsTaker(String[][] board) {
		for (int row = 0; row < ROW_COUNT; row++) {
			for (int col = 0; col < COL_COUNT; col++) {
				if (board[row][col] == CELL_STATE_EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	public static void printBoard(String[][] board) {
		System.out.println("---------");
		for (int row = 0; row < ROW_COUNT; row++) {
			String line = "| ";
			for (int col = 0; col < COL_COUNT; col++) {
				line += board[row][col] + " ";
			}
			line += "|";
			System.out.println(line);
		}
		System.out.println("---------");
	}
}
