package c2s.essp.pms.wbs;

import java.io.Serializable;

public class DtoWbs extends DtoWbsActivity implements Serializable {
    public DtoWbs() {
        this.setWbs(true);
    }
}
