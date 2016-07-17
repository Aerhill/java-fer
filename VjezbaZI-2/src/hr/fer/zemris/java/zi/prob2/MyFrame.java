package hr.fer.zemris.java.zi.prob2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 9179222963180950535L;

	public MyFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Path p = Paths.get("D:\\jabuke.jpg");
		MyPanel mp = new MyPanel(p);
		setLayout(new BorderLayout());
		add(mp, BorderLayout.CENTER);
		setSize(new Dimension(300, 300));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MyFrame().setVisible(true)); 
	}
}
