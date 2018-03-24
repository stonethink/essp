package com.wits.excel;

import com.wits.util.StringUtil;

/**
 * 通过两点(左上和右下)来表示Excel中的一片区域
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class ExcelArea {
  private CellPosition begin;
  private CellPosition end;

  public ExcelArea(String area) {
    setArea(area);
  }

  public void setArea(String area) {
    if (area == null) {
      throw new IllegalArgumentException("The area can not be null!");
    }
    String[] sp = StringUtil.splitS(area, " x ");
    int iSN = sp.length;
    if(iSN != 2)
        throw new IllegalArgumentException("["+area+"] can not be a area!");
    begin = new CellPosition(sp[0]);
    end = new CellPosition(sp[1]);
  }

  public CellPosition getBegin() {
    return begin;
  }

  public void setBegin(CellPosition begin) {
    this.begin = begin;
  }

  public CellPosition getEnd() {
    return end;
  }

  public void setEnd(CellPosition end) {
    this.end = end;
  }

  public static void main(String[] args) throws Exception {

  }

}
