package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTabPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Close tab action closes currently opened tab.
 * 
 * @author Ante Spajic
 *
 */
public class CloseTabAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = 2604141585682469778L;

	/** The Constant ACTION_NAME. */
	public final static String ACTION_NAME = "action_close_current_file";
	
	/** The dialog message. */
	private String message;
	
	/** The dialog title. */
	private String title;
	
	/**
	 * Instantiates a new close tab action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public CloseTabAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.CLOSE_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		putValue(Action.SHORT_DESCRIPTION, "Closes the current tab");
		update();
		lp.addLocalizationListener(()-> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		if (tab == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (tab.isEdited()) {
			int option = JOptionPane.showConfirmDialog(notepad, message, title, 
                    JOptionPane.YES_NO_CANCEL_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				save(true);
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		tabs.remove(tab);
	}
	
	/**
	 * Updates language dependent elements of this action.
	 */
	private void update() {
		message = lp.getString(ActionKeys.UNSAVED_WORK);
		title = lp.getString(ActionKeys.WARNING_TITLE);
	}
}
