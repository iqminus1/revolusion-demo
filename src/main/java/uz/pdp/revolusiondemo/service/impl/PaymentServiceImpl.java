package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.enums.PaymentStatus;
import uz.pdp.revolusiondemo.model.Payment;
import uz.pdp.revolusiondemo.model.User;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.PaymentDto;
import uz.pdp.revolusiondemo.repository.PaymentRepository;
import uz.pdp.revolusiondemo.repository.UserRepository;
import uz.pdp.revolusiondemo.service.PaymentService;
import uz.pdp.revolusiondemo.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CommonUtils commonUtils;
    private final UserRepository userRepository;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        List<PaymentDto> result = paymentRepository.findAll(PageRequest.of(page, size)).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        PaymentDto result = toDTO(paymentRepository.findById(id).orElseThrow());
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> myPayments() {
        List<PaymentDto> result = paymentRepository.findAllByOrderUserId(commonUtils.getCurrentUser().getId()).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> payWithBalance(Integer id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        User user = commonUtils.getCurrentUser();
        if (user.getBalance() < payment.getAmount()) {
            return ApiResultDto.error("there is not enough money in the balance");
        }

        user.setBalance(user.getBalance() - payment.getAmount());
        userRepository.save(user);
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
        return ApiResultDto.success(toDTO(payment));
    }

    @Override
    public ApiResultDto<?> userPaid(Integer id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
        return ApiResultDto.success(toDTO(payment));
    }

    @Override
    public ByteArrayOutputStream exportPaymentsToExcel() {
        List<Payment> payments = paymentRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payments");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Order id", "Amount", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < payments.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Payment payment = payments.get(i);
            row.createCell(0).setCellValue(payment.getId());
            row.createCell(1).setCellValue(payment.getOrder().getId());
            row.createCell(2).setCellValue(payment.getAmount());
            row.createCell(3).setCellValue(payment.getStatus().name());
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

    private PaymentDto toDTO(Payment payment) {
        return new PaymentDto(payment.getId(), payment.getOrder().getId(), payment.getAmount(), payment.getStatus());
    }
}
