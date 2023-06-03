package com.tfxing.persondaily.entity.strategy;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Carl
 * @Date: 2022/12/10/10:20
 * @Description: RowWriteHandler
 */
public class CustomMergeStrategy implements RowWriteHandler {


    private Map<Integer,Integer> map = new HashMap<>();

    private String companyName;

    private String glpName;


    public CustomMergeStrategy(Map<Integer,Integer> map, String companyName, String glpName) {
        this.map = map;
        this.companyName = companyName;
        this.glpName = glpName;
    }

    public CustomMergeStrategy() {}

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
                                Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                               Integer relativeRowIndex, Boolean isHead) {
            Sheet sheet = writeSheetHolder.getSheet();
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        // 如果是标题,则直接返回
        if (isHead) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 6, i, i);
            writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
        }
        /*// 获取当前sheet
        Sheet sheet = writeSheetHolder.getSheet();

        int rowNum = row.getRowNum();
        titleHandle(sheet,companyName,glpName);

        if (rowNum <= 2) {
            return;
        }
        Integer lastRowNum = map.get(rowNum);
        if(-1 == lastRowNum) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, lastRowNum, i, i);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }

        for (int i = 10; i < 12; i++) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, lastRowNum, i, i);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }*/
    }

    private void titleHandle(Sheet sheet, String companyName, String glpName) {
        Workbook workbook = sheet.getWorkbook();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)14);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);

        for (int i = 0; i < 7; i++) {
            Cell cell0 = sheet.getRow(1).getCell(i);
            cell0.setCellValue(companyName);
            cell0.setCellStyle(cellStyle);
        }

        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle1.setFont(font);

        for (int i = 7; i < 12; i++) {
            Cell cell7 = sheet.getRow(1).getCell(i);
            cell7.setCellValue(glpName);
            cell7.setCellStyle(cellStyle1);
        }


    }

}
