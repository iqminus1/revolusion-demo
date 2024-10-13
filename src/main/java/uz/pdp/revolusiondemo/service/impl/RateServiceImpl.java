package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final DefaultMapper defaultMapper;
    private final CommonUtils commonUtils;
    private final RoomRepository roomRepository;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        rateRepository.findAll(PageRequest.of(page, size)).stream().map(this::toDTO).toList();
        return null;
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        return null;
    }

    @Override
    public ApiResultDto<?> readByRoomId(Integer roomId) {
        Rate rate = rateRepository.findByUserIdAndRoomId(commonUtils.getCurrentUser().getId(), roomId).orElseThrow();
        return ApiResultDto.success(toDTO(rate));
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
