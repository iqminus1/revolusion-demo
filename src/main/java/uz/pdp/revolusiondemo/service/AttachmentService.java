package uz.pdp.revolusiondemo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.revolusiondemo.payload.ApiResultDto;

import java.io.ByteArrayOutputStream;

public interface AttachmentService {
    void read(HttpServletResponse resp, Integer id);

    ApiResultDto<?> create(MultipartFile part);

    ApiResultDto<?> update(MultipartFile part, Integer id);

    void delete(Integer id);

    ByteArrayOutputStream exportAttachmentsToExcel();
}