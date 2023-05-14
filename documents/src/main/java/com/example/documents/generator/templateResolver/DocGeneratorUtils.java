package com.example.documents.generator.templateResolver;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

@Component
public class DocGeneratorUtils {
    void createTableRow(XWPFTable table, String columnName, String columnValue) {
        XWPFTableRow tableRow = table.createRow();
        XWPFParagraph paragraph = tableRow.getCell(0).addParagraph();
        setRun(paragraph.createRun(), "Courier", 14, columnName);
        XWPFParagraph paragraph1 = tableRow.getCell(1).addParagraph();
        setRun(paragraph1.createRun(), "Courier", 14, " " + columnValue + " ");
    }

    void setRun(XWPFRun run, String fontFamily, int fontSize, String text) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setText(text);
    }
}
