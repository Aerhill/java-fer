package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * Action unqiue removes all duplicate lines from selected text.
 * 
 * @author Ante Spajic
 *
 */
public class UniqueAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = 7756140438152894726L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_unique";
	
	/**
	 * Instantiates a new unique action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public UniqueAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.UNIQUE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_I);
		putValue(Action.SHORT_DESCRIPTION, "Remove selected duplicate lines");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = ((JTabPP)tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		int offset = len != 0 ? Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()) : doc.getLength();
		try {
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
			String text = doc.getText(offset, len-offset);
			Set<String> sort = new LinkedHashSet<>(Arrays.asList(text.split("\\r?\\n")));
			int lines = editor.getLineCount();
			doc.remove(offset,len-offset);
			for (String string : sort) {
				doc.insertString(offset, string + (--lines>0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
	}

}
