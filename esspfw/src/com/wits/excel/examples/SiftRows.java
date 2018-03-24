package com.wits.excel.examples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class SiftRows {
  public SiftRows() {
  }

  public static void siftRows() throws IOException {
    POIFSFileSystem fs =
        new POIFSFileSystem(new FileInputStream("workbook.xls"));
    HSSFWorkbook wb = new HSSFWorkbook(fs);
    HSSFSheet sheet = wb.getSheetAt(0);

//    HSSFSheet sheet = wb.createSheet("row sheet");

    // Create various cells and rows for spreadsheet.

    // Shift rows 6 - 11 on the spreadsheet to the top (rows 0 - 5)
    System.out.println("row_num="+sheet.getPhysicalNumberOfRows());
    System.out.println("lasRow="+sheet.getLastRowNum());
    sheet.shiftRows(5, sheet.getLastRowNum(), 2,true,true);
//    sheet.shiftRows(5, sheet.getLastRowNum(), 2,true,true);
    HSSFRow row = sheet.getRow(3);
//    row.c

    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
    wb.write(fileOut);
    fileOut.close();
  }

  public static void writeWorkBook() throws IOException {
//    POIFSFileSystem fs =
//        new POIFSFileSystem(new FileInputStream("workbook.xls"));
//    HSSFWorkbook wb = new HSSFWorkbook(fs);
    HSSFWorkbook wb = new HSSFWorkbook();
//    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFSheet sheet = wb.createSheet("row sheet");
    for (int i = 0; i < 10; i++) {
      HSSFRow row = sheet.getRow(i);
      if (row == null) {
        row = sheet.createRow(i);
      }
      for (int j = 0; j < 3; j++) {
        HSSFCell cell = row.getCell((short) j);
        if (cell == null) {
          cell = row.createCell((short) j);
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("Row=" + i + ";Column=" + j);
      }
    }

    // Write the output to a file
    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
    wb.write(fileOut);
    fileOut.close();

  }


  public static void main(String[] args) throws IOException {
//    writeWorkBook();
    siftRows();
  }
}
