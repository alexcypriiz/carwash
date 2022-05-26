package ru.aisa.carwash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.aisa.carwash.model.OrderEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM order_entity ORDER BY start_time", nativeQuery = true)
    List<OrderEntity> findAllOrderByStartTime();

    @Query("SELECT username FROM OrderEntity WHERE startTime < ?1 AND endTime > ?1 " +
            "OR endTime > ?2 AND startTime <?2 " +
            "OR startTime = ?1 AND endTime = ?2")
    String findByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO order_entity(start_time, end_time, email, username, cost_car_wash, brand_car, number_car)" +
            " VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    void insertOrder(LocalDateTime startTime, LocalDateTime endTime, String email, String username, Integer costCarWash, String brandCar, String numberCar);

    @Query("SELECT id FROM OrderEntity WHERE startTime = ?1 AND endTime = ?2")
    Long findIdByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO public.order_entity_wash_options(order_entity_id, wash_options_id)" +
            " VALUES (?1, ?2)", nativeQuery = true)
    void bindOrderAndWashOption(Long idOrder, Long idWashOption);

    @Query(value = "SELECT start_time FROM order_entity WHERE username = ?1 AND now() < start_time " +
            "ORDER BY start_time LIMIT 1", nativeQuery = true)
    LocalDateTime findDistinctStartTimeByUsername(String username);
}
