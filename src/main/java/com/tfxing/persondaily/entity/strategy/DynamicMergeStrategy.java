package com.tfxing.persondaily.entity.strategy;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class DynamicMergeStrategy implements RowWriteHandler {
    private List<String> excelList;

    public DynamicMergeStrategy(List<String> excelList) {
        this.excelList = excelList;
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        for (int i = 0; i < excelList.size(); i++) {
            Cell cell = row1.createCell(i);
            cell.setCellValue("标题"+i);

            Cell cell1 = row2.createCell(i);
            cell1.setCellValue(excelList.get(i));
        }
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {

    }
}
