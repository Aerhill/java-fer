package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionKeys;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.BlankDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortDescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * This is a main frame class for JNotepad++, a simple text editor with a few
 * text editing commands and possibilites to save current docuemnent or edit
 * multiple documents at the same time.
 * 
 * @author Ante Spajic
 *
 */
public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 5258547185894077314L;

	/**
	 * bar with current tab information
	 */
	private JStatusBar statusBar;
	/**
	 * opened tabs in this window
	 */
	public JTabbedPane tabs;
	/**
	 * Localization provider for this window
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * window language
	 */
	public String language = "en";
	{
		tabs = new JTabbedPane();
		JTabPP tab = new JTabPP();
		tabs.add(tab);
		tabs.setToolTipTextAt(0, tab.getToolTipText());
		tabs.setTitleAt(0, tab.getName());
		tabs.setIconAt(0, tab.getIcon());
	}

	/**
	 * Constructor that creates and initializes this window.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		initGUI();
	}

	/**
	 * Initializes graphical user interface components
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(tabs, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		createMenus();
		createToolbars();
		createStatusBar();
		setActions();
	}

	/**
	 * Enables/disables mark text dependent actions
	 */
	public void setActions() {
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		for (int i = 0; i < markDependent.length; i++) {
			markDependent[i].setEnabled(tab.hasMarkedText());
		}
	}

	/**
	 * Sets the status bar up
	 */
	public void setStatusBar() {
		if (tabs.getTabCount() == 0) {
			remove(statusBar);
			return;
		}
		if (tabs.getTabCount() == 1) {
			createStatusBar();
			return;
		}
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		statusBar.setStatusBar(tab);
	}

	/**
	 * Refreshes all tabs information
	 */
	public void refresh() {
		for (int i = 0; i < tabs.getComponentCount(); i++) {
			JTabPP tab = (JTabPP) tabs.getComponentAt(i);
			tabs.setToolTipTextAt(i, tab.getToolTipText());
			tabs.setTitleAt(i, tab.getName());
			tabs.setIconAt(i, tab.getIcon());
		}
	}

	/**
	 * Creates a new status bar
	 */
	private void createStatusBar() {
		JTabPP tab = (JTabPP) tabs.getSelectedComponent();
		statusBar = new JStatusBar(tab);
		add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Creates a toolbar with actions.
	 */
	private void createToolbars() {
		LJToolBar toolBar = new LJToolBar(ActionKeys.TOOLS, flp);
		toolBar.setFloatable(true);

		toolBar.add(blankDocumentAction);
		toolBar.add(openDocumentAction);
		toolBar.add(saveDocumentAction);
		toolBar.add(saveAsDocumentAction);
		toolBar.addSeparator();
		toolBar.add(cutAction);
		toolBar.add(copyAction);
		toolBar.add(pasteAction);
		toolBar.add(uniqueAction);
		toolBar.addSeparator();
		toolBar.add(closeTabAction);
		toolBar.add(statisticsAction);
		toolBar.addSeparator();
		toolBar.add(upperCaseAction);
		toolBar.add(lowerCaseAction);
		toolBar.add(invertCaseAction);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates menus with actions for this window
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu(ActionKeys.FILE, flp);
		menuBar.add(fileMenu);

		fileMenu.add(blankDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(statisticsAction);
		fileMenu.add(closeTabAction);
		fileMenu.add(exitAction);

		LJMenu editMenu = new LJMenu(ActionKeys.EDIT, flp);
		menuBar.add(editMenu);
		editMenu.add(copyAction);
		editMenu.add(cutAction);
		editMenu.add(pasteAction);

		LJMenu toolsMenu = new LJMenu(ActionKeys.TOOLS, flp);
		menuBar.add(toolsMenu);
		LJMenu sort = new LJMenu(ActionKeys.SORT, flp);
		toolsMenu.add(sort);
		sort.add(new JMenuItem(sortAscAction));
		sort.add(new JMenuItem(sortDescAction));
		LJMenu changeMenu = new LJMenu(ActionKeys.CHANGE_CASE, flp);
		toolsMenu.add(changeMenu);
		changeMenu.add(lowerCaseAction);
		changeMenu.add(upperCaseAction);
		changeMenu.add(invertCaseAction);
		toolsMenu.add(uniqueAction);

		LJMenu langMenu = new LJMenu(ActionKeys.LANGUAGES, flp);
		menuBar.add(langMenu);
		langMenu.add(new JMenuItem(langEn));
		langMenu.add(new JMenuItem(langHr));
		langMenu.add(new JMenuItem(langDe));

		this.setJMenuBar(menuBar);
	}

	/**
	 * English language setting action
	 */
	private Action langEn = new LocalizableAction("english", flp) {
		private static final long serialVersionUID = -6137321570637364091L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("en");
		}
	};

	/**
	 * Croatian language setting action
	 */
	private Action langHr = new LocalizableAction("croatian", flp) {
		private static final long serialVersionUID = -2765834421036676871L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("hr");
		}
	};

	/**
	 * German language setting action
	 */
	private Action langDe = new LocalizableAction("german", flp) {
		private static final long serialVersionUID = -2765834421036676871L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("de");
		}
	};

	/**
	 * Sets this window's lanugage
	 * @param lang language to be set for this window
	 */
	private void setLanguage(String lang) {
		this.language = lang;
		LocalizationProvider.getInstance().setLanguage(language);
	}

	/**
	 * Starts this applicationx.
	 * 
	 * @param args unused cmd arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

	
	/** The blank document action. */
	private Action blankDocumentAction = new BlankDocumentAction(flp, this);
	
	/** The open document action. */
	private Action openDocumentAction = new OpenDocumentAction(flp, this);
	
	/** The save document action. */
	private Action saveDocumentAction = new SaveDocumentAction(flp, this);
	
	/** The save as document action. */
	private Action saveAsDocumentAction = new SaveAsDocumentAction(flp, this);
	
	/** The close tab action. */
	private Action closeTabAction = new CloseTabAction(flp, this);
	
	/** The exit action. */
	private Action exitAction = new ExitAction(flp, this);
	
	/** The statistics action. */
	private Action statisticsAction = new StatisticsAction(flp, this);
	
	/** The cut action. */
	private Action cutAction = new CutAction(flp, this);
	
	/** The copy action. */
	private Action copyAction = new CopyAction(flp, this);
	
	/** The paste action. */
	private Action pasteAction = new PasteAction(flp, this);
	
	/** The upper case action. */
	private Action upperCaseAction = new UpperCaseAction(flp, this);
	
	/** The lower case action. */
	private Action lowerCaseAction = new LowerCaseAction(flp, this);
	
	/** The invert case action. */
	private Action invertCaseAction = new InvertCaseAction(flp, this);
	
	/** The sort asc action. */
	private Action sortAscAction = new SortAscendingAction(flp, this);
	
	/** The sort desc action. */
	private Action sortDescAction = new SortDescendingAction(flp, this);
	
	/** The unique action. */
	private Action uniqueAction = new UniqueAction(flp, this);
	
	/** mark dependent actions. */
	private Action[] markDependent = { upperCaseAction, lowerCaseAction, invertCaseAction, sortAscAction,
			sortDescAction, uniqueAction };

}
