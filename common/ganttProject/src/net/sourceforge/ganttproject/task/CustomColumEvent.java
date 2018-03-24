package net.sourceforge.ganttproject.task;

public class CustomColumEvent {

    public static final int EVENT_ADD = 0;

    public static final int EVENT_REMOVE = 1;

    protected final int myType;

    protected final String myColName;

    public CustomColumEvent(int type, String colName) {
        myType = type;
        myColName = colName;
    }

    public String getColName() {
        return myColName;
    }

    public int getType() {
        return myType;
    }

}
