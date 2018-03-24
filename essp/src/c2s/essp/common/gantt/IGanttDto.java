package c2s.essp.common.gantt;

public interface IGanttDto {
    public static int LINK_SF = 1;
    public static int LINK_SS = 2;
    public static int LINK_FS = 4;
    public static int LINK_FF = 8;

    public static int NONE_STYLE = 64;
    public static int LEAF = 128;
    public static int TASK = 256;
    public static int PROCESS = 512;
    public static int ACTUAL_TASK = 1024;

    public int getStyle();

    public long getBeginTime();

    public long getEndTime();

    public double getProcessRate();

//    int getLinkCount();
//
//    int getLinkRow(int i);
//
//    int getLinkType(int i);
}
