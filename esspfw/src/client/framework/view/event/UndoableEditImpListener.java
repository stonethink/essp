package client.framework.view.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoableEditImpListener implements UndoableEditListener {
	protected static final String UndoName = "Undo";
	protected static final String RedoName = "Redo";
	protected static final KeyStroke redoKey = KeyStroke.getKeyStroke("control Y");
	protected static final KeyStroke undoKey = KeyStroke.getKeyStroke("control Z");
	
	private UndoManager undo = new UndoManager();
	protected JTextComponent comp;
	
	public UndoableEditImpListener(JTextComponent comp) {
		this.comp = comp;
		//Undo
		comp.getInputMap().put(undoKey, UndoName);
		comp.getActionMap().put(UndoName, new AbstractAction(UndoName) {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canUndo()) {
						undo.undo();
					}
				} catch (CannotUndoException e) {
				}
			}
		});
		//Redo
		comp.getInputMap().put(redoKey, RedoName);
		comp.getActionMap().put(RedoName, new AbstractAction(RedoName) {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canRedo()) {
						undo.redo();
					}
				} catch (CannotUndoException e) {
				}
			}
		});
	}
	
	public void undoableEditHappened(UndoableEditEvent e) {
		undo.addEdit(e.getEdit());
	}
}
