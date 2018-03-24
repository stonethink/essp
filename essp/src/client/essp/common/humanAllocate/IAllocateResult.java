package client.essp.common.humanAllocate;

public interface IAllocateResult {
    //参数newUserIds为分配后选中的人的id，当有多个时，用逗号","分隔
    void setNewUserIds(String newUserIds );

    //参数newUserNames分配后选中的人的name，当有多个时，用逗号","分隔
    void setNewUserNames(String newUserNames);
}
