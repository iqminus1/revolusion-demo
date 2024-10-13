package uz.pdp.revolusiondemo.service;

import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;
import uz.pdp.revolusiondemo.payload.in.RateCrudDto;

public interface RateService {
    ApiResultDto<?> readAll(int page, int size);

    ApiResultDto<?> read(Integer id);

    ApiResultDto<?> readByRoomId(Integer roomId);

    ApiResultDto<?> create(RateCrudDto crudDto);

    ApiResultDto<?> update(RateCrudDto crudDto, Integer id);

    void delete(Integer id);
}
