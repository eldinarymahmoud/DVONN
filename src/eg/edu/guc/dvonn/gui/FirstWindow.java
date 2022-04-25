package eg.edu.guc.dvonn.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FirstWindow implements ActionListener {
	private JFrame base = new JFrame("DVONN");
	private JLabel size;
	private JLabel rows;
	private JLabel cols;
	private JTextField rows1;
	private JTextField cols1;
	private JButton defaultSize;
	private JLabel fill;
	private JButton fillRandom;
	private JButton fillMan;
	private JLabel names;
	private JLabel player1;
	private JLabel player2;
	private Game g;
	private JTextField p2;
	private JTextField p1;

	// private AudioClip sound;

	public void firstPart() {
		base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		base.setSize(500, 500);
		base.setLayout(null);
		base.getContentPane().setBackground(Color.GRAY);
		base.setVisible(true);
		size = new JLabel("Please, Enter the board size : ");
		size.setVisible(true);
		size.setBounds(10, 10, 300, 25);
		base.add(size);
		rows = new JLabel("Rows : ");
		rows.setVisible(true);
		rows.setBounds(10, 40, 100, 25);
		base.add(rows);
		cols = new JLabel("Columns : ");
		cols.setVisible(true);
		cols.setBounds(10, 70, 100, 25);
		base.add(cols);
		rows1 = new JTextField();
		rows1.setVisible(true);
		rows1.setBounds(60, 40, 50, 20);
		base.add(rows1);
		cols1 = new JTextField();
		cols1.setVisible(true);
		cols1.setBounds(80, 70, 50, 20);
		base.add(cols1);
		defaultSize = new JButton("Default size");
		defaultSize.setVisible(true);
		defaultSize.setBounds(20, 100, 100, 25);
		base.add(defaultSize);
		defaultSize.addActionListener(this);
	}

	public FirstWindow() {
		firstPart();
		JLabel line1 = new JLabel(
				"_______________________________________________________________________");
		line1.setVisible(true);
		line1.setBounds(0, 120, 500, 20);
		base.add(line1);
		names = new JLabel("Please, Enter your names : ");
		names.setVisible(true);
		names.setBounds(10, 140, 200, 25);
		base.add(names);
		player1 = new JLabel("Player 1 : ");
		player1.setVisible(true);
		player1.setBounds(10, 170, 100, 25);
		base.add(player1);
		p1 = new JTextField();
		p1.setVisible(true);
		p1.setBounds(80, 170, 100, 20);
		base.add(p1);
		player2 = new JLabel("Player 2 : ");
		player2.setVisible(true);
		player2.setBounds(10, 200, 100, 25);
		base.add(player2);
		p2 = new JTextField();
		p2.setVisible(true);
		p2.setBounds(80, 200, 100, 20);
		base.add(p2);
		JLabel line2 = new JLabel(
				"_______________________________________________________________________");
		line2.setVisible(true);
		line2.setBounds(0, 220, 500, 20);
		base.add(line2);

		fill = new JLabel("Click on filling option : ");
		fill.setVisible(true);
		fill.setBounds(10, 240, 200, 25);
		base.add(fill);
		fillRandom = new JButton("auto fill");
		fillRandom.setVisible(true);
		fillRandom.setBounds(20, 270, 100, 25);
		base.add(fillRandom);
		fillMan = new JButton("manual fill");
		fillMan.setVisible(true);
		fillMan.setBounds(160, 270, 100, 25);
		base.add(fillMan);

		fillRandom.addActionListener(this);
		fillMan.addActionListener(this);
		base.repaint();
	}

	public void setrows1(int rows1) {
		this.rows1.setText(rows1 + "");
	}

	public void setcols1(int cols1) {
		this.cols1.setText(cols1 + "");
	}

	public int getrows1() {
		try {
			return Integer.parseInt(rows1.getText());
		} catch (NumberFormatException e) {
			return 5;
		}
	}

	public int getcols1() {
		try {
			return Integer.parseInt(cols1.getText());
		} catch (NumberFormatException e) {
			return 11;
		}
	}

	public void visibilty(boolean b) {
		base.setVisible(b);
	}

	public String getp1Name() {
		String x = p1.getText();
		System.out.println(x);
		return x;
	}

	public JButton getFillRandom() {
		return fillRandom;
	}

	public void setFillRandom(JButton fillRandom) {
		this.fillRandom = fillRandom;
		base.setVisible(false);
	}

	public JButton getFillMan() {
		return fillMan;
	}

	public void setFillMan(JButton fillMan) {
		this.fillMan = fillMan;
	}

	public String getp2Name() {
		return p2.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		g = new Game(getrows1(), getcols1(), getp1Name(), getp2Name());
		if (e.getSource() == defaultSize) {
			setrows1(5);
			setcols1(11);
		}
		if (e.getSource() == fillMan) {
			g.fillManly();
		}
		if (e.getSource() == fillRandom) {
			g.fillRandomly();
			base.setVisible(false);
		}

	}

}