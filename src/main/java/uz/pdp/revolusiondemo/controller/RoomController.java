package uz.pdp.revolusiondemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.payload.in.RateCrudDto;
import uz.pdp.revolusiondemo.payload.in.RoomCrudDto;
import uz.pdp.revolusiondemo.service.RoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(roomService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.read(id));
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> create(@RequestBody @Valid RoomCrudDto crudDto) {
        return ResponseEntity.ok(roomService.create(crudDto));
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> update(@RequestBody @Valid RoomCrudDto crudDto, @PathVariable Integer id) {
        return ResponseEntity.ok(roomService.update(crudDto, id));
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public void delete(@PathVariable Integer id) {
        roomService.delete(id);
    }
}
