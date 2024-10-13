package uz.pdp.revolusiondemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.payload.in.RateCrudDto;
import uz.pdp.revolusiondemo.service.RateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rate")
public class RateController {
    private final RateService rateService;

    @GetMapping("/read-all")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(rateService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(rateService.read(id));
    }

    @GetMapping("/read-by-room-id/{roomId}")
    public ResponseEntity<?> readByRoomId(@PathVariable Integer roomId) {
        return ResponseEntity.ok(rateService.readByRoomId(roomId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid RateCrudDto crudDto) {
        return ResponseEntity.ok(rateService.create(crudDto));
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<?> update(@RequestBody @Valid RateCrudDto crudDto, @PathVariable Integer roomId) {
        return ResponseEntity.ok(rateService.update(crudDto, roomId));
    }

    @DeleteMapping("/delete-by-room-id/{roomId}")
    public void delete(@PathVariable Integer roomId) {
        rateService.delete(roomId);
    }
}
