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
 * The Class StatisticsAction shows the statistics for currently showed document.
 */
public class StatisticsAction extends AbstractSuperMegaCoolAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6828355694959121543L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_statistics";
	
	/**
	 * Dialog display message
	 */
	private String message;
	
	/**
	 * Instantiates a new statistics action.
	 *
	 * @param lp the localization provider
	 * @param notepad the notepad
	 */
	public StatisticsAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		lp.addLocalizationListener(()-> update());
		putValue(Action.SMALL_ICON, Icons.STATISTICS);
		putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control B"));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_B);
		putValue(Action.SHORT_DESCRIPTION,"Statistics for current document");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		JOptionPane.showMessageDialog(notepad, message, lp.getString(ActionKeys.STATISTICS_TITLE), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Updates language dependent elements for this action.
	 */
	private void update() {
		JTabPP selected = (JTabPP) tabs.getSelectedComponent();
		if (selected == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		message = lp.getString(ActionKeys.STATISTICS_LINE_1) 
				+ " "  + selected.getTotalNum() + lp.getString(ActionKeys.CHARACTERS)+", " + selected.getCharNum() + 
				" " + lp.getString(ActionKeys.NON_BLANK_CHARACTERS)+ " " + selected.getLines() + " " + lp.getString(ActionKeys.LINES);
	}
}
