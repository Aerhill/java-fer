package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Cut action places the selected text into a clipboard and deletes it from
 * editor.
 * 
 * @author Ante Spajic
 *
 */
public class CutAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -5740192816417655334L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_cut";

	/**
	 * Instantiates a new cut action.
	 *
	 * @param lp
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public CutAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.CUT_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copyAction(true);
	}

}
