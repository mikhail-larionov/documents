package com.example.documents.generator.templateResolver;

import com.example.documents.model.dto.requests.DocumentCreateRequest;
import com.example.documents.model.dto.requests.metadata.IdDocumentMetadata;
import com.example.documents.model.enums.TemplateType;
import com.example.documents.service.MinioService;
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
import java.util.List;
import java.util.Map;

@Component
public class IdTemplateResolver implements TemplateResolver {

    private final MinioService minioService;
    private final DocGeneratorUtils docGeneratorUtils;

    public IdTemplateResolver(MinioService minioService, DocGeneratorUtils docGeneratorUtils) {
        this.minioService = minioService;
        this.docGeneratorUtils = docGeneratorUtils;
    }

    @Override
    public TemplateType getTemplateType() {
        return TemplateType.ID;
    }

    @Override
    public InputStream generateDocument(DocumentCreateRequest documentCreateRequest) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InvalidFormatException {
        IdDocumentMetadata idDocumentMetadata = (IdDocumentMetadata) documentCreateRequest.getDocumentMetadata();

        GetObjectResponse imageById = minioService.getImageById(idDocumentMetadata.getUserId());
        GetObjectResponse documentTemplate = minioService.getDocumentTemplateByTemplateType(documentCreateRequest.getDocumentTemplateType());

        InputStream fis = new ByteArrayInputStream(documentTemplate.readAllBytes());

        XWPFDocument document = new XWPFDocument(fis);

        InputStream is = new ByteArrayInputStream(imageById.readAllBytes());
        String imgFile = "image.jpg";
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("{image}")) {
                        text = text.replace("{image}", "");
                        r.setText(text, 0);
                        r.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
                    }
                }
            }
        }
        Map<String, String> replaceTemplateMap = getReplaceTemplateMap(documentCreateRequest);
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            for (String key : replaceTemplateMap.keySet()) {
                                String text = r.getText(0);
                                if (text != null && text.contains(key)) {
                                    text = text.replace(key, replaceTemplateMap.get(key));
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        document.write(b);
        return new ByteArrayInputStream(b.toByteArray());
    }

    private Map<String, String> getReplaceTemplateMap(DocumentCreateRequest documentCreateRequest) {
        IdDocumentMetadata documentMetadata = (IdDocumentMetadata) documentCreateRequest.getDocumentMetadata();
        return Map.of(
                "{full-name}", documentMetadata.getSurname() + " " + documentMetadata.getName() + " " + documentMetadata.getPatronymic() + " ",
                "{birthday}", documentMetadata.getBirthday(),
                "{gender}", documentMetadata.getGender().getNameValue(),
                "{passport}", documentMetadata.getPassportNumber(),
                "{city}", documentMetadata.getCity(),
                "{address}", documentMetadata.getLivingAddress(),
                "{last-passport-update}", documentMetadata.getPassportLastUpdateDate().toString()
        );
    }
}
