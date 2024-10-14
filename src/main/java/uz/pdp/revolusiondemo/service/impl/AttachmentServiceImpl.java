package uz.pdp.revolusiondemo.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.model.User;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.AttachmentDto;
import uz.pdp.revolusiondemo.repository.AttachmentRepository;
import uz.pdp.revolusiondemo.service.AttachmentService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final DefaultMapper defaultMapper;

    @Value("${app.attachment.base-path}")
    private String basePath;

    @Override
    public void read(HttpServletResponse resp, Integer id) {
        Attachment attachment = attachmentRepository.getById(id);
        try {
            Path path = Path.of(attachment.getPath());
            Files.copy(path, resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResultDto<?> create(MultipartFile part) {
        return ApiResultDto.success(createOrUpdate(new Attachment(), part));
    }

    @Override
    public ApiResultDto<?> update(MultipartFile part, Integer id) {
        Attachment attachment = attachmentRepository.getById(id);
        return ApiResultDto.success(createOrUpdate(attachment, part));

    }

    @Override
    public void delete(Integer id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public ByteArrayOutputStream exportAttachmentsToExcel() {
        List<Attachment> attachments = attachmentRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attachments");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Name", "Content type", "Original name", "Path", "Size"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < attachments.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Attachment attachment = attachments.get(i);
            row.createCell(0).setCellValue(attachment.getId());
            row.createCell(1).setCellValue(attachment.getName());
            row.createCell(2).setCellValue(attachment.getContentType());
            row.createCell(3).setCellValue(attachment.getOriginalName());
            row.createCell(4).setCellValue(attachment.getPath());
            row.createCell(5).setCellValue(attachment.getSize());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
            return outputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private AttachmentDto createOrUpdate(Attachment attachment, MultipartFile part) {
        try {
            String contentType = part.getContentType();
            String originalName = part.getOriginalFilename();
            long size = part.getSize();

            String[] split = originalName.split("\\.");
            String s = split[split.length - 1];
            UUID uuid = UUID.randomUUID();

            String name = uuid + "." + s;

            String pathString = basePath + "/" + name;

            Files.copy(part.getInputStream(), Path.of(pathString));

            attachment.setName(name);
            attachment.setSize(size);
            attachment.setPath(pathString);
            attachment.setContentType(contentType);
            attachment.setOriginalName(originalName);

            attachmentRepository.save(attachment);

            return defaultMapper.toDTO(attachment);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
