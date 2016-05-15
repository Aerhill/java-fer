package hr.fer.zemris.tecaj.swing.p04;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8826341685279647668L;

	public Prozor1() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20,20);
		setSize(500,200);
		initGui();
		
		System.out.println(Thread.currentThread().getName());
	}

	private void initGui() {
		getContentPane().setLayout(null);
		
		JComponent komponenta1 = new JComponent() {};
		komponenta1.setLocation(10,10);
		komponenta1.setSize(100,40);
		komponenta1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		getContentPane().add(komponenta1);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {new Prozor1().setVisible(true);});
	}
}
