package uz.pdp.revolusiondemo.service;

import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.in.RoomCrudDto;

public interface RoomService {
    ApiResultDto<?> readAll(int page, int size);

    ApiResultDto<?> read(Integer id);

    ApiResultDto<?> create(RoomCrudDto crudDto);

    ApiResultDto<?> update(RoomCrudDto crudDto, Integer id);

    void delete(Integer id);
}
