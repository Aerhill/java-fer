package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * The Class JTabPP holds all the elements that one tab in JNotepad++ can have
 * and its info like what file is currently opened, current file path editor for
 * this tab etc.
 * 
 * @author Ante Spajic
 */
public class JTabPP extends JScrollPane {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4754363224811476119L;

	/** The editor. */
	private JTextArea editor;

	/** The file path. */
	private Path filePath;

	/** The file name. */
	private String fileName;

	/** The total num. */
	private int totalNum;

	/** The char num. */
	private int charNum;

	/** The lines. */
	private int lines;

	/** The edited. */
	private boolean edited;

	/** The has marked text. */
	private boolean hasMarkedText;

	/**
	 * Instantiates a new custom tab for tabbed pane.
	 */
	public JTabPP() {
		this.editor = new JTextArea();
		this.fileName = "New Document";
		setUpEditor();
	}

	/**
	 * Instantiates a new custom tab for tabbed pane with provided path to file
	 * and preset text.
	 *
	 * @param filePath
	 *            the file path to opened file
	 * @param text
	 *            the text to be set in editor
	 */
	public JTabPP(Path filePath, String text) {
		this.filePath = filePath;
		this.fileName = filePath.toAbsolutePath().getFileName().toString();
		this.editor = new JTextArea();
		this.editor.setText(text);
		setUpEditor();
	}

	/**
	 * Sets the up editor for this tab.
	 */
	private void setUpEditor() {
		this.edited = false;
		setViewportView(editor);
		editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}

			private void update(DocumentEvent e) {
				Document doc = e.getDocument();
				if (!isEdited()) {
					setEdited(true);
				}
				((JNotepadPP) JTabPP.this.getTopLevelAncestor()).refresh();
				totalNum = doc.getLength();
				lines = editor.getLineCount();
				charNum = editor.getText().replaceAll("\\s+", "").length();
			}
		});
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (hasMarkedText != checkForMarks(e)) {
					hasMarkedText = checkForMarks(e);
					((JNotepadPP) JTabPP.this.getTopLevelAncestor()).setActions();
				}
			}

			private boolean checkForMarks(CaretEvent e) {
				return Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark()) != 0;
			}
		});
	}

	/**
	 * Returns the editor from this tab.
	 *
	 * @return the editor
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Sets the editor for this tab.
	 *
	 * @param editor
	 *            the new editor
	 */
	public void setEditor(JTextArea editor) {
		this.editor = editor;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath
	 *            the new file path
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
		this.fileName = filePath.toAbsolutePath().getFileName().toString();
	}

	@Override
	public String getToolTipText() {
		if (filePath == null) {
			return "Unsaved";
		}
		return filePath.toAbsolutePath().toString();
	}

	/**
	 * Gets the total number of characters in this tabs editor.
	 *
	 * @return the total num
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * Gets the char number in this tabs editor.
	 *
	 * @return the char num
	 */
	public int getCharNum() {
		return charNum;
	}

	/**
	 * Gets the nubmer of lines in this tabs editor.
	 *
	 * @return the lines
	 */
	public int getLines() {
		return lines;
	}

	@Override
	public String getName() {
		return fileName;
	}

	/**
	 * Signals that the editor has been edited.
	 *
	 * @param edited
	 *            does this editor have any unsaved changes
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Gets the icon for this tabs editor status.
	 *
	 * @return the icon
	 */
	public Icon getIcon() {
		return edited ? Icons.RED_ICON : Icons.GREEN_ICON;
	}

	/**
	 * Checks if is edited.
	 *
	 * @return true, if it is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Checks for marked text.
	 *
	 * @return true, if successful
	 */
	public boolean hasMarkedText() {
		return hasMarkedText;
	}
}
