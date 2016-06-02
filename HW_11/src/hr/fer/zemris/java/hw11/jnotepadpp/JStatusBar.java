package hr.fer.zemris.java.hw11.jnotepadpp;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * The JStatusBar is an element with information about the currently opened
 * document in JNotepad++ window.
 * 
 * @author Ante Spajic
 */
public class JStatusBar extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5436931251319099439L;

	/** The clock. */
	private JLabel clock;

	/** The ln label. */
	private JLabel lnLabel;

	/** The col label. */
	private JLabel colLabel;

	/** The sel label. */
	private JLabel selLabel;

	/** The len label. */
	private JLabel lenLabel;

	/** The tab. */
	private JTabPP tab;

	/** The editor. */
	private JTextArea editor;

	/** Date format for clock */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Instantiates a new notepad status bar.
	 *
	 * @param tab
	 *            the tab with editor to show the info about
	 */
	public JStatusBar(JTabPP tab) {
		setStatusBar(tab);
	}

	/**
	 * Sets the status bar.
	 *
	 * @param tab
	 *            the new status bar
	 */
	public void setStatusBar(JTabPP tab) {
		this.tab = tab;
		this.editor = tab.getEditor();
		this.lenLabel = new JLabel("Len: 0");
		this.lnLabel = new JLabel("Ln: 1");
		this.colLabel = new JLabel(" Col: 0");
		this.selLabel = new JLabel(" Sel: 0");
		this.clock = new JLabel();
		removeAll();
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		setUpLabel();
	}

	/**
	 * Sets the up labels.
	 */
	private void setUpLabel() {
		add(lenLabel);
		add(Box.createHorizontalGlue());
		add(lnLabel);
		add(colLabel);
		add(selLabel);
		add(Box.createHorizontalGlue());
		add(clock);

		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea) e.getSource();
				int linenum = 1;
				int columnnum = 1;
				try {
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - editArea.getLineStartOffset(linenum);
					linenum += 1;
				} catch (Exception ignorable) {
				}
				updateStatus(linenum, columnnum);
			}

			private void updateStatus(int linenum, int columnnum) {
				lenLabel.setText("Len: " + tab.getTotalNum());
				lnLabel.setText("Ln: " + linenum);
				colLabel.setText(" Col: " + columnnum);
				selLabel.setText(" Sel: " + Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark()));
			}
		});
		keepPosted();
	}

	/**
	 * Method that keeps the clock refreshed.
	 */
	private void keepPosted() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(250);
					SwingUtilities.invokeLater(() -> clock.setText(sdf.format(new Date())));
				} catch (InterruptedException ignorable) {
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
