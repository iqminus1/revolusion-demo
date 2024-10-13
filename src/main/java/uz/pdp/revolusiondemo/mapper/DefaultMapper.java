package uz.pdp.revolusiondemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.model.Hotel;
import uz.pdp.revolusiondemo.model.Rate;
import uz.pdp.revolusiondemo.model.Room;
import uz.pdp.revolusiondemo.payload.AttachmentDto;
import uz.pdp.revolusiondemo.payload.HotelDto;
import uz.pdp.revolusiondemo.payload.RateDto;
import uz.pdp.revolusiondemo.payload.RoomDto;
import uz.pdp.revolusiondemo.payload.in.HotelCrudDto;
import uz.pdp.revolusiondemo.payload.in.RateCrudDto;
import uz.pdp.revolusiondemo.payload.in.RoomCrudDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DefaultMapper {
    AttachmentDto toDTO(Attachment attachment);

    HotelDto toDTO(Hotel hotel);
    void updateEntity(@MappingTarget Hotel hotel, HotelCrudDto crudDto);

    RoomDto toDTO(Room room);
    void updateEntity(@MappingTarget Room room, RoomCrudDto crudDto);

    RateDto toDTO(Rate rate);
    void updateEntity(@MappingTarget Rate room, RateCrudDto crudDto);
}