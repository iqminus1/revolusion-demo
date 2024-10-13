package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Hotel;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.HotelDto;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;
import uz.pdp.revolusiondemo.repository.HotelRepository;
import uz.pdp.revolusiondemo.service.HotelService;

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
}
