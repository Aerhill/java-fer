package hr.fer.zemris.tecaj.swing.p10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor3 extends JFrame {

	private static final long serialVersionUID = -8826341685279647668L;

	public Prozor3() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGui();

	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel("<html>Ovo je <i><font color='red'>Labela</font></i></html>");
		labela.setOpaque(true);
		labela.setBackground(Color.YELLOW);
		labela.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton button = new JButton("Stisni me");
		button.setBounds(10,50,100,30);
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(button, BorderLayout.SOUTH);
	}

	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor3().setVisible(true);
		});
	}
}
