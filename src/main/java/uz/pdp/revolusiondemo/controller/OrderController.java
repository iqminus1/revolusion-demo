package uz.pdp.revolusiondemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.enums.OrderStatus;
import uz.pdp.revolusiondemo.payload.in.OrderCrudDto;
import uz.pdp.revolusiondemo.service.OrderService;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/orders/excel")
    public ResponseEntity<byte[]> exportUsersToExcel() {
        ByteArrayOutputStream outputStream = orderService.exportOrdersToExcel();

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(orderService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.read(id));
    }

    @GetMapping("/read-my-orders")
    public ResponseEntity<?> readMyOrders() {
        return ResponseEntity.ok(orderService.readMyOrders());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderCrudDto crudDto) {
        return ResponseEntity.ok(orderService.create(crudDto));
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@RequestParam OrderStatus orderStatus, @PathVariable Integer id) {
        return ResponseEntity.ok((orderService.changeStatus(orderStatus, id)));
    }
}
