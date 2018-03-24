package client.framework.model;

import java.util.HashMap;
import validator.ValidatorResult;

public class VMTableValidatorResult {
    private HashMap resultMap = new HashMap();

    public void setResult(int row, int column, ValidatorResult result) {
        resultMap.put(row + "@" + column, result);
    }

    public ValidatorResult getResult(int row, int column) {
        return (ValidatorResult) resultMap.get(row + "@" + column);
    }

    public void clear() {
        resultMap.clear();
    }
}
