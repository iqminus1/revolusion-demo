package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.enums.OrderStatus;
import uz.pdp.revolusiondemo.enums.PaymentStatus;
import uz.pdp.revolusiondemo.mapper.DefaultMapper;
import uz.pdp.revolusiondemo.model.Order;
import uz.pdp.revolusiondemo.model.Payment;
import uz.pdp.revolusiondemo.model.Room;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.OrderDto;
import uz.pdp.revolusiondemo.payload.in.OrderCrudDto;
import uz.pdp.revolusiondemo.repository.OrderRepository;
import uz.pdp.revolusiondemo.repository.PaymentRepository;
import uz.pdp.revolusiondemo.repository.RoomRepository;
import uz.pdp.revolusiondemo.service.OrderService;
import uz.pdp.revolusiondemo.utils.CommonUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DefaultMapper defaultMapper;
    private final OrderRepository orderRepository;
    private final CommonUtils commonUtils;
    private final RoomRepository roomRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public ApiResultDto<?> readAll(int page, int size) {
        List<OrderDto> result = orderRepository.findAll(PageRequest.of(page, size)).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> read(Integer id) {
        OrderDto result = toDTO(orderRepository.findById(id).orElseThrow());
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> readMyOrders() {
        List<OrderDto> result = orderRepository.findAllByUserId(commonUtils.getCurrentUser().getId()).stream().map(this::toDTO).toList();
        return ApiResultDto.success(result);
    }

    @Override
    public ApiResultDto<?> create(OrderCrudDto crudDto) {
        if (crudDto.getStartAt().isAfter(crudDto.getEndAt())) {
            return ApiResultDto.error("bad request");
        }
        if (crudDto.getStartAt().isBefore(LocalDate.now())) {
            return ApiResultDto.error("bad request");
        }

        boolean existsPending = orderRepository.existsByRoomIdAndStatusAndStartAtLessThanAndEndAtGreaterThan(crudDto.getRoomId(), OrderStatus.PENDING, crudDto.getEndAt(), crudDto.getStartAt());
        if (existsPending) {
            return ApiResultDto.error("you cannot book a room already booked by another");
        }
        boolean existsAccepted = orderRepository.existsByRoomIdAndStatusAndStartAtLessThanAndEndAtGreaterThan(crudDto.getRoomId(), OrderStatus.ACCEPTED, crudDto.getEndAt(), crudDto.getStartAt());
        if (existsAccepted) {
            return ApiResultDto.error("you cannot book a room already booked by another");
        }

        Order order = new Order();
        updateEntity(order, crudDto);
        orderRepository.save(order);
        return ApiResultDto.success(toDTO(order));
    }

    @Override
    public ApiResultDto<?> changeStatus(OrderStatus status, Integer id) {
        Order order = orderRepository.findById(id).orElseThrow();
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            return ApiResultDto.error("Bad request");
        }
        order.setStatus(status);
        orderRepository.save(order);
        if (status.equals(OrderStatus.ACCEPTED))
            openPayment(order);
        return ApiResultDto.success(toDTO(order));
    }

    @Async
    public void openPayment(Order order) {
        Room room = roomRepository.findById(order.getRoom().getId()).orElseThrow();
        long daysBetween = ChronoUnit.DAYS.between(order.getStartAt(), order.getEndAt());
        double amount = room.getPrice() * daysBetween;
        paymentRepository.save(new Payment(order, amount, PaymentStatus.NOT_PAID));
    }

    public void updateEntity(Order order, OrderCrudDto crudDto) {
        defaultMapper.updateEntity(order, crudDto);
        order.setRoom(roomRepository.findById(crudDto.getRoomId()).orElseThrow());
        order.setUser(commonUtils.getCurrentUser());
    }

    private OrderDto toDTO(Order order) {
        OrderDto dto = defaultMapper.toDTO(order);
        dto.setUserId(order.getUser().getId());
        dto.setRoomId(order.getRoom().getId());
        return dto;
    }
}
