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
import uz.pdp.revolusiondemo.model.Rate;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.RateDto;
import uz.pdp.revolusiondemo.payload.in.RateCrudDto;
import uz.pdp.revolusiondemo.repository.RateRepository;
import uz.pdp.revolusiondemo.repository.RoomRepository;
import uz.pdp.revolusiondemo.service.RateService;
import uz.pdp.revolusiondemo.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final DefaultMapper defaultMapper;
    private final CommonUtils commonUtils;
    private final RoomRepository roomRepository;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        List<RateDto> result = rateRepository.findAll(PageRequest.of(page, size)).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        RateDto result = toDTO(rateRepository.findById(id).orElseThrow());
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> readByRoomId(Integer roomId) {
        Rate rate = rateRepository.findByUserIdAndRoomId(commonUtils.getCurrentUser().getId(), roomId).orElseThrow();
        return ApiResultDto.success(toDTO(rate));
    }

    @Override
    public ApiResultDto<?> roomRate(Integer roomId) {
        return ApiResultDto.success(rateRepository.getRateByRoomId(roomId));
    }

    @Override
    public ApiResultDto<?> create(RateCrudDto crudDto) {
        Rate rate = new Rate();
        updateEntity(rate, crudDto);
        rateRepository.save(rate);
        return ApiResultDto.success(toDTO(rate));
    }

    @Override
    public ApiResultDto<?> update(RateCrudDto crudDto, Integer roomId) {
        Rate rate = rateRepository.findByUserIdAndRoomId(commonUtils.getCurrentUser().getId(), roomId).orElseThrow();
        updateEntity(rate, crudDto);
        rateRepository.save(rate);
        return ApiResultDto.success(toDTO(rate));
    }

    @Override
    public void delete(Integer roomId) {
        Rate rate = rateRepository.findByUserIdAndRoomId(commonUtils.getCurrentUser().getId(), roomId).orElseThrow();
        rateRepository.delete(rate);
    }

    @Override
    public ByteArrayOutputStream exportRatesToExcel() {
        List<Rate> rates = rateRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rates");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "User id", "Room id", "rate", "description"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < rates.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Rate rate = rates.get(i);
            row.createCell(0).setCellValue(rate.getId());
            row.createCell(1).setCellValue(rate.getUser().getId());
            row.createCell(2).setCellValue(rate.getRoom().getId());
            row.createCell(3).setCellValue(rate.getNumber());
            row.createCell(4).setCellValue(rate.getDescription());
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

    private void updateEntity(Rate rate, RateCrudDto crudDto) {
        defaultMapper.updateEntity(rate, crudDto);
        rate.setUser(commonUtils.getCurrentUser());
        rate.setRoom(roomRepository.findById(crudDto.getRoomId()).orElseThrow());
    }

    private RateDto toDTO(Rate rate) {
        RateDto result = defaultMapper.toDTO(rate);
        result.setRoomId(rate.getRoom().getId());
        result.setUserId(rate.getUser().getId());
        return result;
    }
}
