package eg.edu.guc.dvonn.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import eg.edu.guc.dvonn.engine.Board;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener {
	private JButton test;
	private int rowStart;
	private int rowEnd;
	private int colStart;
	private int check = 0;
	private boolean turn = false;
	private int colEnd;
	private JFrame gameBase;
	private Board b;
	private JLabel winner;
	private int count = 0;
	private JButton[][] board;
	private JLabel[][] height;
	private int rows1;
	private int cols1;
	private JLabel p1Name, p2Name;
	private JLabel player1, player2;
	private JButton exit;
	private JButton newGame;

	public Game(int rows1, int cols1, String player1, String player2) {
		p1Name = new JLabel("player1: ");
		p2Name = new JLabel("player2: ");
		newGame = new JButton("NEWGAME");
		newGame.setBounds(350, 350, 100, 50);
		newGame.setVisible(true);
		exit = new JButton("EXIT");
		exit.setBounds(200, 350, 100, 50);
		exit.setVisible(true);
		this.p1Name.setBounds(200, 500, 50, 20);
		this.p2Name.setBounds(200, 530, 50, 20);
		this.player1 = new JLabel(player1);
		this.player2 = new JLabel(player2);
		this.player1.setBounds(300, 500, 100, 20);
		this.player1.setVisible(true);
		this.player2.setBounds(300, 530, 100, 20);
		this.rows1 = rows1;
		this.cols1 = cols1;
		b = new Board(rows1, cols1);
		height = new JLabel[rows1][cols1];
		board = new JButton[rows1][cols1];
		test = new JButton();
		gameBase = new JFrame();
		gameBase.setSize(700, 700);
		gameBase.getContentPane().setBackground(Color.GRAY);
		gameBase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameBase.setLayout(null);
		gameBase.setVisible(false);
		gameBase.add(this.p1Name);
		gameBase.add(this.p2Name);
		gameBase.add(this.player1);
		gameBase.add(this.player2);
		newGame.addActionListener(this);
		exit.addActionListener(this);
		gameBase.add(exit);
		gameBase.add(newGame);
	}

	public void putHelper(int rowss, int colu) {
		if (b.put(rowss, colu)) {
			height[rowss][colu].setText(b.getHeight(rowss, colu) + "");
			height[rowss][colu].setVisible(true);
			board[rowss][colu].add(height[rowss][colu]);
			if (count == 1) {
				p1Name.setBackground(Color.red);
				p1Name.setOpaque(true);
				p2Name.setOpaque(false);
				p2Name.setBackground(Color.gray);
				count--;
			} else {
				p2Name.setBackground(Color.red);
				p2Name.setOpaque(true);
				p1Name.setOpaque(false);
				p1Name.setBackground(Color.gray);
				count++;
			}
			if (b.getColor(rowss, colu) == 0) {
				test.setIcon(new ImageIcon("stone_red.png"));
			}
			if (b.getColor(rowss, colu) == 1) {
				test.setIcon(new ImageIcon("stone_white.png"));
			}
			if (b.getColor(rowss, colu) == 2) {
				test.setIcon(new ImageIcon("stone_black.png"));
			}
		}
	}

	public void exitOrRestart(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		}
		if (e.getSource() == newGame) {
			@SuppressWarnings("unused")
			FirstWindow x = new FirstWindow();
			gameBase.setVisible(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		exitOrRestart(e);
		test = (JButton) e.getSource();
		int x = test.getX();
		int y = test.getY();
		int colu = (x / 60);
		int rowss = (y / 60);
		if (b.getCurrentPhase() == 0) {
			putHelper(rowss, colu);
		} else {
			if (check == 0) { // first position
				test.setOpaque(false);
				rowStart = rowss;
				colStart = colu;
				if (!b.checkmovess(rowStart, colStart)) {
					test.setOpaque(true);
					return;
				}
				if (turn && b.getColor(rowss, colu) == 2) {
					test.setOpaque(true);
					return;
				} else if (!turn && b.getColor(rowss, colu) == 1) {
					test.setOpaque(true);
					return;
				} else if (b.getColor(rowss, colu) == 0
						|| b.getColor(rowss, colu) == -1) {
					return;
				}
				check = 1;
			} else if (check == 1) { // second position
				if (b.getColor(rowss, colu) == -1) {
					return;
				}
				rowEnd = rowss;
				colEnd = colu;
				if (rowStart == rowEnd && colStart == colEnd) {
					return;
				}
				soundApplet y1 = new soundApplet("beep.wav");
				y1.playAudio();
				move();
				if (turn) {
					name1();
				} else {
					name2();
				}
			} else {
				return;
			}
		}
	}

	public void name1() {
		p1Name.setBackground(Color.red);
		p1Name.setOpaque(true);
		p2Name.setOpaque(false);
		p2Name.setBackground(Color.gray);
	}

	public void name2() {
		p2Name.setBackground(Color.red);
		p2Name.setOpaque(true);
		p1Name.setOpaque(false);
		p1Name.setBackground(Color.gray);
	}

	public void move() {
		if (b.move(rowStart, colStart, rowEnd, colEnd)) {
			fill();
			if (!checkWhite()) {
				turn = false;
				b.setTurn(true);
			} else {
				if (!checkBlack()) {
					turn = true;
					b.setTurn(false);
				} else {
					turn = !turn;
				}
			}
			System.out.println(turn);
			gameOver();
			check = 0;
		}
	}

	public boolean checkWhite() {
		boolean x = false;
		for (int i = 0; i < rows1; i++) {
			for (int j = 0; j < cols1; j++) {
				if (b.getColor(i, j) == 1) {
					if (b.checkmovess(i, j)) {
						x = true;
					}
				}
			}
		}
		return x;
	}

	public boolean checkBlack() {
		boolean x = false;
		for (int i = 0; i < rows1; i++) {
			for (int j = 0; j < cols1; j++) {
				if (b.getColor(i, j) == 2) {
					if (b.checkmovess(i, j)) {
						x = true;
					}
				}
			}
		}
		return x;

	}

	public void gameOver() {
		if (b.isGameOver()) {
			soundApplet x = new soundApplet("Evil.wav");
			x.playAudio();
			if (b.getWinner() == 1) {
				winner = new JLabel("Player1 WON");
			}
			if (b.getWinner() == 2) {
				winner = new JLabel("Player2 WON");
			}
			winner.setBounds(200, 200, 100, 100);
			winner.setVisible(true);
			gameBase.repaint();
			JOptionPane.showMessageDialog(winner, winner, "Gameover", 1);
		}
	}

	public boolean checkTurn(boolean turn) {
		boolean f = turn;
		if (turn) {
			for (int i = 0; i < rows1; i++) {
				for (int j = 0; j < cols1; j++) {
					if (b.getPos()[i][j].getLast() == 1) {
						return f;
					}
				}
			}
		} else {
			for (int i = 0; i < rows1; i++) {
				for (int j = 0; j < cols1; j++) {
					if (b.getPos()[i][j].getLast() == 2) {

						return f;

					}
				}
			}
		}
		f = !f;
		return f;
	}

	public void fill() {
		System.out.println("In the fil *********************************");
		gameBase.setVisible(true);
		for (int i = 0; i < rows1; i++) {
			for (int j = 0; j < cols1; j++) {
				height[i][j].setText(b.getHeight(i, j) + "");
				height[i][j].setVisible(true);
				if (b.getColor(i, j) == -1) {
					board[i][j].setOpaque(false);
					board[i][j].setIcon(new ImageIcon("click_mark.png"));
					gameBase.getContentPane().add(board[i][j]);
					repaint();
				}
				if (b.getColor(i, j) == 0) {
					board[i][j].setIcon(new ImageIcon("stone_red.png"));
					gameBase.getContentPane().add(board[i][j]);
					repaint();
				}
				if (b.getColor(i, j) == 1) {
					if (b.hasRed(i, j)) {
						board[i][j].setIcon(new ImageIcon("redwhite.png"));
					} else {
						board[i][j].setIcon(new ImageIcon("stone_white.png"));
					}
					gameBase.getContentPane().add(board[i][j]);
					repaint();
				}
				if (b.getColor(i, j) == 2) {
					if (b.hasRed(i, j)) {
						board[i][j].setIcon(new ImageIcon("redBlack.png"));
					} else {
						board[i][j].setIcon(new ImageIcon("stone_black.png"));
					}
					gameBase.getContentPane().add(board[i][j]);
				}

			}

		}

	}

	public void fillManly() {
		System.out.println("In teh fill manual ************************");
		gameBase.setVisible(true);
		b = new Board(rows1, cols1);
		int y = 0;
		for (int i = 0; i < rows1; i++) {
			int x = 0;
			for (int j = 0; j < cols1; j++) {
				board[i][j] = new JButton();
				board[i][j].addActionListener(this);
				board[i][j].setBounds(x, y, 60, 60);
				board[i][j].setIcon(new ImageIcon("click_mark.png"));
				board[i][j].setBackground(Color.LIGHT_GRAY);
				board[i][j].setOpaque(true);
				board[i][j].setVisible(true);
				height[i][j] = new JLabel(b.getHeight(i, j) + "");
				height[i][j].setLayout(null);
				board[i][j].setLayout(null);
				height[i][j].setBounds(22, 20, 20, 20);
				height[i][j].setVisible(true);
				board[i][j].add(height[i][j]);
				gameBase.add(board[i][j]);
				x = x + 60;
			}
			y = y + 60;
		}
		p1Name.setOpaque(true);
		p1Name.setBackground(Color.red);
	}

	public void fillRandomly() {
		System.out.println("In teh fill random ************************");
		gameBase.setVisible(true);
		gameBase.setTitle("HI");
		b = new Board(rows1, cols1);
		b.fillRandom();
		int y = 0;
		for (int i = 0; i < rows1; i++) {
			int x = 0;
			for (int j = 0; j < cols1; j++) {
				board[i][j] = new JButton();
				board[i][j].setBounds(x, y, 60, 60);
				board[i][j].setBackground(Color.LIGHT_GRAY);
				board[i][j].setOpaque(true);
				board[i][j].addActionListener(this);
				board[i][j].setVisible(true);
				height[i][j] = new JLabel(b.getHeight(i, j) + "");
				height[i][j].setLayout(null);
				board[i][j].setLayout(null);
				height[i][j].setBounds(22, 20, 20, 20);
				height[i][j].setVisible(true);
				board[i][j].add(height[i][j]);
				gameBase.add(board[i][j]);

				if (b.getColor(i, j) == 0) {
					board[i][j].setIcon(new ImageIcon("stone_red.png"));
				}
				if (b.getColor(i, j) == 1) {
					board[i][j].setIcon(new ImageIcon("stone_white.png"));
				}
				if (b.getColor(i, j) == 2) {
					board[i][j].setIcon(new ImageIcon("stone_black.png"));
				}
				x = x + 60;
			}
			y = y + 60;
		}
		p2Name.setOpaque(true);
		p2Name.setBackground(Color.red);

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FirstWindow f = new FirstWindow();
	}
}