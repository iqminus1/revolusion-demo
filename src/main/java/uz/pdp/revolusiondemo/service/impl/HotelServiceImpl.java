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
import uz.pdp.revolusiondemo.model.Hotel;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.HotelDto;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;
import uz.pdp.revolusiondemo.repository.HotelRepository;
import uz.pdp.revolusiondemo.service.HotelService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final DefaultMapper defaultMapper;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        List<HotelDto> result = hotelRepository.findAll(PageRequest.of(page, size)).stream().map(defaultMapper::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        return ApiResultDto.success(defaultMapper.toDTO(hotelRepository.findById(id).orElseThrow()));
    }

    @Override
    public ApiResultDto<?> create(HotelCrudDto crudDto) {
        Hotel hotel = new Hotel();
        defaultMapper.updateEntity(hotel, crudDto);
        hotelRepository.save(hotel);
        return ApiResultDto.success(defaultMapper.toDTO(hotel));
    }

    @Override
    public ApiResultDto<?> update(HotelCrudDto crudDto, Integer id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        defaultMapper.updateEntity(hotel, crudDto);
        hotelRepository.save(hotel);
        return ApiResultDto.success(defaultMapper.toDTO(hotel));
    }

    @Override
    public void delete(Integer id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public ByteArrayOutputStream exportHotelsToExcel() {
        List<Hotel> hotels = hotelRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hotels");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Name", "Phone number", "Latitude", "Longitude"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < hotels.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Hotel hotel = hotels.get(i);
            row.createCell(0).setCellValue(hotel.getId());
            row.createCell(1).setCellValue(hotel.getName());
            row.createCell(2).setCellValue(hotel.getPhoneNumber());
            row.createCell(3).setCellValue(hotel.getLatitude());
            row.createCell(4).setCellValue(hotel.getLongitude());
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
}
