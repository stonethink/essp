package c2s.essp.timesheet.account;

/**
 * <p>Title: IAccountIcon</p>
 *
 * <p>Description: AccountNodeViewManager使用此接口来确定Account的界面表现形式</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IAccountStyle {

    /**
     * 此Account是不是部门
     * @return boolean
     */
    public boolean isDept();

    /**
     * 此Account有没有使用P3
     * @return boolean
     */
    public boolean isP3Adapted();
}
