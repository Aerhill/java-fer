package hr.fer.zemris.cmdapps.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;

/**
 * This is an action that clears the model.
 * 
 * @author Ante Spajic
 *
 */
public class ClearModelAction extends AbstractAction {

	private static final long serialVersionUID = -8554439501053580695L;

	private DrawingModel model;
	
	public ClearModelAction(DrawingModel model) {
		this.model = model;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        model.clear();
    }
}
