package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The Class PrimDemo that is used to demonstrate prime number list generator.
 */
public class PrimDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2552614965736703542L;

	/**
	 * Creates and initializes a new Prime numbers frame.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		initGUI();
	}

	/**
	 * Initializes all needed components for graphical user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<Integer>(model);
		JList<Integer> list2 = new JList<Integer>(model);
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel panel = new JPanel(new GridLayout(1, 2));
		JScrollPane pane1 = new JScrollPane(list1);
		JScrollPane pane2 = new JScrollPane(list2);

		panel.add(pane1);
		panel.add(pane2);

		JPanel lower = new JPanel(new BorderLayout());
		JButton next = new JButton("Next");
		lower.add(next, BorderLayout.CENTER);

		cp.add(panel, BorderLayout.CENTER);
		cp.add(lower, BorderLayout.PAGE_END);

		next.addActionListener((e) -> {
			model.next();
		});
	}

	/**
	 * Entry point to the program, creates a new window for prime number
	 * generator.
	 * 
	 * @param args unused command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
