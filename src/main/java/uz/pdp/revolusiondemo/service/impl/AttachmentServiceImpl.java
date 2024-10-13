package uz.pdp.revolusiondemo.service.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.AttachmentDTO;
import uz.pdp.revolusiondemo.repository.AttachmentRepository;
import uz.pdp.revolusiondemo.service.AttachmentService;

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
    public ApiResultDto<?> create(HttpServletRequest req) {
        try {
            List<AttachmentDTO> result = req.getParts().stream()
                    .map(part -> createOrUpdate(new Attachment(), part, false))
                    .toList();
            return ApiResultDto.success(result);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResultDto<?> update(HttpServletRequest req, Integer id) {
        try {
            Attachment attachment = attachmentRepository.getById(id);
            List<AttachmentDTO> result = req.getParts().stream()
                    .map(part -> createOrUpdate(attachment, part, true)).toList();
            return ApiResultDto.success(result);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        Attachment attachment = attachmentRepository.getById(id);
        attachmentRepository.delete(attachment);
    }

    private AttachmentDTO createOrUpdate(Attachment attachment, Part part, boolean isUpdate) {
        if (isUpdate) {
            Attachment copyAttachment = new Attachment(
                    attachment.getName(),
                    attachment.getOriginalName(),
                    attachment.getPath(),
                    attachment.getContentType(),
                    attachment.getSize()
            );
            copyAttachment.setDeleted(true);
            attachmentRepository.save(copyAttachment);

        }
        try {

            String contentType = part.getContentType();
            String originalName = part.getSubmittedFileName();
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
