/*
 * $Id: TableCellRenderers.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.table;

import java.awt.Component;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.geom.Rectangle2D;

import java.text.DateFormat;
import java.text.NumberFormat;

import java.util.Date;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swing.data.Link;

public class TableCellRenderers {

    private static HashMap typeMap;

    static {
        // load the handler map with classes designed to handle the
        // type-specific rendering
        typeMap = new HashMap();
        typeMap.put(Number.class,
            "org.jdesktop.swing.table.TableCellRenderers$NumberRenderer");
        typeMap.put(Double.class,
            "org.jdesktop.swing.table.TableCellRenderers$DoubleRenderer");
        typeMap.put(Float.class,
            "org.jdesktop.swing.table.TableCellRenderers$DoubleRenderer");
        typeMap.put(Date.class,
                    "org.jdesktop.swing.table.TableCellRenderers$DateRenderer");
        typeMap.put(Icon.class,
                    "org.jdesktop.swing.table.TableCellRenderers$IconRenderer");
        typeMap.put(Boolean.class,
            "org.jdesktop.swing.table.TableCellRenderers$BooleanRenderer");
        typeMap.put(Link.class,
                    "org.jdesktop.swing.table.TableCellRenderers$LinkRenderer");

    }

    private static String getRendererClassName(Class columnClass) {
        String rendererClassName = (String) typeMap.get(columnClass);
        return rendererClassName != null ? rendererClassName :
            "javax.swing.table.DefaultTableCellRenderer";
    }

    /**
     * @see #getNewDefaultRenderer
     * @param columnClass Class of value being rendered
     * @param rendererClassName String containing the class name of the renderer which
     *        should be returned for the specified column class
     */
    public static void setDefaultRenderer(Class columnClass, String rendererClassName) {
        typeMap.put(columnClass, rendererClassName);
    }

    /**
     * Returns a new instance of the default renderer for the specified class.
     * This differs from JTable:getDefaultRenderer() in that it returns a new
     * instance each time so that the renderer may be set and customized for
     * a particular column.
     *
     * @param columnClass Class of value being rendered
     * @return TableCellRenderer instance which renders values of the specified type
     */
    public static TableCellRenderer getNewDefaultRenderer(Class columnClass) {
        TableCellRenderer renderer = null;
        String rendererClassName = getRendererClassName(columnClass);
        try {
            Class rendererClass = Class.forName(rendererClassName);
            renderer = (TableCellRenderer) rendererClass.newInstance();
        }
        catch (Exception e) {
            renderer = new DefaultTableCellRenderer();
        }
        return renderer;
    }

    TableCellRenderers() {
    }

    /*
     * Default Type-based Renderers:
     * JTable's default table cell renderer classes are private and
     * JTable:getDefaultRenderer() returns a *shared* cell renderer instance,
     * thus there is no way for us to instantiate a new instance of one of its
     * default renderers.  So, we must replicate the default renderer classes
     * here so that we can instantiate them when we need to create renderers
     * to be set on specific columns.
     */
    static class NumberRenderer extends DefaultTableCellRenderer {
        public NumberRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }
    }

    static class DoubleRenderer extends NumberRenderer {
        NumberFormat formatter;
        public DoubleRenderer() {
            super();
        }

        public void setValue(Object value) {
            if (formatter == null) {
                formatter = NumberFormat.getInstance();
            }
            setText( (value == null) ? "" : formatter.format(value));
        }
    }

    static class DateRenderer extends DefaultTableCellRenderer {
        DateFormat formatter;
        public DateRenderer() {
            super();
        }

        public void setValue(Object value) {
            if (formatter == null) {
                formatter = DateFormat.getDateInstance();
            }
            setText( (value == null) ? "" : formatter.format(value));
        }
    }

    static class IconRenderer extends DefaultTableCellRenderer {
        public IconRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        public void setValue(Object value) {
            setIcon( (value instanceof Icon) ? (Icon) value : null);
        }
    }

    static class BooleanRenderer extends JCheckBox
        implements TableCellRenderer {
        public BooleanRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            }
            else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setSelected( (value != null && ( (Boolean) value).booleanValue()));
            return this;
        }
    }

    /**
     * Renders a Link type the link in the table column
     */
    public static class LinkRenderer extends DefaultTableCellRenderer {

        // Should have a way of setting these statically
        private static Color colorLive = new Color(0, 0, 238);
        private static Color colorVisited = new Color(82, 24, 139);

        public void setValue(Object value) {
            if (value != null && value instanceof Link) {
                Link link = (Link) value;

                setText(link.getText());
                setToolTipText(link.getURL().toString());

                if (link.getVisited()) {
                    setForeground(colorVisited);
                }
                else {
                    setForeground(colorLive);
                }
            }
            else {
                super.setValue(value != null? value.toString() : "");
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!getText().equals("")) {
                // Render an underline. A really smart person
                // would actually render an underline font but
                // that's too much for my little brain.
                Rectangle rect = TableCellRenderers.getTextBounds(g, this);

                FontMetrics fm = g.getFontMetrics();
                int descent = fm.getDescent();

                //REMIND(aim): should we be basing the underline on
                //the font's baseline instead of the text bounds?

                g.drawLine(rect.x, (rect.y + rect.height) - descent + 1,
                           rect.x + rect.width,
                           (rect.y + rect.height) - descent + 1);
            }
        }
    }


    /**
     * Returns the bounds that the text of a label will be drawn into.
     * Takes into account the current font metrics.
     */
    static Rectangle getTextBounds(Graphics g, JLabel label) {

	FontMetrics fm = g.getFontMetrics();
	Rectangle2D r2d = fm.getStringBounds(label.getText(), g);
	Rectangle rect = r2d.getBounds();

	int xOffset = 0;
	switch (label.getHorizontalAlignment()) {
	case SwingConstants.RIGHT:
	case SwingConstants.TRAILING:
	    xOffset = label.getBounds().width - rect.width;
	    break;

	case SwingConstants.CENTER:
	    xOffset = (label.getBounds().width - rect.width)/2;
	    break;

	default:
	case SwingConstants.LEFT:
	case SwingConstants.LEADING:
	    xOffset = 0;
	    break;
	}

	int yOffset = 0;
	switch (label.getVerticalAlignment()) {
	case SwingConstants.TOP:
	    yOffset = 0;
	    break;
	case SwingConstants.CENTER:
	    yOffset = (label.getBounds().height - rect.height)/2;
	    break;
	case SwingConstants.BOTTOM:
	    yOffset = label.getBounds().height - rect.height;
	    break;
	}
	return new Rectangle(xOffset, yOffset, rect.width, rect.height);
    }



}
