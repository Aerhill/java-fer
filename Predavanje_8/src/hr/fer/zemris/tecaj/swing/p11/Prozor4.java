package hr.fer.zemris.tecaj.swing.p11;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor4 extends JFrame {

	private static final long serialVersionUID = -8826341685279647668L;

	public Prozor4() {
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
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1,0));
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(buttonsPanel, BorderLayout.SOUTH);
		
		JButton[] buttons = new JButton[4];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("Gumb " + (i+1));
			buttonsPanel.add(buttons[i]);
		}
	}

	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor4().setVisible(true);
		});
	}
}
