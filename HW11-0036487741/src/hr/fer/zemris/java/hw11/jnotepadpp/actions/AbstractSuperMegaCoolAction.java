package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTabPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Well this class at one point in this homework seemed like a really good idea
 * but a day after not so much, but as you may know there is very little time to
 * make significant changes to your application so this stayed as is and it is
 * basically heart of every action with most of method implementations staying
 * in here and serving for extending actions for use.
 * 
 * @author Ante Spajic
 *
 */
public abstract class AbstractSuperMegaCoolAction extends LocalizableAction {
	private static final long serialVersionUID = -7608420508559818629L;

	/** The notepad. */
	protected JNotepadPP notepad;

	/** The tabs. */
	protected JTabbedPane tabs;

	/** The localization provider. */
	protected ILocalizationProvider lp;

	/** The locale. */
	private Locale locale;

	/** The collator for comparator. */
	private Collator col;

	/** The save error. */
	private String saveError;

	/** The save success. */
	private String saveSuccess;

	/** The overwrite message text. */
	private String overwrite;

	/** The nothing saved message text. */
	private String nothingSaved;

	/**
	 * Instantiates a new abstract super mega cool action.
	 *
	 * @param key
	 *            the key for exdending action name
	 * @param lp
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public AbstractSuperMegaCoolAction(String key, ILocalizationProvider lp, JNotepadPP notepad) {
		super(key, lp);
		this.lp = lp;
		this.notepad = notepad;
		this.tabs = notepad.tabs;

		update();
		lp.addLocalizationListener(() -> update());
		setFrameTitle();
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setFrameTitle();
				notepad.setStatusBar();
			}
		});
	}

	/**
	 * Updates language dependent elements.
	 */
	private void update() {
		locale = Locale.forLanguageTag(notepad.language);
		col = Collator.getInstance(locale);

		saveError = lp.getString(ActionKeys.SAVE_UNSUCCESSFUL_TEXT);
		saveSuccess = lp.getString(ActionKeys.SAVE_SUCCESSFUL_TEXT);
		overwrite = lp.getString(ActionKeys.OVERWRITE_QUESTION);
		nothingSaved = lp.getString(ActionKeys.SAVE_CANCELED);
	}

	/**
	 * Refreshes tabs in notepad.
	 */
	protected void refreshTabs() {
		for (int i = 0; i < tabs.getComponentCount(); i++) {
			JTabPP tab = (JTabPP) tabs.getComponentAt(i);
			tabs.setToolTipTextAt(i, tab.getToolTipText());
			tabs.setTitleAt(i, tab.getName());
			tabs.setIconAt(i, tab.getIcon());
		}
	}

	/**
	 * Sets notepads frame title.
	 */
	protected void setFrameTitle() {
		if (tabs != null && tabs.getTabCount() == 0) {
			notepad.setTitle("Nothing opened");
			return;
		}
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		notepad.setTitle((tab.getFilePath() == null ? "Unsaved" : tab.getFilePath().toString()) + " - JNotepad++");
	}

	/**
	 * Save action implementation.
	 * 
	 * @param saveAs
	 *            true if user should be prompted for location, false to save to
	 *            current file
	 */
	protected void save(boolean saveAs) {
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		if (tab == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path openedFilePath = tab.getFilePath();
		if (saveAs) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(notepad, nothingSaved, "Upozorenje", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (jfc.getSelectedFile().exists()) {
				if (JOptionPane.showConfirmDialog(notepad, overwrite, "Yo",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			tab.setFilePath(openedFilePath);
		}
		JTextArea editor = tab.getEditor();
		byte[] podaci = editor.getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(openedFilePath, podaci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(notepad, saveError + openedFilePath.toAbsolutePath(), "Yo",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(notepad, saveSuccess, "Info", JOptionPane.INFORMATION_MESSAGE);
		((JTabPP) tabs.getSelectedComponent()).setEdited(false);
		setFrameTitle();
		notepad.refresh();
	}

	/**
	 * Copy action implementation.
	 * 
	 * @param cut
	 *            true if a text should be cut, false otherwise
	 */
	protected void copyAction(boolean cut) {
		JTabPP selected = (JTabPP) tabs.getSelectedComponent();
		JTextArea editor = selected.getEditor();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if (len == 0)
			return;
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			StringSelection stringSelection = new StringSelection(doc.getText(offset, len));
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
			if (cut) {
				doc.remove(offset, len);
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Action that is responsible for case actions, changes case depending on
	 * what {@link CaseChanger} action has been provided.
	 * 
	 * @param cc
	 *            {@link CaseChanger} function to be executed on text.
	 */
	protected void caseAction(CaseChanger cc) {
		JTextArea editor = ((JTabPP) tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = len != 0 ? Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()) : doc.getLength();

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, cc);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException lol) {
		}
	}

	/**
	 * Changes the case on selected text.
	 * 
	 * @param text
	 *            selected text
	 * @param cc
	 *            {@link CaseChanger} function
	 * @return changed text
	 */
	private String changeCase(String text, CaseChanger cc) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			znakovi[i] = cc.changeCase(c);
		}
		return new String(znakovi);
	}

	/**
	 * Collator comparator implementation, works on many languages.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private class MyComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return col.compare(o1, o2);
		}
	}

	/**
	 * Sorts the selected lines of text.
	 * 
	 * @param ascending
	 *            true if text should be sorted in ascending order, false
	 *            otherwise
	 */
	protected void sort(boolean ascending) {
		JTextArea editor = ((JTabPP) tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = len != 0 ? Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()) : doc.getLength();

		try {
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
			String text = doc.getText(offset, len - offset);
			List<String> sort = Arrays.asList(text.split("\\r?\\n"));
			Collections.sort(sort, ascending ? new MyComparator() : new MyComparator().reversed());
			int lines = editor.getLineCount();
			doc.remove(offset, len - offset);
			for (String string : sort) {
				doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Case changer functional interface used by casechaning actions.
	 * 
	 * @author Ante Spajic
	 */
	protected interface CaseChanger {
		char changeCase(char c);
	}
}
