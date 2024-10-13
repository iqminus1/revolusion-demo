package uz.pdp.revolusiondemo.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.AttachmentDto;
import uz.pdp.revolusiondemo.repository.AttachmentRepository;
import uz.pdp.revolusiondemo.service.AttachmentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
