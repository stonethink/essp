package com.wits.excel.importer;

import java.util.*;

import junit.framework.*;
import org.apache.poi.hssf.usermodel.*;

public class TestSheetImporter extends TestCase {
    private SheetImporter readExcelSheet = null;
    private Date today = new Date();
    private String[][] config = { {"userLoginId", "string"}, {"domain",
                                "string"},
                                null, {"inDate", "date"}
    };


    public TestSheetImporter(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();

    }


    protected void tearDown() throws Exception {
        readExcelSheet = null;
        super.tearDown();
    }

    public void testGetDataListNormal() {
        initNormalData();
        List lst = readExcelSheet.getDataList();
        assertEquals("return list size", 2, lst.size());
        TestImportBean bean = (TestImportBean) lst.get(0);
        assertEquals("data loginId", "WH0607014", bean.getUserLoginId());
        assertEquals("data domain", "wh", bean.getDomain());
        assertEquals("data date", today, bean.getInDate());
        bean = (TestImportBean) lst.get(1);
        assertEquals("data loginId", "WH0607016", bean.getUserLoginId());
    }

    public void testGetDataListNoData() {
        initNoData();
        List lst = readExcelSheet.getDataList();
        assertNotNull("list not null", lst);
        assertEquals("list size is zero", 0, lst.size());
    }


    public void testGetDataListEmptyData() {
        initEmptyData();
        List lst = readExcelSheet.getDataList();

        assertNotNull("list not null", lst);
        assertEquals("list size is 1", 1, lst.size());
        TestImportBean bean = (TestImportBean) lst.get(0);
        assertNull("user loginId is null ", bean.getUserLoginId());
        assertNull("user domain is null ", bean.getDomain());
        assertNull("user inDate is null ", bean.getInDate());
    }

    public void testGetDataListUnNormalData() {
        initUnNormalData();
        String exStringHead = "get cell data error at row:3, column:4";
        String exStr = null;
        try {
            List lst = readExcelSheet.getDataList();
        } catch (Exception e) {
            exStr = e.getMessage();
        }
        assertNotNull("exception not null", exStr);
        assertTrue("assert exception correct", exStr.startsWith(exStringHead));
    }

    public void testGetDataListNoInDateData() {
        initNoInDateData();
        List lst = readExcelSheet.getDataList();

        assertEquals("return list size", 2, lst.size());
        TestImportBean bean = (TestImportBean) lst.get(0);
        assertEquals("data loginId", "WH0607014", bean.getUserLoginId());
        assertEquals("data domain", "wh", bean.getDomain());
        assertEquals("data date", today, bean.getInDate());
        bean = (TestImportBean) lst.get(1);
        assertEquals("data loginId", "WH0607016", bean.getUserLoginId());
        assertNull("InDate is null", bean.getInDate());
    }

    private void initNormalData() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cellLoginId = row.createCell((short) 0);
        HSSFCell cellDoman = row.createCell((short) 1);
        HSSFCell cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607014"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));
        cellInDate.setCellValue(today);
        row = sheet.createRow(2);
        cellLoginId = row.createCell((short) 0);
        cellDoman = row.createCell((short) 1);
        cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607016"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));
        cellInDate.setCellValue(today);

        readExcelSheet = new SheetImporter(sheet, TestImportBean.class, config);
    }

    private void initNoData() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        readExcelSheet = new SheetImporter(sheet, TestImportBean.class, config);
    }

    private void initEmptyData() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        readExcelSheet = new SheetImporter(sheet, TestImportBean.class, config);
    }


    private void initUnNormalData() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cellLoginId = row.createCell((short) 0);
        HSSFCell cellDoman = row.createCell((short) 1);
        HSSFCell cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607014"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));
        cellInDate.setCellValue(today);
        row = sheet.createRow(2);
        cellLoginId = row.createCell((short) 0);
        cellDoman = row.createCell((short) 1);
        cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607016"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));
        cellInDate.setCellValue(new HSSFRichTextString("faffasf"));

        readExcelSheet = new SheetImporter(sheet, TestImportBean.class, config);
    }

    private void initNoInDateData() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cellLoginId = row.createCell((short) 0);
        HSSFCell cellDoman = row.createCell((short) 1);
        HSSFCell cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607014"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));
        cellInDate.setCellValue(today);
        row = sheet.createRow(2);
        cellLoginId = row.createCell((short) 0);
        cellDoman = row.createCell((short) 1);
        cellInDate = row.createCell((short) 3);

        cellLoginId.setCellValue(new HSSFRichTextString("WH0607016"));
        cellDoman.setCellValue(new HSSFRichTextString("wh"));

        readExcelSheet = new SheetImporter(sheet, TestImportBean.class, config);
    }
}
