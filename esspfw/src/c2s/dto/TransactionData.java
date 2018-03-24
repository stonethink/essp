package c2s.dto;

import validator.ValidatorResult;
import java.io.Serializable;

public class TransactionData implements Serializable {
  private InputInfo inputInfo = new InputInfo();
  private ReturnInfo returnInfo = new ReturnInfo();

  public InputInfo getInputInfo() {
    return inputInfo;
  }

  public ReturnInfo getReturnInfo() {
    return returnInfo;
  }

  public ValidatorResult getValidatorResult() {
    return returnInfo.getValidatorResult();
  }

  public void setInputInfo(InputInfo inputInfo) {
    this.inputInfo = inputInfo;
  }

  public void setReturnInfo(ReturnInfo returnInfo) {
    this.returnInfo = returnInfo;
  }

  public void copy(Object other) {
    if (other != null && other instanceof TransactionData) {
      TransactionData otherObj = (TransactionData) other;
      this.getInputInfo().copy(otherObj.getInputInfo());
      this.getReturnInfo().copy(otherObj.getReturnInfo());
    }
  }
}
