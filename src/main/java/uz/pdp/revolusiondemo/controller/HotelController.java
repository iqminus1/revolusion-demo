package uz.pdp.revolusiondemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;
import uz.pdp.revolusiondemo.service.HotelService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(hotelService.readAll(page, size));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelService.read(id));
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public  ResponseEntity<?> create(@RequestBody @Valid HotelCrudDto crudDto) {
        return ResponseEntity.ok(hotelService.create(crudDto));
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public ResponseEntity<?> update(@RequestBody @Valid HotelCrudDto crudDto, @PathVariable Integer id) {
        return ResponseEntity.ok(hotelService.update(crudDto, id));
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority(T(uz.pdp.revolusiondemo.enums.RoleEnum).ADMIN.name())")
    public void delete(@PathVariable Integer id) {
        hotelService.delete(id);
    }
}
