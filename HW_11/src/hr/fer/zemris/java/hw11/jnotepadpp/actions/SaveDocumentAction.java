package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTabPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Save document action saves current document to its path or if it has no path
 * offers you to select a new file.
 * 
 * @author Ante Spajic
 *
 */
public class SaveDocumentAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -4259238913878020902L;

	private static final String ACTION_NAME = "action_save";

	/**
	 * Instantiates a new save document action.
	 *
	 * @param lp
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public SaveDocumentAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.SAVE_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		if (tab == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path openedFilePath = tab.getFilePath();
		if (openedFilePath == null) {
			save(true);
		} else {
			save(false);
		}
	}

}
