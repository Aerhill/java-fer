package hr.fer.zemris.tecaj.swing.p03;

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

		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {new Prozor1().setVisible(true);});
	}
}
