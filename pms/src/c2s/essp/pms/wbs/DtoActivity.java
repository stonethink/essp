package c2s.essp.pms.wbs;

import java.io.Serializable;

public class DtoActivity extends DtoWbsActivity implements Serializable {
    public DtoActivity() {
        this.setActivity(true);
    }
}
