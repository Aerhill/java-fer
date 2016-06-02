package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTabPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Paste action inserts the text, if any, from clipboard to current location of
 * caret in editor.
 * 
 * @author Ante Spajic
 *
 */
public class PasteAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -5972742173991668842L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_paste";

	/**
	 * Instantiates a new paste action.
	 *
	 * @param lp
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public PasteAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.PASTE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTabPP selected = (JTabPP) tabs.getSelectedComponent();
		JTextArea editor = selected.getEditor();
		Document doc = editor.getDocument();
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		String copied = clpbrd.getContents(null) == null ? "" : clpbrd.getContents(null).toString();
		try {
			doc.insertString(editor.getCaretPosition(), copied, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
