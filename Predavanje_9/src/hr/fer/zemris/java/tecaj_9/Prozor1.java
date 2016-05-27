package hr.fer.zemris.java.tecaj_9;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Prozor1 extends JFrame {

	private static final long serialVersionUID = -4811984031975354008L;

	public Prozor1() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(300, 300);
		setLocation(100, 100);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int odluka =JOptionPane.showConfirmDialog(Prozor1.this, "Jeste li sigurno da želite zatvoriti prozor?",
						"Poruka sustava", 
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (odluka != JOptionPane.YES_OPTION) {
					return;
				}
				dispose();
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Prozor1().setVisible(true));
	}
}
