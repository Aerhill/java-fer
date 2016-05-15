package hr.fer.zemris.tecaj.swing.p09;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor2 extends JFrame {

	private static final long serialVersionUID = -8826341685279647668L;

	public Prozor2() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGui();

	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(null);
		
		JLabel labela = new JLabel("Ovo je tekst!");
		labela.setBounds(10,10,100,30);
		
		JButton button = new JButton("Stisni me");
		button.setBounds(10,50,100,30);
		
		cp.add(labela);
		cp.add(button);
	}

	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor2().setVisible(true);
		});
	}
}
