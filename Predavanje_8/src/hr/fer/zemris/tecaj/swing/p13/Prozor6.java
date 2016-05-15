package hr.fer.zemris.tecaj.swing.p13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor6 extends JFrame {

	private static final long serialVersionUID = -8826341685279647668L;

	public Prozor6() {
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

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		bottomPanel.add(new JLabel("Unesi broj: "));

		JTextField unosBroja = new JTextField();
		bottomPanel.add(unosBroja);
		
		JButton izracunaj = new JButton("Izracunaj");
		bottomPanel.add(izracunaj);
		
		izracunaj.addActionListener(e -> {
			obradi(labela, unosBroja.getText());
		});
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void obradi(JLabel labela, String text) {
		try {
			double broj = Double.parseDouble(text);
			labela.setText(Double.toString(broj*broj));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nisam mogao pretvoriti " + text + " u broj");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor6().setVisible(true);
		});
	}
}
