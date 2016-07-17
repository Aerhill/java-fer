package hr.fer.zemris.java.zavrsni.zavrad.prob2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SwingMijenjalo extends JFrame {
	private static final long serialVersionUID = -1170747429763777706L;

	JTextArea textArea = new JTextArea("Podatci nepotpuni");
	JList<Path> lista;
	MyListModel model;
	
	Map<String,String> kljucevi = new HashMap<>();
	
	public SwingMijenjalo() {
		setSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setUpActions();
		setMenuBar();
	
		model = new MyListModel();
		model.add(Paths.get("."));
		lista = new JList<>(model);
		JScrollPane pane = new JScrollPane(lista);
		lista.setMinimumSize(new Dimension(getHeight(), 100));
		add(textArea, BorderLayout.CENTER);
		add(pane, BorderLayout.WEST);
	
		lista.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Path path = model.getElementAt(e.getFirstIndex());
				String novi;
				try {
					novi = new String(Files.readAllBytes(path));
					for (Map.Entry<String, String> pair : kljucevi.entrySet()) {
						novi = novi.replaceAll("\\#\\$\\{.*"+ pair.getKey() +"\\s*\\}\\$\\#", pair.getValue());
						novi = novi.replaceAll("\\#\\$\\{.*"+ pair.getKey() +"\\s*\\n", pair.getValue() + "\n");
					}
					novi = novi.replaceAll("\\#\\$\\{.*\\}\\$\\#", "???");
					novi = novi.replaceAll("\\#\\$\\{.*\\n", "???\n");
					if (kljucevi.isEmpty()) {
						textArea.setText("Podatci nepotpuni");
					} else {
						textArea.setText(novi);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	private void setUpActions() {
		directoryOpen.putValue(Action.NAME, "Open");
		getKeys.putValue(Action.NAME, "Open keys");
	}


	private void setMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu jm = new JMenu("File");
		mb.add(jm);
		jm.add(directoryOpen);
		jm.add(getKeys);
		setJMenuBar(mb);
	}

	private Action getKeys = new AbstractAction() {
		private static final long serialVersionUID = 6666898674450538585L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(SwingMijenjalo.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(SwingMijenjalo.this,
						"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			List<String> lines = new ArrayList<>();
			try {
				lines = Files.readAllLines(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(SwingMijenjalo.this,
						"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath(), "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (String string : lines) {
				String[] pair = string.split("=");
				if (pair.length != 2) continue;
				kljucevi.put(pair[0].trim(), pair[1].trim());
			}
		}
	};

	private Action directoryOpen = new AbstractAction () {
		private static final long serialVersionUID = 6666898674450538585L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(fc.showOpenDialog(SwingMijenjalo.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			File[] files = fileName.listFiles();
			for (File file : files) {
				model.add(file.toPath());
			}
		}
	};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new SwingMijenjalo().setVisible(true));
	}
}
