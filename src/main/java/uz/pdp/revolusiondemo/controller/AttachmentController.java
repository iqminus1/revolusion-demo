package uz.pdp.revolusiondemo.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.revolusiondemo.service.AttachmentService;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/attachments/excel")
    public ResponseEntity<byte[]> exportUsersToExcel() {
        ByteArrayOutputStream outputStream = attachmentService.exportAttachmentsToExcel();

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=attachments.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public void read(@PathVariable Integer id, HttpServletResponse resp) {
        attachmentService.read(resp, id);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.status(200).body(attachmentService.create(multipartFile));
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> update(@RequestParam("file") MultipartFile multipartFile, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(attachmentService.update(multipartFile, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public void delete(@PathVariable Integer id) {
        attachmentService.delete(id);
    }
}