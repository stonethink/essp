package client.framework.view;

import com.wits.util.Parameter;



public interface IVWWorkArea {

    //外界调用setParameter给workArea设数据
    //workArea可能直接显示这些数据, 也可能用这些数据来据作为条件到数据库中查找数据
    public void setParameter( Parameter parameter );

    //刷新WorkArea
    //一般在外界设新的parameter时要刷新自己， 但只有在真正使用时才调用refreshWorkArea.
    public void refreshWorkArea();

    //保存workArea中组件的值，如果有必要的话， 可以在这里将数据保存到数据库中去。
    public void saveWorkArea();

    //saveWorkArea是否成功，一般在检查数据通不过或者保存数据不成功时返回false
    public boolean isSaveOk();

}
