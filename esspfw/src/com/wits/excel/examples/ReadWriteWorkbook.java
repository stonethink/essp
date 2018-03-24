
/* ====================================================================
   Copyright 2002-2004   Apache Software Foundation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */


package com.wits.excel.examples;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * This example demonstrates opening a workbook, modifying it and writing
 * the results back out.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class ReadWriteWorkbook
{
    public static void main(String[] args)
        throws IOException
    {
        POIFSFileSystem fs      =
                new POIFSFileSystem(new FileInputStream("workbook.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(1);
//        if (row == null)
//            row = sheet.createRow(2);
        HSSFCell cell = row.getCell((short)1);
//        if (cell == null)
//            cell = row.createCell((short)3);
//        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//        cell.setCellValue("a test");
       HSSFCellStyle style =  cell.getCellStyle();
//       style.setFillForegroundColor(HSSFColor.BLUE.index);
       style.setFillBackgroundColor(HSSFColor.BLUE.index);
       style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
       style.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);

       cell.setCellStyle(style);

       HSSFCell cell2 = row.getCell((short)3);
       if(cell2 == null)
           cell2 = row.createCell((short)3);
       cell2.setCellStyle(style);
       cell2.setCellValue("sefsefsef");
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        wb.write(fileOut);
        fileOut.close();

    }
}
