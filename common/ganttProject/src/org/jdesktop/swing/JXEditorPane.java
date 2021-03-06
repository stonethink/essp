/*
 * $Id: JXEditorPane.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing;

import java.awt.Component;
import java.awt.Dimension;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.IOException;
import java.io.Reader;

import java.net.URL;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import org.jdesktop.swing.actions.ActionManager;

/**
 * An extended editor pane which has the following features built in:
 * <ul>
 *   <li>Text search
 *   <li>undo/redo
 *   <li>simple html/plain text editing
 * </ul>
 *
 * @author Mark Davidson
 */
public class JXEditorPane extends JEditorPane implements Searchable {

    private Matcher matcher;

    private UndoableEditListener undoHandler;
    private UndoManager undoManager;
    private CaretListener caretHandler;
    private JComboBox selector;

    // The ids of supported actions. Perhaps this should be public.
    private final static String ACTION_FIND = "find";
    private final static String ACTION_UNDO = "undo";
    private final static String ACTION_REDO = "redo";

    public JXEditorPane() {
        init();
    }

    public JXEditorPane(String url) throws IOException {
        super(url);
        init();
    }

    public JXEditorPane(String type, String text) {
        super(type, text);
        init();
    }

    public JXEditorPane(URL initialPage) throws IOException {
        super(initialPage);
        init();
    }

    private void init() {
        addPropertyChangeListener(new PropertyHandler());
        getDocument().addUndoableEditListener(getUndoableEditListener());
        initActions();
    }

    private class PropertyHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if (name.equals("document")) {
                Document doc = (Document)evt.getOldValue();
                if (doc != null) {
                    doc.removeUndoableEditListener(getUndoableEditListener());
                }

