package com.tfxing.persondaily.entity.strategy;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public abstract class AbstractMergeStrategy implements RowWriteHandler {

    private Integer titleRowNum = 0;

    private List<ExcelMergeIndex> mergeIndexList;

    public AbstractMergeStrategy(List<ExcelMergeIndex> mergeIndexList) {
        this.mergeIndexList = mergeIndexList;
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
                                Integer relativeRowIndex, Boolean isHead) {
        if(isHead) {
            titleRowNum+=1;
        }
    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                               Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();

        // 合并单元格
        // 参数说明：开始行，结束行，开始列，结束列
        for (ExcelMergeIndex excelMergeIndex : mergeIndexList) {
            int firstRow = excelMergeIndex.getTopLeftPoint().getRow();
            int lastRow = excelMergeIndex.getBottomRightPoint().getRow();
            int firstCol = excelMergeIndex.getTopLeftPoint().getColumn();
            int lastCol = excelMergeIndex.getBottomRightPoint().getColumn();

            CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow+titleRowNum, lastRow+titleRowNum, firstCol, lastCol);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }
    }

}
