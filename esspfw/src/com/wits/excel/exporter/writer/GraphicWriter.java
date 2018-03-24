package com.wits.excel.exporter.writer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import client.image.ComImage;
import java.net.URL;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.util.HashMap;
import java.util.Map;

public class GraphicWriter extends AbstractDataWriter {
       private Map sheetMap = new HashMap();
       private HSSFPatriarch getSheetDrawingPatriarch( HSSFSheet sheet){
           Object obj = sheetMap.get(sheet);
           if(obj == null){
               HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
               sheetMap.put(sheet,patriarch);
               return patriarch;
           }
           return (HSSFPatriarch) obj;
       }
        /**
         * 写入图片
         * @param workbook HSSFWorkbook
         * @param cell HSSFCell
         * @param value Object
         */
        public void writeCell(HSSFWorkbook workbook, HSSFSheet sheet,HSSFRow row,HSSFCell cell, Object value) {
            HSSFPatriarch patriarch = getSheetDrawingPatriarch(sheet);
            HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,8,8,
                    cell.getCellNum(),row.getRowNum(),(short)(cell.getCellNum() + 1),row.getRowNum() + 1);
            if(value instanceof String){
                String imgName = (String) value;
                patriarch.createPicture(anchor,
                                       workbook.addPicture(getImage(imgName),
                                                           HSSFWorkbook.PICTURE_TYPE_PNG
                                       )
                   );

            }else if(value instanceof byte[]){
                patriarch.createPicture(anchor,
                                        workbook.addPicture((byte[])value,
                                                            HSSFWorkbook.PICTURE_TYPE_PNG
                                        )
                    );
            }

        }

        private  byte[] getImage(String imgName){
            URL url = ComImage.getImage(imgName);
            byte[] buffer = new byte[1024];
            byte[] result = null;
            try {
                InputStream in = url.openStream();

                BufferedInputStream bin = new BufferedInputStream(in);

                ByteArrayOutputStream bout = new ByteArrayOutputStream();

                while(bin.read(buffer) != -1){
                    bout.write(buffer);
                }

                result = bout.toByteArray();
                in.close();
                bout.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return result;
        }


}
