package c2s.dto;

/**
 *
 * <p>Title: DtoBase</p>
 *
 * <p>Description: DtoBase</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IDto {
    /**
     *Client�˶Ը�Beanδ����
     */
    public static final String OP_NOCHANGE = "N";

    /**
     * Client�˶Ը�Bean���޶�
     */
    public static final String OP_MODIFY = "M";

    /**
     * Client��������¼
     */
    public static final String OP_INSERT = "I";

    /**
     * Client��ɾ����¼
     */
    public static final String OP_DELETE = "D";

    /**
     * getOp()
     * @return String
     */
    public String getOp();

    /**
     * setOp(String op)
     * @param op String
     */
    public void setOp(String op);

    /**
     * isChanged()
     * @return boolean
     */
    public boolean isChanged();

    public boolean isInsert();

    public boolean isModify();

    public boolean isDelete();

    public boolean isReadonly();
}
