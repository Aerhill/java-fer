package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Upper case action is a mark dependent action that transforms all selected
 * text to opposite case letters.
 * 
 * @author Ante Spajic
 *
 */
public class InvertCaseAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -5594468236249683915L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_toggle_case";
	
	/**
	 * Instantiates a new invert case action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public InvertCaseAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.INVERT_CASE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.SHORT_DESCRIPTION, "Inverts casing of selected text");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		caseAction(c-> Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
	}
}
