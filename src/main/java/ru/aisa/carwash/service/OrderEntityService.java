package ru.aisa.carwash.service;

import org.springframework.stereotype.Service;
import ru.aisa.carwash.model.Client;
import ru.aisa.carwash.model.OrderEntity;
import ru.aisa.carwash.repository.OrderEntityRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderEntityService {

    final
    OrderEntityRepository orderEntityRepository;

    public OrderEntityService(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }

    public List<OrderEntity> findAllOrderByStartTime() {
        return orderEntityRepository.findAllOrderByStartTime();
    }

    public boolean saveOrder(OrderEntity orderEntity, Client client) {
        //    Затрачиваемое время с учетом всех услг
        LocalDateTime startTime;
        LocalDateTime endTime;
        Integer fullTime = orderEntity.getWashOptions().stream().mapToInt(x -> x.getTime()).sum();
        String orderEntityFromDB;

//        если клиент выбрал время
        if (orderEntity.getStartTime() != null) {
//        нахождение конца интервала времени
            startTime = orderEntity.getStartTime();
            endTime = orderEntity.getStartTime().plusMinutes(fullTime);

//        проверка на пересечение по времени
            orderEntityFromDB = orderEntityRepository.findByStartTimeAndEndTime(orderEntity.getStartTime(), endTime);
            if (orderEntityFromDB != null) {
                return false;
            }
        } else {
            String checkOrderEntityFromDB = orderEntityRepository.findByStartTimeAndEndTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(fullTime));
//            если сейчас не свободно время
            if (checkOrderEntityFromDB != null) {
                LocalDateTime freeStartTime = orderEntityRepository.findNearestByStartTime(fullTime);
                startTime = freeStartTime;
                endTime = freeStartTime.plusMinutes(fullTime);
            } else {
                startTime = LocalDateTime.now();
                endTime = LocalDateTime.now().plusMinutes(fullTime);
//                orderEntityFromDB = checkOrderEntityFromDB;
            }
        }

//        бронирование времени с учетом всех данных
        orderEntityRepository.insertOrder(startTime, endTime, client.getEmail().trim(),
                client.getUsername().trim(), orderEntity.getWashOptions().stream().mapToInt(x -> x.getCost()).sum(),
                client.getBrandCar().trim(), client.getNumberCar().trim());

//        нахождение ид записи для связывания с услугами
        Long idOrder = orderEntityRepository.findIdByStartTimeAndEndTime(startTime, endTime);

//        связывание записи с услугами, в дальнейшем может пригодиться для отображения услуг, которые выполняют определенные рабочие
        orderEntity.getWashOptions().stream().forEach(x -> orderEntityRepository.bindOrderAndWashOption(idOrder, x.getId()));

        return true;
    }

    public LocalDateTime findDistinctStartTimeByUsername(String usernameOrder) {
        return orderEntityRepository.findDistinctStartTimeByUsername(usernameOrder);
    }
}
