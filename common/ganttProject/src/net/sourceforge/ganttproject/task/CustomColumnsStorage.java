package net.sourceforge.ganttproject.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.util.DateUtils;

/**
 * TODO Remove tha map Name->customColum to keep only the map Id -> CustomColumn
 * This class stores the CustomColumns.
 * 
 * @author bbaranne (Benoit Baranne) Mar 2, 2005
 */
public class CustomColumnsStorage {
    public static List availableTypes = null;

    public static GanttLanguage language = GanttLanguage.getInstance();

    private static int nextId;

    private final static String ID_PREFIX = "tpc";

    static {
        initTypes();
    }

    /**
     * Column name (String) -> CustomColumn
     */
    // private Map customColumns = null;
    private Map mapIdCustomColum = null;

    /**
     * Creates an instance of CustomColumnsStorage.
     */
    public CustomColumnsStorage() {
        // customColumns = new HashMap();
        mapIdCustomColum = new HashMap();
    }

    public void reset() {
        initTypes();
        mapIdCustomColum.clear();
        nextId = 0;
    }

    // public CustomColumnsStorage(Map customCols)
    // {
    // customColumns = customCols;
    // }

    /**
     * Initialize the available types (text, integer, ...)
     */
    private static void initTypes() {
        availableTypes = new Vector(6);
        availableTypes.add(language.getText("text"));
        availableTypes.add(language.getText("integer"));
        availableTypes.add(language.getText("double"));
        availableTypes.add(language.getText("date"));
        availableTypes.add(language.getText("boolean"));
    }

    /**
     * Changes the language of the available types.
     * 
     * @param lang
     *            New language.
     */
    public static void changeLanguage(GanttLanguage lang) {
        language = lang;
        initTypes();
    }

    /**
     * Add a custom column.
     * 
     * @param col
     *            CustomColumn to be added.
     * @throws CustomColumnsException
     *             A CustomColumnsException of type
     *             CustomColumnsException.ALREADY_EXIST could be thrown if the
     *             CustomColumn that should be added has a name that already
     *             exists.
     */
    public void addCustomColumn(CustomColumn col) throws CustomColumnsException {

        // if (customColumns == null)
        // customColumns = new HashMap();
        // if (customColumns.containsKey(col.getName()))
        // throw new
        // CustomColumnsException(CustomColumnsException.ALREADY_EXIST,
        // col.getName());
        // if (mapIdCustomColum == null)
        // mapIdCustomColum = new HashMap();
        // String id = ID_PREFIX + nextId++;
        // mapIdCustomColum.put(id, col);
        // col.setId(id);
        // customColumns.put(col.getName(), col);

        // -----------
        if (getCustomColumnsNames().contains(col.getName()))
            throw new CustomColumnsException(
                    CustomColumnsException.ALREADY_EXIST, col.getName());

        if (mapIdCustomColum == null)
            mapIdCustomColum = new HashMap();

        String id = col.getId();
        while (id == null) {
            id = ID_PREFIX + nextId++;
            if (existsForID(id))
                id = null;
        }

        mapIdCustomColum.put(id, col);
        col.setId(id);
    }

