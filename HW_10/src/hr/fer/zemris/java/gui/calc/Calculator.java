package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * An electronic calculator is a small, portable electronic device used to
 * perform both basic operations of arithmetic and complex mathematical
 * operations. This is an implementation of simple calculator.
 * 
 * @author Ante Spajic
 *
 */
public class Calculator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5016122500695877672L;

	/** Panel that is used for storing components. */
	private JPanel p;
	/**
	 * Label where the result is stored.
	 */
	private JLabel result;
	
	/** Checkbox that is used for inverting invertable operations. */
	private JCheckBox inv;

	/** Calculator stack. */
	private String stack;

	/** cached argument used for binary operations. */
	private String cachedArgument;
	
	/** cached operator used for binary operations. */
	private BinaryOperator cachedOperator;
	
	/** a flag that signals that a binary operator has been pressed. */
	private boolean binaryOperationStarted;
	
	/** a flag that signals that '=' sign has been pressed. */
	private boolean isResult;
	
	/** flag that signals if a new argument is needed for an operation. */
	private boolean newArgument;

	/**
	 * Constructor that initializes a new calculator.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Turbo super mega ultra fun Calculator");
		setLocation(20, 20);
		setSize(475, 323);
		initGUI();
	}

	/**
	 * Method used to initialize Graphical user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		this.p = new JPanel(new CalcLayout(3));

		this.result = new JLabel("0");
		result.setBackground(Color.YELLOW);
		result.setHorizontalAlignment(JTextField.RIGHT);
		result.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Font font = new Font("Arial", Font.BOLD, 15);
		result.setFont(font);
		result.setForeground(Color.decode("#191970"));
		result.setOpaque(true);
		p.add(result, "1,1");

		this.inv = new JCheckBox("inv");
		inv.setBackground(Color.decode("#729FCF"));
		p.add(inv, "5,7");

		addInversiveComponents();
		addNumberComponents();
		addBinaryOperations();
		addStorageOperators();

		setMinimumSize(p.getMinimumSize());
		setMaximumSize(p.getMaximumSize());
		setPreferredSize(p.getPreferredSize());
		cp.add(p);
	}

	/**
	 * Adds buttons associated with storage operations.
	 */
	private void addStorageOperators() {
		createButton("clr", "1,7", l -> result.setText("0"));
		createButton("res", "2,7", l -> reset());
		createButton("push", "3,7", l -> stack = result.getText());
		createButton("pop", "4,7", l -> {
			if (stack == null || stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Stack is empty");
				return;
			}
			result.setText(stack);
			stack = "";
		});
	}

	/**
	 * Adds buttons associated with binary operations.
	 */
	private void addBinaryOperations() {
		createButton("=", "1,6", l -> calculate());
		createButton("/", "2,6", l -> doBinaryOperation(BinaryOperator.DIVIDE));
		createButton("*", "3,6", l -> doBinaryOperation(BinaryOperator.MULTIPLY));
		createButton("-", "4,6", l -> doBinaryOperation(BinaryOperator.SUBTRACT));
		createButton("+", "5,6", l -> doBinaryOperation(BinaryOperator.ADD));
	}

	/**
	 * Adds buttons with numbers.
	 */
	private void addNumberComponents() {
		createButton("0", "5,3", new NumberActionListener("0"));
		createButton("1", "4,3", new NumberActionListener("1"));
		createButton("2", "4,4", new NumberActionListener("2"));
		createButton("3", "4,5", new NumberActionListener("3"));
		createButton("4", "3,3", new NumberActionListener("4"));
		createButton("5", "3,4", new NumberActionListener("5"));
		createButton("6", "3,5", new NumberActionListener("6"));
		createButton("7", "2,3", new NumberActionListener("7"));
		createButton("8", "2,4", new NumberActionListener("8"));
		createButton("9", "2,5", new NumberActionListener("9"));
		createButton("+/-", "5,4", l -> {
			if (result.getText().equals("0"))
				return;
			else if (result.getText().startsWith("-"))
				result.setText(result.getText().substring(1));
			else
				result.setText("-" + result.getText());
		});
		createButton(".", "5,5", l -> dotOperator());
	}
	
	/**
	 * Adds buttons associated with inversive operations.
	 */
	private void addInversiveComponents() {
		createButton("1/x", "2,1", new InversiveListener(l -> Math.pow(l, -1), l -> Math.pow(l, -1)));
		createButton("log", "3,1", new InversiveListener(Math::log10, l -> Math.pow(10, l)));
		createButton("ln", "4,1", new InversiveListener(Math::log, Math::exp));
		createButton("x^n", "5,1", l -> doBinaryOperation(BinaryOperator.POWER));
		createButton("sin", "2,2", new InversiveListener(Math::sin, Math::asin));
		createButton("cos", "3,2", new InversiveListener(Math::cos, Math::acos));
		createButton("tan", "4,2", new InversiveListener(Math::tan, Math::atan));
		createButton("ctg", "5,2", new InversiveListener(l -> Math.tan(1 / l), l -> Math.atan(1 / l)));
	}


	/**
	 * Creates a button and places it inside a calculator layout.
	 * Method takes button text, position inside a layout and an action listener to perform an action when the button has been pressed.
	 * 
	 * @param name text on a button
	 * @param position position of a button
	 * @param action action that button performs
	 */
	private void createButton(String name, String position, ActionListener action) {
		JButton btn = new JButton(name);
		btn.addActionListener(action);
		btn.setBackground(Color.decode("#729FCF"));
		p.add(btn, position);
	}

	/**
	 * Resets the calculator.
	 */
	private void reset() {
		result.setText("0");
		cachedArgument = "0";
		binaryOperationStarted = false;
		isResult = false;
		newArgument = false;
	}

	/**
	 * Method that performs an operation when '=' is pressed.
	 */
	private void calculate() {
		if (cachedOperator == null) {
			return;
		}
		String currentArg = result.getText();
		doOperation(cachedArgument, currentArg);
		if (binaryOperationStarted) {
			cachedArgument = currentArg;
		}
		isResult = true;
		binaryOperationStarted = false;
	}

	/**
	 * Performs the cached binary operation.
	 *
	 * @param firstOp first operand
	 * @param secondOp second operand
	 */
	private void doOperation(String firstOp, String secondOp) {
		double cachedArg = Double.parseDouble(firstOp);
		double currentArg = Double.parseDouble(secondOp);
		switch (cachedOperator) {
		case DIVIDE:
			if (!isResult) {
				result.setText(Double.toString(cachedArg / currentArg));
			} else {
				result.setText(Double.toString(currentArg / cachedArg));
			}
			break;
		case MULTIPLY:
			result.setText(Double.toString(cachedArg * currentArg));
			break;
		case SUBTRACT:
			result.setText(Double.toString(cachedArg - currentArg));
			break;
		case ADD:
			result.setText(Double.toString(cachedArg + currentArg));
			break;
		case POWER:
			if (inv.isSelected()) {
				result.setText(Double.toString(Math.pow(cachedArg, 1 / currentArg)));
			} else {
				result.setText(Double.toString(Math.pow(cachedArg, currentArg)));
			}
			break;
		}
	}

	/**
	 * Method that is used to executed binary operations.
	 * 
	 * @param operator binary operator
	 */
	private void doBinaryOperation(BinaryOperator operator) {
		if (binaryOperationStarted) {
			calculate();
		}
		cachedArgument = result.getText();
		cachedOperator = operator;
		binaryOperationStarted = true;
		newArgument = true;
	}

	/**
	 * Method that is called when an '.' is called, it does the intelligence behind.
	 */
	private void dotOperator() {
		if (result.getText().indexOf(".") == -1) {
			result.setText(result.getText() + ".");
		} else if (newArgument || isResult) {
			result.setText("0.");
			newArgument = false;
			isResult = false;
		}
	}

	/**
	 * Entry point to the program, summons the window.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

	/**
	 * The listener interface for receiving numberAction events.
	 * The class that is interested in processing a numberAction
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addNumberActionListener<code> method. When
	 * the numberAction event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see NumberActionEvent
	 */
	private class NumberActionListener implements ActionListener {

		/** The number. */
		private String number;

		/**
		 * Instantiates a new number action listener.
		 *
		 * @param number the number
		 */
		public NumberActionListener(String number) {
			this.number = number;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (result.getText().equals("0") || newArgument || isResult) {
				result.setText(number);
				isResult = false;
				newArgument = false;
			} else {
				result.setText(result.getText() + number);
			}
		}
	}

	/**
	 * The listener interface for receiving inversive events.
	 * The class that is interested in processing a inversive
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addInversiveListener<code> method. When
	 * the inversive event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see InversiveEvent
	 */
	private class InversiveListener implements ActionListener {

		/** The action. */
		private Function<Double, Double> action;
		
		/** The inverted action. */
		private Function<Double, Double> invertedAction;

		/**
		 * Instantiates a new inversive listener.
		 *
		 * @param action the action
		 * @param invertedAction the inverted action
		 */
		public InversiveListener(Function<Double, Double> action, Function<Double, Double> invertedAction) {
			this.action = action;
			this.invertedAction = invertedAction;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (inv.isSelected()) {
				result.setText(Double.toString(invertedAction.apply(Double.parseDouble(result.getText()))));
			} else {
				result.setText(Double.toString(action.apply(Double.parseDouble(result.getText()))));
			}

		}
	}
}
