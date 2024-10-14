package uz.pdp.revolusiondemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.service.PaymentService;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payments/excel")
    public ResponseEntity<byte[]> exportUsersToExcel() {
        ByteArrayOutputStream outputStream = paymentService.exportPaymentsToExcel();

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=payments.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(paymentService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.read(id));
    }

    @GetMapping("/read-my-payments")
    public ResponseEntity<?> readMyOrders() {
        return ResponseEntity.ok(paymentService.myPayments());
    }

    @PutMapping("/pay-with-balance/{paymentId}")
    public ResponseEntity<?> payWithBalance(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.payWithBalance(paymentId));
    }

    @PutMapping("/user-paid/{paymentId}")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> userPaid(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.userPaid(paymentId));
    }

}
