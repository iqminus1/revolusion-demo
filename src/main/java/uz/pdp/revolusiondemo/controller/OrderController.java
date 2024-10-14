package uz.pdp.revolusiondemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.enums.OrderStatus;
import uz.pdp.revolusiondemo.payload.in.OrderCrudDto;
import uz.pdp.revolusiondemo.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/read-all")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(orderService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
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
