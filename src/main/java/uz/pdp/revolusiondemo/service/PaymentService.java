package uz.pdp.revolusiondemo.service;

import uz.pdp.revolusiondemo.payload.ApiResultDto;

import java.io.ByteArrayOutputStream;

public interface PaymentService {

    ApiResultDto<?> readAll(int page, int size);

    ApiResultDto<?> read(Integer id);

    ApiResultDto<?> myPayments();

    ApiResultDto<?> payWithBalance(Integer id);

    ApiResultDto<?> userPaid(Integer id);
    ByteArrayOutputStream exportPaymentsToExcel();
}