    /**
     * Removes the CustomColumn whose name is given in parameter. If the column
     * name does not exist, nothing is done.
     * 
     * @param name
     *            Name of the column to remove.
     */
    public void removeCustomColumn(String name) {

        // CustomColumn cc = (CustomColumn) customColumns.remove(name);
        CustomColumn cc;
        try {
            cc = getCustomColumn(name);
            mapIdCustomColum.remove(cc.getId());
        } catch (CustomColumnsException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the number of custom columns.
     * 
     * @return The number of custom columns.
     */
    public int getCustomColumnCount() {
        // return customColumns.size();
        return mapIdCustomColum.size();
    }

    /**
     * Returns a collection containing the names of all the stored custom
     * columns.
     * 
     * @return A collection containing the names of all the stored custom
     *         columns.
     */
    public List getCustomColumnsNames() {
        // return customColumns.keySet();
        // -----
        List c = new ArrayList();
        Iterator it = mapIdCustomColum.keySet().iterator();
        while (it.hasNext()) {
            String id = (String) it.next();
            c.add(((CustomColumn) mapIdCustomColum.get(id)).getName());
        }
        return c;
    }

    /**
     * Returns a collection with all the stored custom columns.
     * 
     * @return A collection with all the stored custom columns.
     */
    public Collection getCustomColums() {
        // return customColumns.values();
        return mapIdCustomColum.values();
    }

    /**
     * @param name
     * @return
     * @throws CustomColumnsException
     */
    public CustomColumn getCustomColumn(String name)
            throws CustomColumnsException {
        // if (!exists(name))
        // throw new CustomColumnsException(CustomColumnsException.DO_NOT_EXIST,
        // name);
        // return (CustomColumn) customColumns.get(name);
        String id = getIdFromName(name);
        if (id == null)
            throw new CustomColumnsException(
                    CustomColumnsException.DO_NOT_EXIST, name);
        return (CustomColumn) mapIdCustomColum.get(id);
    }

    public CustomColumn getCustomColumnByID(String id) {
        return (CustomColumn) mapIdCustomColum.get(id);
    }

    /**
     * Returns true if a custom column has the same name that the one given in
     * parameter, false otherwise.
     * 
     * @param colName
     *            The name of the column to check the existence.
     * @return True if a custom column has the same name that the one given in
     *         parameter, false otherwise.
     */
    public boolean exists(String colName) {
        return getIdFromName(colName) != null;
    }

    public boolean existsForID(String id) {
        return this.mapIdCustomColum.keySet().contains(id);
    }

    public void renameCustomColumn(String name, String newName) {
        // if (customColumns.containsKey(name))
        // {
        // CustomColumn cc = (CustomColumn) customColumns.get(name);
        // cc.setName(newName);
        // customColumns.put(newName, cc);
        // customColumns.remove(name);
        // ((CustomColumn) mapIdCustomColum.get(cc.getId())).setName(newName);
        // }
        String id = getIdFromName(name);
        if (id != null) {
            CustomColumn cc = (CustomColumn) mapIdCustomColum.get(id);
            cc.setName(newName);
        }

    }

    public void changeDefaultValue(String colName, Object newDefaultValue)
            throws CustomColumnsException {
        // if (customColumns.containsKey(colName))
        // {
        // CustomColumn cc = (CustomColumn) customColumns.get(colName);
        // cc.setDefaultValue(newDefaultValue);
        // ((CustomColumn)
        // mapIdCustomColum.get(cc.getId())).setDefaultValue(newDefaultValue);
        // }
        String id = getIdFromName(colName);
        if (id != null) {
            CustomColumn cc = (CustomColumn) mapIdCustomColum.get(id);

            if (newDefaultValue.getClass().isAssignableFrom(cc.getType()))
                cc.setDefaultValue(newDefaultValue);
            else {
                try {
                    if (cc.getType().equals(Boolean.class))
                        cc.setDefaultValue(Boolean.valueOf(newDefaultValue
                                .toString()));
                    else if (cc.getType().equals(Integer.class))
                        cc.setDefaultValue(Integer.valueOf(newDefaultValue
                                .toString()));
                    else if (cc.getType().equals(Double.class))
                        cc.setDefaultValue(Double.valueOf(newDefaultValue
                                .toString()));
                    else if (GregorianCalendar.class.isAssignableFrom(cc
                            .getType()))
                        cc.setDefaultValue(DateUtils.parseDate(newDefaultValue
                                .toString(), GanttLanguage.getInstance()
                                .getLocale()));

                } catch (Exception ee) {
                    throw new CustomColumnsException(
                            CustomColumnsException.CLASS_MISMATCH,
                            "Try to set a '" + newDefaultValue.getClass()
                                    + "' for '" + cc.getType() + "'");
                }

            }
        }
    }

    public String getIdFromName(String name) {
        String id = null;
        Iterator it = mapIdCustomColum.values().iterator();
        while (it.hasNext()) {
            CustomColumn cc = (CustomColumn) it.next();
            if (cc.getName().equals(name)) {
                id = cc.getId();
                break;
            }
        }
        return id;
    }

    public String getNameFromId(String id) {
        return ((CustomColumn) mapIdCustomColum.get(id)).getName();
    }

    /**
     * Add all stored custom column to the given task. All custom column will
     * have the default value.
     * 
     * @param task
     *            The task to process.
     */
    public void processNewTask(Task task) {
        // Iterator it = customColumns.keySet().iterator();
        //
        // while (it.hasNext())
        // {
        // String k = (String) it.next();
        // try
        // {
        // task.getCustomValues().setValue(k, ((CustomColumn)
        // customColumns.get(k)).getDefaultValue());
        // }
        // catch (CustomColumnsException e)
        // {
        // e.printStackTrace();
        // }
        // }
        Iterator it = mapIdCustomColum.values().iterator();
        while (it.hasNext()) {
            CustomColumn cc = (CustomColumn) it.next();
            try {
                task.getCustomValues().setValue(cc.getName(),
                        cc.getDefaultValue());
            } catch (CustomColumnsException e) {
                e.printStackTrace();
            }
        }

    }

    public String toString() {
        return mapIdCustomColum.toString();
    }
}
