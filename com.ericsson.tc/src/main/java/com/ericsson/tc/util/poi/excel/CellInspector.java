package com.ericsson.tc.util.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

public interface CellInspector {
    // Inspect cell
    public void inspect(HSSFCell cell);
}
