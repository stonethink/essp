package client.essp.common.hrallocate;

public interface Sponsor {
    /**
     * �������е��ˡ�����{ {userId1,userName1}, {userId2,userName2} }
     * ���û�У��ɷ���null
     * �ڷ���֮������ô˺�����
     * @return String[][]
     */
    public String[][] getOldData();

    /**
     * ������Ӧ���⴦�����Ľ����newDataΪ����Ľ��������{ {userId1,userName1}, {userId2,userName2} }
     * ���û�У�����null
     * @param newData String[][]
     */
    public void setNewData(String[][] newData);

}
