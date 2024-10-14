package uz.pdp.revolusiondemo.service;

import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;

import java.io.ByteArrayOutputStream;

public interface HotelService {
    ApiResultDto<?> readAll(int page, int size);

    ApiResultDto<?> read(Integer id);

    ApiResultDto<?> create(HotelCrudDto crudDto);

    ApiResultDto<?> update(HotelCrudDto crudDto, Integer id);

    void delete(Integer id);

    ByteArrayOutputStream exportHotelsToExcel();
}
