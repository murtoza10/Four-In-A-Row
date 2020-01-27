import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Pavel
 */
public class NInARow {
	static Random random = new Random();

	public static void main(String[] args) {
		printj("Default is 7x6 Change?[Y/N]");
		int width = 7;
		int height = 6;
                int difficulty;
                int userLastmove=-1;
		if (getBoolean("Y", "N")) {
			printj("width of the board");
			width = getInput(0, 100);
			printj("height of the board");
			height = getInput(0, 100);
		}

		printj("Default is 4 in a row.Change? [Y/N]");
		int winLength = 4;
		if (getBoolean("Y", "N")) {
			printj("number of stones required to win");
			winLength =getInput(2, Math.max(width, height));
		}
                printj("Do you want to select Difficulty?Default is Intermediate");
                printj("1.Easy\n2.Intermediate\n3.Hard");
                difficulty=getInput(0, 4);
		printj("The game starts now");
		boolean turn = getRandomBoolean();
		if (turn) {
			printj("You are first to go!");
		} else {
			printj("AI has the first move!");
		}

		Board board =new Board(height, width,winLength);
		Ai ai = new Ai(board,difficulty);
		int column = 0;
		while (!board.hasWinner() && !board.Tie()) {
			printj("");
			printj(board.toString());
			if (turn) {
				do {
					printj("In which column do you want to place your stone?");
					column =getInput(1, width) - 1;
                                        userLastmove=column;
					if (!board.isValidMove(column)) {
						printj("You cannot place your stone in that column!");
					}
				} while (!board.isValidMove(column));
				board.makeMovePlayer(column);
			} else {
				int aiColumn = ai.makeTurn(userLastmove);
				printj("Your opponent placed in column "+ (aiColumn + 1));
			}
			turn = !turn;
		}

		printj("");
		printj(board.toString());
		if (board.playerIsWinner()) {
			printj("You win!");
		} else if (board.Tie()) {
			printj("It's a tie, I think that is the best you can do!");
		} else {
			printj("AI wins.Try again");
		}
	}

	static void printj(String text) {
		System.out.println(text);
	}

	static boolean getRandomBoolean() {
		return random.nextBoolean();
	}
        final static Scanner scanner = new Scanner(System.in);

	public static int getInput(int from, int to) {
		int result = 0;
		boolean noException;
		String inputString;
		do {
			noException = true;
			inputString = scanner.nextLine();
			try {
				result = Integer.parseInt(inputString);
			} catch (NumberFormatException e) {
				noException = false;
			}
		} while (result < from || result > to || !noException);
		return result;
	}

	public static boolean getBoolean(String trueInd, String falseInd) {
		String inputString;
		do {
			inputString = scanner.nextLine(); 
		} while (!inputString.equals(trueInd) && !inputString.equals(falseInd));
		return inputString.equals(trueInd);
}
}
 