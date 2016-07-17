package hr.fer.zemris.cmdapps.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.cmdapps.jvdraw.actions.ClearModelAction;
import hr.fer.zemris.cmdapps.jvdraw.actions.ExportFileAction;
import hr.fer.zemris.cmdapps.jvdraw.actions.OpenFileAction;
import hr.fer.zemris.cmdapps.jvdraw.color.JColorArea;
import hr.fer.zemris.cmdapps.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.cmdapps.jvdraw.drawing.Painter;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;
import hr.fer.zemris.cmdapps.jvdraw.models.CoolModel;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingObjectListModel;


/**
 * Main class that starts the GUI application.
 * 
 * @author Ante Spajić
 * 
 */
public class JVDraw extends JFrame {

    private static final long serialVersionUID = -992166089361956921L;
    public static final String EXTENSION = ".jvd";

    private JColorArea foregnd;
    private JColorArea backgnd;
    private JDrawingCanvas canvas;
    private Painter painter;
    private DrawingModel model = new CoolModel();
    private Path path;

    /**
     * Creates a new {@link JVDraw} object. Initializes the gui.
     */
    public JVDraw() {
        setLayout(new BorderLayout());

        setLocation(100, 100);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        foregnd = new JColorArea(Color.BLUE, true);
        backgnd = new JColorArea(Color.RED, false);
        canvas = new JDrawingCanvas(model);
        painter = new Painter(canvas, model, foregnd, backgnd);
        add(canvas, BorderLayout.CENTER);
        JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                int index = list.getSelectedIndex();
                if (index == -1) return;
                model.getObject(index).onClick(JVDraw.this.canvas, JVDraw.this.canvas);
            }
        });
        JScrollPane pane = new JScrollPane(list);
        add(pane, BorderLayout.LINE_END);
        prepareMenu();
    }


    /**
     * Creates menu for this program.
     */
    private void prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        initializeActions();
        JMenu file = new JMenu("File");
        file.add(new JMenuItem(openFileAction));
        file.add(new JMenuItem(saveFileAction));
        file.add(new JMenuItem(saveAsFileAction));
        file.addSeparator();
        
        file.add(new JMenuItem(exportFileAction));
        file.add(new JMenuItem(exitAction));
        menuBar.add(file);
        
        setJMenuBar(menuBar);
        JToolBar toolbar = new JToolBar("Tools");
        JToggleButton line = new JToggleButton("Line");
        line.setSelected(true);
        JToggleButton circle = new JToggleButton("Circle");
        JToggleButton fcircle = new JToggleButton("FCircle");
        line.addActionListener(l-> painter.setStatus(Painter.LINE));
        circle.addActionListener(l-> painter.setStatus(Painter.CIRCLE));
        fcircle.addActionListener(l-> painter.setStatus(Painter.FCIRCLE));
        ButtonGroup group = new ButtonGroup();
        group.add(line);
        group.add(circle);
        
        group.add(fcircle);
        toolbar.add(foregnd);
        toolbar.add(backgnd);
        toolbar.addSeparator();
        toolbar.add(line);
        
        toolbar.add(circle);
        toolbar.add(fcircle);
        toolbar.addSeparator();
        
        toolbar.add(new JButton(clearAll));
        toolbar.addSeparator();
        add(toolbar, BorderLayout.PAGE_START);
    }

    /**
     * Initializes actions.
     */
    private void initializeActions() {
        openFileAction.putValue(Action.NAME, "Open");
        openFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openFileAction.putValue(Action.SHORT_DESCRIPTION, "Opens a new file");

        saveFileAction.putValue(Action.NAME, "Save");
        saveFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveFileAction.putValue(Action.SHORT_DESCRIPTION, "Saves the current file");

        saveAsFileAction.putValue(Action.NAME, "Save as");
        saveAsFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        saveAsFileAction.putValue(Action.SHORT_DESCRIPTION, "Saves the current file to given path");

        exportFileAction.putValue(Action.NAME, "Export");
        exportFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exportFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        exportFileAction.putValue(Action.SHORT_DESCRIPTION, "Exports the created file as image");

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes the program");

        clearAll.putValue(Action.NAME, "Clear");
    }

    private Action openFileAction = new OpenFileAction(model,path);
    private Action exportFileAction = new ExportFileAction(model);
    private Action clearAll = new ClearModelAction(model);

    private Action saveFileAction = new AbstractAction() {
		private static final long serialVersionUID = -562793308939012972L;

		@Override
        public void actionPerformed(ActionEvent e) {
            if (path == null) {
                saveAsFileAction.actionPerformed(e);
            }
    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		try {
    			for (int i = 0, size = model.getSize(); i < size; i++) {
    				String obj = model.getObject(i).toString();
    				bos.write(obj.getBytes(StandardCharsets.UTF_8));
    				if (i + 1 < size) {
    					bos.write("\n".getBytes(StandardCharsets.UTF_8));
    				}
    			}
    			byte[] content = bos.toByteArray();
    			bos.close();

    			Files.write(path, content);
    			JOptionPane.showMessageDialog(null, "File was successfully saved", null, JOptionPane.INFORMATION_MESSAGE);

    		} catch (IOException ignore) {
    			JOptionPane.showMessageDialog(null, "Saving failed", "Error", JOptionPane.ERROR_MESSAGE);
    		}
            painter.fileSaved();
        }
    };

    private Action saveAsFileAction = new AbstractAction() {
		private static final long serialVersionUID = -5276525357010729619L;

		@Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            path = Paths.get(fc.getSelectedFile().toPath() + EXTENSION);
            saveFileAction.actionPerformed(e);
        }
    };

    private Action exitAction = new AbstractAction() {
		private static final long serialVersionUID = 8041165254251972215L;

		@Override
        public void actionPerformed(ActionEvent e) {
            if (painter.hasChanged()) {
                if ( JOptionPane.showConfirmDialog(JVDraw.this, "Do you wish to save this drawing?", "Save?",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    saveFileAction.actionPerformed(e);
                }
            }
            JVDraw.this.dispose();
        }
    };

    /**
     * Creates a new JVDraw frame and sets it's visibility to <code>true</code>.
     * 
     * @param args don't mather
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
    }

}
