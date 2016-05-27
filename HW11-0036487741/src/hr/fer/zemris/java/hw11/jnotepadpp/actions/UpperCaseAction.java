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
 * text to upper case letters.
 * 
 * @author Ante Spajic
 *
 */
public class UpperCaseAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = 6690943598643833272L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_to_upper_case";

	/**
	 * Instantiates a new upper case action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public UpperCaseAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.UPPER_CASE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.SHORT_DESCRIPTION, "Changes selected text to uppercase");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		caseAction(Character::toUpperCase);
	}

}
