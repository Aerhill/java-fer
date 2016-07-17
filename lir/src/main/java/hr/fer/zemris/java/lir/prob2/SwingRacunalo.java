package hr.fer.zemris.java.lir.prob2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.lir.prob1.Racunalo;
import hr.fer.zemris.java.lir.prob1.Racunalo.MyFile;

public class SwingRacunalo extends JFrame {

	private static final long serialVersionUID = -4695034261136695960L;

	JTextArea textArea = new JTextArea("Podatci nepotpuni");
	JList<Path> lista;
	MyListModel model = new MyListModel();
	public SwingRacunalo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		initGui();
	}
	
	private void initGui() {
		setUpActions();
		setMenuBar();
		
		lista = new JList<>(model);
		
		lista.addListSelectionListener(listener -> {
			Path p = lista.getSelectedValue();
			
			MyFile f = Racunalo.calculate(p);
			DecimalFormat df = new DecimalFormat("#.###");
			String string = "C = " + df.format(f.getUkC()) + "\nR = " + df.format(f.getUkR()) + "\nT = "
					+ df.format(f.getUkT());
			textArea.setText(string);
		});
		lista.setPreferredSize(new Dimension(100,450));
		add(lista, BorderLayout.WEST);
		add(textArea, BorderLayout.CENTER);
	}
	
	
	
	private void setMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu jm = new JMenu("File");
		mb.add(jm);
		jm.add(directoryOpen);
		setJMenuBar(mb);
	}

	private void setUpActions() {
		directoryOpen.putValue(Action.NAME, "Otvori direktorij");
	}



	private Action directoryOpen = new AbstractAction () {
		private static final long serialVersionUID = 6666898674450538585L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(fc.showOpenDialog(SwingRacunalo.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			File[] files = fileName.listFiles();
			model.removeAll();
			for (File file : files) {
				if(!Files.isDirectory(file.toPath())) {
					model.add(file.toPath());
				}
			}
		}
	};

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> new SwingRacunalo().setVisible(true));
	}
}
