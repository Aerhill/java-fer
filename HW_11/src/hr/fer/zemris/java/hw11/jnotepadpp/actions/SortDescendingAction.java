package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Sort ascending is a mark dependent action that sorts the selected lines of
 * text in descending order.
 * 
 * @author Ante Spajic
 *
 */
public class SortDescendingAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = 8822462073228640707L;
	
	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_sort_descending";
	
	/**
	 * Instantiates a new sort descending action.
	 *
	 * @param lp the localization provdire
	 * @param notepad the notepad
	 */
	public SortDescendingAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.SORT_DESC);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift Y"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.SHORT_DESCRIPTION, "Sort selected lines of text in descending order");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sort(false);
	}

}
