
public class Basics {

	public volatile int move;
	private int[][] board;

	public Basics() {
		board = new int[3][3];
		move = 1;
	}

	public void reset() {
		board = new int[3][3];
		move = 1;
	}

	public boolean validMove(int i, int j) {
		// make sure space is not already used
		if (this.board[i][j] != 0) {
			return false;
		} else {
			// occupy the board space, invert move, return true to state move was made
			this.board[i][j] = this.move;
			this.move = -this.move;
			return true;
		}
	}

	public String printBoard() {
		String boardRow = "#";
		for (int i = 0; i < 3; ++i) {
			// concatenate row into string, and add following rows in same fashion
			boardRow += Integer.toString(this.board[i][0]) + "," + Integer.toString(this.board[i][1]) + ","
					+ Integer.toString(this.board[i][2]) + ";";
		}
		return boardRow;
	}

	public int win() {
		boolean winner = true;
		for (int i = 0; i < 3; ++i) {
			// check for three in a row horizontal
			if ((this.board[i][0] == this.board[i][1] && this.board[i][0] == this.board[i][2])
					&& this.board[i][0] != 0) {
				return this.board[i][0];
			}
		}

		for (int i = 0; i < 3; ++i) {
			// check for three in a row horizontal
			if ((this.board[0][i] == this.board[1][i] && this.board[0][i] == this.board[2][i])
					&& this.board[0][i] != 0) {
				return this.board[0][i];
			}
		}
		// check for upper right to lower left diagonal
		if ((this.board[0][0] == this.board[1][1] && this.board[0][0] == this.board[2][2]) && this.board[0][0] != 0) {
			return this.board[0][0];
		}
		// check for lower right to upper left diagonal
		if ((this.board[2][0] == this.board[1][1] && this.board[2][0] == this.board[0][2]) && this.board[2][0] != 0) {
			return this.board[2][0];
		}

		// no winner and spaces still on board
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (this.board[i][j] == 0) {
					winner = false;
				}
			}
		}
		if (winner) {
			return 2;
		} else {
			return 0;
		}
	}

}
