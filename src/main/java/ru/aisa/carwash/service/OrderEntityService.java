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

    public List<OrderEntity> findAll() {
        return orderEntityRepository.findAll();
    }

    public boolean saveOrder(OrderEntity orderEntity,Client client) {
//        нахождение конца интервала времени
        LocalDateTime endTime = orderEntity.getStartTime().plusMinutes(orderEntity.getWashOptions().stream().mapToInt(x -> x.getTime()).sum());

//        проверка на пересечение по времени
        String orderEntityFromDB = orderEntityRepository.findByStartTimeAndEndTime(orderEntity.getStartTime(), endTime);

        if (orderEntityFromDB != null) {
            return false;
        }

//        бронирование времени с учетом всех данных
        orderEntityRepository.insertOrder(orderEntity.getStartTime(), endTime, client.getEmail(),
                client.getUsername(), orderEntity.getWashOptions().stream().mapToInt(x -> x.getCost()).sum(),
                client.getBrandCar(), client.getNumberCar());

//        нахождение ид записи для связывания с услугами
        Long idOrder = orderEntityRepository.findIdByStartTimeAndEndTime(orderEntity.getStartTime(), endTime);

//        связывание записи с услугами, в дальнейшем может пригодиться для отображения услуг, которые выполняют определенные рабочие
        orderEntity.getWashOptions().stream().forEach(x -> orderEntityRepository.bindOrderAndWashOption(idOrder, x.getId()));

        return true;
    }
}
