package hr.fer.zemris.java.zi.prob3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextFrame extends JFrame {
	private static final long serialVersionUID = -7283599361991234438L;

	private JTextArea textArea;
	
	public TextFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setEditable(false);
		prepareActions();
		addMenuBar();
		JScrollPane pane = new JScrollPane(textArea);
		add(pane, BorderLayout.CENTER);
	}
	
	private void prepareActions() {
		openAction.putValue(Action.NAME, "Open");
		sortAction.putValue(Action.NAME, "Sort");
		dellOddAction.putValue(Action.NAME, "Del odd");
	}

	private void addMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu jm = new JMenu("File");
		mb.add(jm);
		jm.add(openAction);
		jm.add(sortAction);
		jm.add(dellOddAction);
		setJMenuBar(mb);
	}

	private Action openAction = new AbstractAction() {
		private static final long serialVersionUID = 7043048758785238413L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(TextFrame.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(TextFrame.this,
						"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] bytes;
			try {
				bytes = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(TextFrame.this,
						"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath(), "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String text = new String(bytes, StandardCharsets.UTF_8);
			textArea.setText(text);
		}

	};
	
	private Action sortAction = new AbstractAction() {
		private static final long serialVersionUID = 9139177320372373030L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = textArea.getDocument();
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			int offset = len != 0 ? Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark()) : doc.getLength();

			try {
				offset = 0;
				len = textArea.getText().length();
				String text = doc.getText(offset, len - offset);
				List<String> sort = Arrays.asList(text.split("\\r?\\n"));
				Collections.sort(sort, (b1,b2) -> b1.compareTo(b2));
				int lines = textArea.getLineCount();
				doc.remove(offset, len - offset);
				for (String string : sort) {
					doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
					offset += string.length() + 1;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	private Action dellOddAction = new AbstractAction() {
		private static final long serialVersionUID = 567813651449806519L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = textArea.getDocument();
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			int offset = len != 0 ? Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark()) : doc.getLength();

			try {
				offset = 0;
				len = textArea.getText().length();
				String text = doc.getText(offset, len - offset);
				List<String> sort = Arrays.asList(text.split("\\r?\\n"));
				int lines = textArea.getLineCount();
				doc.remove(offset, len - offset);
				int i = 1;
				for (String string : sort) {
					if (i%2 == 0) {
						doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
						offset += string.length() + 1;
					}
					i++;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TextFrame().setVisible(true));
	}
}
