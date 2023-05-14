package com.example.documents.generator.templateResolver;

import com.example.documents.model.entity.Document;
import com.example.documents.model.enums.TemplateType;
import com.example.documents.service.MinioService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class WordDocGenerator {

    private final MinioService minioService;
    private final DocGeneratorUtils docGeneratorUtils;
    private Map<TemplateType, TemplateResolver> templateResolverMap;
    private final AtomicLong atomicLong = new AtomicLong(0);

    public WordDocGenerator(MinioService minioService, MeterRegistry meterRegistry, DocGeneratorUtils docGeneratorUtils, Map<TemplateType, TemplateResolver> templateResolverMap) {
        this.minioService = minioService;
        this.docGeneratorUtils = docGeneratorUtils;
        this.templateResolverMap = templateResolverMap;
        meterRegistry.gauge("test", atomicLong);
    }


    @Timed
    public void generate(Document documentForGenerating) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InvalidFormatException {
        atomicLong.incrementAndGet();
        GetObjectResponse imageById = minioService.getImageById(documentForGenerating.getId());

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        XWPFRun run = title.createRun();
        title.setAlignment(ParagraphAlignment.CENTER);
        String imgFile = "image.jpg";
        InputStream is = new ByteArrayInputStream(imageById.readAllBytes());
        run.setText("{image}");
        run.addBreak();
        run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
        is.close();
        XWPFTable table = document.createTable();

        table.setCellMargins(100, 100, 100, 100);

        XWPFTableRow tableRowOne = table.getRow(0);
        XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
        docGeneratorUtils.setRun(paragraph.createRun(), "Courier", 14, "ФИО");
        docGeneratorUtils.setRun(paragraph.createRun(), "Courier", 14, "ФИО ");
        tableRowOne.addNewTableCell();
        XWPFParagraph paragraph1 = tableRowOne.getCell(1).addParagraph();
        docGeneratorUtils.setRun(paragraph1.createRun(), "Courier", 14, "{full-name}");
//                "Courier", 14,  " " + documentForGenerating.getSurname() + " " + documentForGenerating.getName() + " " + documentForGenerating.getPatronymic() + " ");

        docGeneratorUtils.createTableRow(table, "Дата рождения", "{birthday}");
        docGeneratorUtils.createTableRow(table, "Пол", "{gender}");
        docGeneratorUtils.createTableRow(table, "Номер паспорта", "{passport}");
        docGeneratorUtils.createTableRow(table, "Город рождения", "{city}");
        docGeneratorUtils.createTableRow(table, "Адрес проживания", "{address}");
        docGeneratorUtils.createTableRow(table, "Дата последнего обновления паспорта", "{last-passport-update}");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        document.write(b);
        InputStream input = new ByteArrayInputStream(b.toByteArray());
        minioService.saveDocument(documentForGenerating.getId(), input);
    }
}
