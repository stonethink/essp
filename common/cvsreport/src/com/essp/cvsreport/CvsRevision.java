package com.essp.cvsreport;
import java.util.Calendar;

public class CvsRevision {
  CvsFile cvsFile;

  String id;
  Calendar date;
  String author;
  String state;
  int lineAdd;
  int lineSubstract;
  String msg;

  String action;

  public CvsRevision() {
  }

  //参数upperRev的版本的时间新些
  public void secondPass(CvsRevision early){
    if( early == null ){
      if( "Exp".equals( getState() ) == true ){
        this.action = Constant.ACTION_ADD;
      }else if( "dead".equals( getState() ) == true ){
        this.action = Constant.ACTION_REMOVE;
      }else{
        throw new CvsException(CvsException.SYMBOL_UNKNOWN, "The file["+getCvsFile().getFullName()+"]'s state["+getState()+"] is unknown.It is not in('Exp', 'dead').");
      }
    }else if( early.isRemoveAction() ){
      if( "Exp".equals( getState() ) == true ){
        this.action = Constant.ACTION_ADD;
      }else if( "dead".equals( getState() ) == true ){
        this.action = Constant.ACTION_REMOVE;
      }else{
        throw new CvsException(CvsException.SYMBOL_UNKNOWN, "The file["+getCvsFile().getFullName()+"]'s state["+getState()+"] is unknown.It is not in('Exp', 'dead').");
      }
    }else{
      if( "Exp".equals( getState() ) == true ){
        this.action = Constant.ACTION_MODIFY;
      }else if( "dead".equals( getState() ) == true ){
        this.action = Constant.ACTION_REMOVE;
      }else{
        throw new CvsException(CvsException.SYMBOL_UNKNOWN, "The file["+getCvsFile().getFullName()+"]'s state["+getState()+"] is unknown.It is not in('Exp', 'dead').");
      }
    }
  }

  public String getAuthor() {
    return author;
  }
  public Calendar getDate() {
    return date;
  }
  public String getId() {
    return id;
  }

  public String getMsg() {
    return msg;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    if( state.indexOf("/") != -1 ){
      int i = 0;
    }
    this.state = state;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public void setId(String id) {
    this.id = id;
  }

  public void setDate(Calendar date) {
    if( date == null ){
      throw new CvsException(CvsException.DATA_INVALID, "The revision["+getId()+"] of file["+getCvsFile().getFullName()+"] has no date.");
    }
    this.date = date;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public int getLineAdd() {
    return lineAdd;
  }
  public int getLineSubstract() {
    return lineSubstract;
  }
  public void setLineAdd(int lineAdd) {
    this.lineAdd = lineAdd;
  }
  public void setLineSubstract(int lineSubstract) {
    this.lineSubstract = lineSubstract;
  }
  public CvsFile getCvsFile() {
    return cvsFile;
  }
  public void setCvsFile(CvsFile cvsFile) {
    this.cvsFile = cvsFile;
  }
  public String getAction() {
    return action;
  }

  public boolean isRemoveAction(){
    return Constant.ACTION_REMOVE.equals(getAction());
  }

  public boolean isAddAction(){
    return Constant.ACTION_ADD.equals(getAction());
  }

  public boolean isModifyAction(){
    return Constant.ACTION_MODIFY.equals(getAction());
  }

  public String getActionString(){
    if( Constant.ACTION_ADD.equals(getAction()) ){
      return "新增";
    }else if( Constant.ACTION_MODIFY.equals(getAction()) ){
      return "修改";
    }else if( Constant.ACTION_REMOVE.equals(getAction()) ){
      return "删除";
    }else{
      return "Unknown";
    }
  }

}
