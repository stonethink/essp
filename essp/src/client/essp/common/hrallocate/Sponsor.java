package client.essp.common.hrallocate;

public interface Sponsor {
    /**
     * 返回已有的人。形如{ {userId1,userName1}, {userId2,userName2} }
     * 如果没有，可返回null
     * 在分配之初会调用此函数。
     * @return String[][]
     */
    public String[][] getOldData();

    /**
     * 调用者应在这处理分配的结果。newData为分配的结果，形如{ {userId1,userName1}, {userId2,userName2} }
     * 如果没有，返回null
     * @param newData String[][]
     */
    public void setNewData(String[][] newData);

}
