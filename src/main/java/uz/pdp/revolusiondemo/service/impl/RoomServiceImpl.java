package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.model.Room;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.RoomDto;
import uz.pdp.revolusiondemo.payload.in.RoomCrudDto;
import uz.pdp.revolusiondemo.repository.AttachmentRepository;
import uz.pdp.revolusiondemo.repository.HotelRepository;
import uz.pdp.revolusiondemo.repository.RoomRepository;
import uz.pdp.revolusiondemo.service.RoomService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final AttachmentRepository attachmentRepository;
    private final DefaultMapper defaultMapper;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        List<RoomDto> result = roomRepository.findAll(PageRequest.of(page, size)).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        RoomDto result = toDTO(roomRepository.findById(id).orElseThrow());
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> create(RoomCrudDto crudDto) {
        Room room = new Room();
        updateEntity(room, crudDto);
        roomRepository.save(room);
        return ApiResultDto.success(defaultMapper.toDTO(room));
    }

    @Override
    public ApiResultDto<?> update(RoomCrudDto crudDto, Integer id) {
        Room room = roomRepository.findById(id).orElseThrow();
        updateEntity(room, crudDto);
        roomRepository.save(room);
        return ApiResultDto.success(defaultMapper.toDTO(room));
    }

    @Override
    public void delete(Integer id) {
        roomRepository.deleteById(id);
    }

    @Override
    public ByteArrayOutputStream exportRoomsToExcel() {
        List<Room> rooms = roomRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rooms");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Hotel id", "Attachment ids", "Type", "Number", "Price"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < rooms.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Room room = rooms.get(i);
            row.createCell(0).setCellValue(room.getId());
            row.createCell(1).setCellValue(room.getHotel().getId());
            row.createCell(2).setCellValue(room.getAttachments().stream().map(Attachment::getId).toList().toString());
            row.createCell(3).setCellValue(room.getType().name());
            row.createCell(4).setCellValue(room.getNumber());
            row.createCell(5).setCellValue(room.getPrice());
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

    private void updateEntity(Room room, RoomCrudDto crudDto) {
        defaultMapper.updateEntity(room, crudDto);
        room.setHotel(hotelRepository.findById(crudDto.getHotelId()).orElseThrow());
        if (crudDto.getAttachmentIds() != null)
            room.setAttachments(attachmentRepository.findAllById(crudDto.getAttachmentIds()));
    }

    private RoomDto toDTO(Room room) {
        RoomDto result = defaultMapper.toDTO(room);
        result.setAttachmentIds(room.getAttachments().stream().map(Attachment::getId).toList());
        result.setHotelId(room.getHotel().getId());
        return result;
    }
}
