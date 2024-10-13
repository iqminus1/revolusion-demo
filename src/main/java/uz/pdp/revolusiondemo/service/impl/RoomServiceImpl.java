package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
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

    private void updateEntity(Room room, RoomCrudDto crudDto) {
        defaultMapper.updateEntity(room, crudDto);
        room.setHotel(hotelRepository.findById(crudDto.getHotelId()).orElseThrow());
        room.setAttachments(attachmentRepository.findAllById(crudDto.getAttachmentIds()));
    }

    private RoomDto toDTO(Room room) {
        RoomDto result = defaultMapper.toDTO(room);
        result.setAttachmentIds(room.getAttachments().stream().map(Attachment::getId).toList());
        result.setHotelId(room.getHotel().getId());
        return result;
    }
}
