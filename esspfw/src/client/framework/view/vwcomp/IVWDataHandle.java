package client.framework.view.vwcomp;

/**
 * <p>Title: </p>
 * <p>Description: 此接口定义UI的数据检查、UI数据初始化、UI数据收集和UI数据赋值</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */
public interface IVWDataHandle {
    /**
     * 检查UI上数据是否有改动过。这个方法没有定义到IVWComponent中，因为考虑到
     * 只有主界面才能判断出某个控件的数据是否被改动（虽然这个控件中途可能经过无数
     * 次修改，但最终的值与初值相同，主界面可判定此控件中的数据没有被修改）。
     *
     * 定义此方法是为了能统一处理界面数据是否被修改的检查。至于具体的判断标准，请各个
     * UI主程序自行定义。
     *
     * @return boolean
     */
    public boolean checkModified();

    /**
     * 用于设置界面初始值
     */
    public void initData();

    /**
     * 将数据设置到界面上
     * 通常情况下，可以使用共通程序中提供的方法来设置界面数据。
     * 但是考虑到可能存在某些复杂情况下共通程序无法处理，请在setData中加上特别处理
     */
    public void setData();

    /**
     * 收集界面上的数据
     * 通常情况下，可以使用共通程序中提供的方法来获取界面数据。
     * 但是考虑到可能存在某些复杂情况下共通程序无法处理，请在getData中加上特别处理
     */
    public void getData();

    /**
     * 数据检查。
     * 这个方法用于提交整个界面数据前作检查。单个控件的数据检查请在各个控件的事件中处理
     */
    public void validateData();
}
