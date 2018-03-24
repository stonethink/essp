package c2s.essp.ebs;

import c2s.dto.IDto;

public interface IDtoEbsAcnt extends IDto{
    public Long getRid();

    public void setRid(Long rid);

    public String getStatus();

    public void setStatus(String status);

    public String getManager();

    public void setManager(String manager);

    public String getCode();

    public void setCode(String code);

    public String getName();

    public void setName(String name);

    public boolean isEbs();

    public boolean isAcnt();
}
