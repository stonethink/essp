package com.wits.excel;

/**
 * 表示Excel种一个单元格式的位置,行用数字表示,从1开始,
 * 列可用字符或数字开始,从'A'或1开始
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class CellPosition {
  private int column = 0;
  private int row = 0;
  /**
   * @param row int:位置所在的行数
   * @param column int:位置所在的列数
   */
  public CellPosition(int row,int column) {
      setRow(row);
      setColumn(column);
  }
  /**
   * 用字符'A'-'Z'或'a'-'z'表示列号
   * @param row char
   * @param column int
   */
  public CellPosition(int row,char column) {
      setRow(row);
      if(!Character.isLetter(column))
          throw new IllegalArgumentException("column ["+column+"] must be a char!");
      int temp = 0;
      if(Character.isLowerCase(column)){
          temp = column - 'a' + 1;
      }else{
          temp = column - 'A' + 1;
      }
      setColumn(temp);
  }
  /**
   * 用Excel表示行和列的方式,如"A1"
   * @param position String
   */
  public CellPosition(String position) {
    setPosition(position);
  }

  public void setPosition(CellPosition position) {
    setColumn(position.getColumn());
    setRow(position.getRow());
  }

  public void setPosition(String position) {
      if (position == null || position.equals("")) {
          setRow(1);
          setColumn(1);
          return;
      }
      position = position.toUpperCase();
      char[] ca = position.toCharArray();
      int length = ca.length;
      if(Character.isDigit(position.charAt(0)))
          throw new IllegalArgumentException("["+position+"]:The first char of position must be letter!");
      if(Character.isLetter(position.charAt(length - 1)))
          throw new IllegalArgumentException("["+position+"]:The last char of position must be digit!");
      //从后往前遍历,所有的数字作为行值
      int index = length - 1;
      while (Character.isDigit(position.charAt(index--)))
          ;
      index += 2;
      String rowStr = position.substring(index,length);
      setRow(Integer.parseInt(rowStr));
      index -- ;
      int temp = 0;
      int power = 0;
      for(;index >= 0 ; index --){
          char ch = position.charAt(index);
          if(Character.isDigit(ch))
              throw new IllegalArgumentException("["+position+"]:The colunm can not contain digit!");
          temp += (ch - 'A' + 1) * Math.pow(26,power);
          power ++;
      }
      setColumn(temp);
  }

  public int getColumn() {
    return column;
  }


  public int getRow() {
    return row;
  }

  public void setColumn(int column) {
      if(column <= 0)
          throw new IllegalArgumentException("column ["+column+"] must be lager than 0!");
      this.column = column;
  }

  public void setRow(int row) {
      if(row <= 0)
          throw new IllegalArgumentException("row ["+row+"] must be lager than 0!");
      this.row = row;
  }


  public static void main(String[] args) throws Exception {
//    test("");
//    test("B");
//    test("$B");
//    test("BB");
//    test("4");
//    test("$4");
//    test("44");
//    test("B4");
//    test("BB4");
//    test("B44");
//    test("$B4");
//    test("B$4");
//    test("$B$4");
  }




}
