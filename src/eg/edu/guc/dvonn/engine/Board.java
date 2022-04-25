package eg.edu.guc.dvonn.engine;

public class Board implements BoardInterface {
	private Height[][] pos;
	private int blackSize;
	private int whiteSize;
	private int redSize;
	private int rows2;
	private int columns2;
	private boolean turn = true;
	private boolean linked;

	public Height[][] getPos() {
		return pos;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setPos(Height[][] pos) {
		this.pos = pos;
	}

	public Board(int rows, int columns) {
		pos = new Height[rows][columns];
		blackSize = ((rows * columns) - 3) / 2;
		whiteSize = ((rows * columns) - 3) / 2;
		redSize = 3;
		rows2 = rows;

		columns2 = columns;
		linked = false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				pos[i][j] = new Height();
			}
		}
	}

	public boolean checkmovess(int rowStart, int colStart) {
		int height = pos[rowStart][colStart].size();
		int color = getColor(rowStart, colStart);
		if (!(isEmpty(rowStart - height, colStart - height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart - height, colStart))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart - height, colStart + height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart, colStart - height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart, colStart + height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart + height, colStart - height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart + height, colStart + height))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		if (!(isEmpty(rowStart + height, colStart))) {
			if (color == 1 || color == 2) {
				return true;
			}
		}
		return false;
	}

	public void fillRandomHelperRandom0(int i, int j) {
		if (redSize != 0) {
			pos[i][j].add(0);
			redSize--;
		} else {
			if (blackSize != 0) {
				pos[i][j].add(2);
				blackSize--;
			} else {
				if (whiteSize != 0) {
					pos[i][j].add(1);
					whiteSize--;
				}
			}
		}
	}

	public void fillRandomHelperRandom1(int i, int j) {
		if (blackSize != 0) {
			pos[i][j].add(2);
			blackSize--;
		} else {
			if (whiteSize != 0) {
				pos[i][j].add(1);
				whiteSize--;
			} else {
				if (redSize != 0) {
					pos[i][j].add(0);
					redSize--;
				}
			}
		}
	}

	public void fillRandomHelperRandom2(int i, int j) {
		if (whiteSize != 0) {
			pos[i][j].add(1);
			whiteSize--;
		} else {
			if (blackSize != 0) {
				pos[i][j].add(2);
				blackSize--;
			} else {
				if (redSize != 0) {
					pos[i][j].add(0);
					redSize--;
				}
			}
		}
	}

	public void fillRandom() {
		for (int i = 0; i < rows2; i++) {
			for (int j = 0; j < columns2; j++) {
				int k = (int) (Math.random() * 3);
				if (k == 0) {
					fillRandomHelperRandom0(i, j);
				}
				if (k == 1) {
					fillRandomHelperRandom1(i, j);
				}
				if (k == 2) {
					fillRandomHelperRandom2(i, j);
				}
			}
		}
	}

	public int getCurrentPhase() {
		if (redSize == 0 && blackSize == 0 && whiteSize == 0) {
			return 1;
		}
		return 0;
	}

	public boolean put(int row, int col) {
		if (pos[row][col].size() == 0) {
			if (redSize != 0) {
				pos[row][col].add(0);
				redSize--;
				return true;
			}
			if (whiteSize != blackSize) {
				if (whiteSize != 0) {
					pos[row][col].add(1);
					whiteSize--;
					return true;
				}
			} else {
				if (blackSize != 0) {
					pos[row][col].add(2);
					blackSize--;
					return true;
				}
			}
		}
		return false;
	}

	public Height adder(Height x, Height y) {
		for (int i = 0; i < x.size(); i++) {
			y.add(x.getI(i));
		}
		return y;
	}

	public void removeCell() {
		for (int i = 0; i < rows2; i++) {
			for (int j = 0; j < columns2; j++) {
				if (!isEmpty(i, j)) {
					for (int a = 0; a < rows2; a++) {
						for (int b = 0; b < columns2; b++) {
							pos[a][b].setB(false);
						}
					}
					if (!isLinked(i, j)) {
						pos[i][j].delete();
					}
				}
			}
		}
	}

	public boolean isLinked(int i, int j) {
		if (i < 0 || j < 0 || i > rows2 - 1 || j > columns2 - 1) {
			linked = false;
		} else if (hasRed(i, j)) {
			linked = true;
		} else if (pos[i][j].isB()) {
			return linked;
		} else if (isEmpty(i, j)) {
			linked = false;
		} else {
			pos[i][j].setB(true);
			linked = isLinked(i - 1, j - 1) || isLinked(i - 1, j)
					|| isLinked(i - 1, j + 1) || isLinked(i, j + 1)
					|| isLinked(i, j) || isLinked(i, j - 1)
					|| isLinked(i + 1, j - 1) || isLinked(i + 1, j)
					|| isLinked(i + 1, j + 1);
		}
		return linked;
	}

	public boolean moveHelper(int rowStart, int colStart, int rowEnd, int colEnd) {
		int height = pos[rowStart][colStart].size();
		for (int i = rowStart - height; i <= rowStart + height; i = i + height) {
			for (int j = colStart - height; j <= colStart + height; j = j
					+ height) {
				if (i >= 0 && j >= 0 && i < rows2 && j < columns2) {
					if (pos[i][j] == pos[rowEnd][colEnd]) {
						adder(pos[rowStart][colStart], pos[rowEnd][colEnd]);
						pos[rowStart][colStart].delete();
						removeCell();
						turn = !turn;
						System.out.println("moved");
						return true;

					}
				}
			}
		}
		return false;
	}

	public boolean checkTurn(boolean turn) {
		boolean f = turn;
		if (turn) {
			for (int i = 0; i < rows2; i++) {
				for (int j = 0; j < columns2; j++) {
					if (pos[i][j].getLast() == 2) {

						return f;
					}
				}
			}
		}
		if (!turn) {
			for (int i = 0; i < rows2; i++) {
				for (int j = 0; j < columns2; j++) {
					if (pos[i][j].getLast() == 1) {

						return f;

					}
				}
			}
		}
		f = !f;
		return f;
	}

	public boolean move(int rowStart, int colStart, int rowEnd, int colEnd) {
		System.out.println("board move" + turn);
		if (isEmpty(rowEnd, colEnd)) {
			return false;
		} else if (pos[rowStart][colStart].getLast() == 0) {
			return false;
		} else if (turn && pos[rowStart][colStart].getLast() != 2) {
			return false;
		} else if (!turn && pos[rowStart][colStart].getLast() != 1) {
			return false;
		} else if (rowStart == 0 || colStart == 0 || rowStart == rows2 - 1
				|| colStart == columns2 - 1) {
			return moveHelper(rowStart, colStart, rowEnd, colEnd);
		}
		return moveHelper(rowStart, colStart, rowEnd, colEnd);
	}

	public boolean isEmpty(int row, int col) {
		if (row < 0 || col < 0 || row >= rows2 || col >= columns2) {
			return true;
		}
		return pos[row][col].size() == 0;

	}

	public boolean isGameOver() {
		boolean a = true;
		for (int i = 0; i < rows2; i++) {
			for (int j = 0; j < columns2; j++) {
				if (checkmovess(i, j)) {
					System.out.println(i + " " + j);
					a = false;
				}
			}
		}
		return a;
	}

	public int getWinner() {
		int max = 1;
		int y = 0;
		int x = 0;
		for (int i = 0; i < rows2; i++) {
			for (int j = 0; j < columns2; j++) {
				if (pos[i][j].size() > max) {
					x = i;
					y = j;
					max = pos[i][j].size();
				}
			}
		}
		return getColor(x, y);
	}

	public int getColor(int row, int col) {
		return pos[row][col].getLast();
	}

	public int getHeight(int row, int col) {
		return pos[row][col].size();
	}

	public boolean hasRed(int row, int col) {
		for (int i = 0; i < pos[row][col].size(); i++) {
			if (pos[row][col].getI(i) == 0) {
				return true;
			}
		}
		return false;
	}
}