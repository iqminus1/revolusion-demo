package uz.pdp.revolusiondemo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.revolusiondemo.payload.ApiResultDto;

public interface AttachmentService {
    void read(HttpServletResponse resp,Integer id);
    ApiResultDto<?> create(HttpServletRequest req);
    ApiResultDto<?> update(HttpServletRequest req,Integer id);
    void delete(Integer id);
}