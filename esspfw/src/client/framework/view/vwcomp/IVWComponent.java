package client.framework.view.vwcomp;

import validator.Validator;
import validator.ValidatorResult;

/**
 *
 * <p>Title: public interface IVWComponent</p>
 * <p>Description: Interface of View Component
 *     重载自定义View Component，必须定义该Component所绑定的IVMComponent
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @author Stone
 * @version 1.0
 */
public interface IVWComponent {
    /**
     * 获取控件所对应的数据名称
     * @return String
     */
    public String getName();

    /**
     * 向控件赋值，即将业务数据赋值给控件
     * @param value Object
     */
    public void setUICValue(Object value);

    /**
     * 从控件取值，即将控件中所输入的数据返回为业务数据
     * @return Object
     */
    public Object getUICValue();

    /**
     * 设置控件验证所采用的Class类型
     * @param dtoClass Class
     */
    public void setDtoClass(Class dtoClass);

    /**
     * 向Component 设置validator对象
     * @param validator Validator
     */
    public void setValidator(Validator validator);

    /**
     * 验证控件输入数据的合法法，合法返回True，否则返回False；
     * 如果未设置Validator或Dto Class则不进行验证返回值为True
     * @return boolean
     */
    public boolean validateValue();

    public ValidatorResult getValidatorResult();

    /**
     * 设置提示信息
     * @param text String 提示信息
     */
    public void setToolTipText(String text);

    /**
     * 设置控件错误或正常显示的样式（前景和背景）
     * 如果flag=true,即设置为错误的样式
     * 否则，设置为正常的样式
     * @param flag boolean
     */
    public void setErrorField(boolean flag);


    /**
     * 设置控件是否可用
     * @param isEnabled boolean
     */
    public void setEnabled(boolean isEnabled);

    /**
     * 复制控件
     * @return Object
     */
    public IVWComponent duplicate();

    /**
     * 将控件向右偏移offet距离(通过getOffet()得到)。比如当控件作为TreeTable的editor时,就要控件向右偏移一些距离
     * @return boolean
     */
    public boolean isBReshap();

    public void setBReshap(boolean bReshap);

    //返回偏移的距离
    public int getOffset();

    public void setOffset(int offset);

    public int getHorizontalAlignment();

    public boolean isEnabled();
}