                doc = (Document)evt.getNewValue();
                if (doc != null) {
                    doc.addUndoableEditListener(getUndoableEditListener());
                }
            }
        }

    }

    // pp for testing
    CaretListener getCaretListener() {
        return caretHandler;
    }

    // pp for testing
    UndoableEditListener getUndoableEditListener() {
        if (undoHandler == null) {
            undoHandler = new UndoHandler();
            undoManager = new UndoManager();
        }
        return undoHandler;
    }

    /**
     * Overidden to perform document initialization based on type.
     */
    public void setEditorKit(EditorKit kit) {
        super.setEditorKit(kit);

        if (kit instanceof StyledEditorKit) {
            if (caretHandler == null) {
                caretHandler = new CaretHandler();
            }
            addCaretListener(caretHandler);
        }
    }

    /**
     * Register the actions that this class can handle.
     */
    protected void initActions() {
        ActionMap map = getActionMap();
        map.put(ACTION_FIND, new Actions(ACTION_FIND));
        map.put(ACTION_UNDO, new Actions(ACTION_UNDO));
        map.put(ACTION_REDO, new Actions(ACTION_REDO));
    }

    // undo/redo implementation

    private class UndoHandler implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent evt) {
            undoManager.addEdit(evt.getEdit());
            updateActionState();
        }
    }

    /**
     * Updates the state of the actions in response to an undo/redo operation.
     */
    private void updateActionState() {
        // Update the state of the undo and redo actions
        Runnable doEnabled = new Runnable() {
                public void run() {
                    ActionManager manager = Application.getInstance().getActionManager();
                    manager.setEnabled(ACTION_UNDO, undoManager.canUndo());
                    manager.setEnabled(ACTION_REDO, undoManager.canRedo());
                }
            };
        SwingUtilities.invokeLater(doEnabled);
    }

    /**
     * A small class which dispatches actions.
     * TODO: Is there a way that we can make this static?
     */
    private class Actions extends UIAction {
        Actions(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent evt) {
            String name = getName();
            if (ACTION_FIND.equals(name)) {
                find();
            }
            else if (ACTION_UNDO.equals(name)) {
                try {
                    undoManager.undo();
                } catch (CannotUndoException ex) {
                    ex.printStackTrace();
                }
                updateActionState();
            }
            else if (ACTION_REDO.equals(name)) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException ex) {
                    ex.printStackTrace();
                }
                updateActionState();
            }
            else {
                System.out.println("ActionHandled: " + name);
            }

        }
    }

    /**
     * Retrieves a component which will be used as the paragraph selector.
     * This can be placed in the toolbar.
     * <p>
     * Note: This is only valid for the HTMLEditorKit
     */
    public JComboBox getParagraphSelector() {
        if (selector == null) {
            selector = new ParagraphSelector();
        }
        return selector;
    }

    /**
     * A control which should be placed in the toolbar to enable
     * paragraph selection.
     */
    private class ParagraphSelector extends JComboBox implements ItemListener {

        private Map itemMap;

        public ParagraphSelector() {

            // The item map is for rendering
            itemMap = new HashMap();
            itemMap.put(HTML.Tag.P, "Paragraph");
            itemMap.put(HTML.Tag.H1, "Heading 1");
            itemMap.put(HTML.Tag.H2, "Heading 2");
            itemMap.put(HTML.Tag.H3, "Heading 3");
            itemMap.put(HTML.Tag.H4, "Heading 4");
            itemMap.put(HTML.Tag.H5, "Heading 5");
            itemMap.put(HTML.Tag.H6, "Heading 6");
            itemMap.put(HTML.Tag.PRE, "Preformatted");

            // The list of items
            Vector items = new Vector();
            items.addElement(HTML.Tag.P);
            items.addElement(HTML.Tag.H1);
            items.addElement(HTML.Tag.H2);
            items.addElement(HTML.Tag.H3);
            items.addElement(HTML.Tag.H4);
            items.addElement(HTML.Tag.H5);
            items.addElement(HTML.Tag.H6);
            items.addElement(HTML.Tag.PRE);

            setModel(new DefaultComboBoxModel(items));
            setRenderer(new ParagraphRenderer());
            addItemListener(this);
            setFocusable(false);
        }

        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                applyTag((HTML.Tag)evt.getItem());
            }
        }

        private class ParagraphRenderer extends DefaultListCellRenderer {

            public ParagraphRenderer() {
                setOpaque(true);
            }

            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected,
                                                   cellHasFocus);

                setText((String)itemMap.get(value));

                return this;
            }
        }


        // TODO: Should have a rendererer which does stuff like:
        // Paragraph, Heading 1, etc...
    }

    /**
     * Applys the tag to the current selection
     */
    protected void applyTag(HTML.Tag tag) {
        Document doc = getDocument();
        if (!(doc instanceof HTMLDocument)) {
            return;
        }
        HTMLDocument hdoc = (HTMLDocument)doc;
        int start = getSelectionStart();
        int end = getSelectionEnd();

        Element element = hdoc.getParagraphElement(start);
        MutableAttributeSet newAttrs = new SimpleAttributeSet(element.getAttributes());
        newAttrs.addAttribute(StyleConstants.NameAttribute, tag);

        hdoc.setParagraphAttributes(start, end - start, newAttrs, true);
    }

    private JXFindDialog dialog = null;

    /**
     * The paste method has been overloaded to strip off the <html><body> tags
     * This doesn't really work.
     */
    public void paste() {
        Clipboard clipboard = getToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(this);
        if (content != null) {
            DataFlavor[] flavors = content.getTransferDataFlavors();
            try {
                for (int i = 0; i < flavors.length; i++) {
                    if (String.class.equals(flavors[i].getRepresentationClass())) {
                        Object data = content.getTransferData(flavors[i]);

                        if (flavors[i].isMimeTypeEqual("text/plain")) {
                            // This works but we lose all the formatting.
                            replaceSelection(data.toString());
                            break;
                        } /*
                            else if (flavors[i].isMimeTypeEqual("text/html")) {
                            // This doesn't really work since we would
                            // have to strip off the <html><body> tags
                            Reader reader = flavors[i].getReaderForText(content);
                            int start = getSelectionStart();
                            int end = getSelectionEnd();
                            int length = end - start;
                            EditorKit kit = getUI().getEditorKit(this);
                            Document doc = getDocument();
                            if (length > 0) {
                            doc.remove(start, length);
                            }
                            kit.read(reader, doc, start);
                            break;
                            } */
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void find() {
        if (dialog == null) {
            dialog = new JXFindDialog(this);
        }
        dialog.setVisible(true);
    }

    public int search(String searchString) {
        return search(searchString, -1);
    }

    public int search(String searchString, int columnIndex) {
        Pattern pattern = null;
        if (searchString !=  null) {
            return search(Pattern.compile(searchString, 0), columnIndex);
        }
        return -1;
    }

    public int search(Pattern pattern) {
        return search(pattern, -1);
    }

    public int search(Pattern pattern, int startIndex) {
        return search(pattern, startIndex, false);
    }

    /**
     * @return end position of matching string or -1
     */
    public int search(Pattern pattern, int startIndex, boolean backwards) {
        if (pattern == null) {
            return -1;
        }

        int start = startIndex + 1;
        int end = -1;

        Segment segment = new Segment();
        try {
            Document doc = getDocument();
            doc.getText(start, doc.getLength() - start, segment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        matcher = pattern.matcher(segment.toString());
        if (matcher.find()) {
            start = matcher.start() + startIndex;
            end = matcher.end() + startIndex;
            select(start + 1, end + 1);
        } else {
            return -1;
        }
        return end;
    }

    /**
     * Listens to the caret placement and adjusts the editing
     * properties as appropriate.
     *
     * Should add more attributes as required.
     */
    private class CaretHandler implements CaretListener {
        public void caretUpdate(CaretEvent evt) {
            StyledDocument document = (StyledDocument)getDocument();
            int dot = evt.getDot();
            Element elem = document.getCharacterElement(dot);
            AttributeSet set = elem.getAttributes();

            ActionManager manager = Application.getInstance().getActionManager();
            manager.setSelected("font-bold", StyleConstants.isBold(set));
            manager.setSelected("font-italic", StyleConstants.isItalic(set));
            manager.setSelected("font-underline", StyleConstants.isUnderline(set));

            elem = document.getParagraphElement(dot);
            set = elem.getAttributes();

            // Update the paragraph selector if applicable.
            if (selector != null) {
                selector.setSelectedItem(set.getAttribute(StyleConstants.NameAttribute));
            }

            switch (StyleConstants.getAlignment(set)) {
                // XXX There is a bug here. the setSelected method
                // should only affect the UI actions rather than propagate
                // down into the action map actions.
            case StyleConstants.ALIGN_LEFT:
                manager.setSelected("left-justify", true);
                break;

            case StyleConstants.ALIGN_CENTER:
                manager.setSelected("center-justify", true);
                break;

            case StyleConstants.ALIGN_RIGHT:
                manager.setSelected("right-justify", true);
                break;
            }
        }
    }
}
