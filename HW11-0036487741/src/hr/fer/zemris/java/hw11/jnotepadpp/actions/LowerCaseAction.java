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
 * text to lower case letters.
 * 
 * @author Ante Spajic
 *
 */
public class LowerCaseAction extends AbstractSuperMegaCoolAction {

	private static final long serialVersionUID = -8724066540905418886L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_to_lower_case";
	
	/**
	 * Instantiates a new lower case action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public LowerCaseAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.LOWER_CASE);
		putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control L"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(Action.SHORT_DESCRIPTION, "Changes selected text to lowercase");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		caseAction(Character::toLowerCase);
	}

}
