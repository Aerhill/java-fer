package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Save as document action prompts user for location to of a file to save currently
 * edited document to.
 * 
 * @author Ante Spajic
 *
 */
public class SaveAsDocumentAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -1013163142593135717L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_save_as";

	/**
	 * Instantiates a new save as document action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public SaveAsDocumentAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.SAVEAS_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		save(true);
	}

}
