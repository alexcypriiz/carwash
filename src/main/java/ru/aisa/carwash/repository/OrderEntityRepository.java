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
    @Query(value = "SELECT * FROM order_entity WHERE end_time > now() ORDER BY start_time", nativeQuery = true)
    List<OrderEntity> findAllOrderByStartTime();

    @Query(value = "SELECT username FROM order_entity WHERE start_time < ?1 " +
            "AND end_time > ?1 " +
            "OR end_time > ?2 AND start_time <?2 " +
            "OR start_time = ?1 AND end_time = ?2 " +
            "LIMIT 1", nativeQuery = true)
    String findByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "WITH next AS (SELECT " +
            "LEAD (start_time,1,'3000-12-12 05:00:00')OVER (ORDER BY start_time) AS start_, " +
            "LEAD(end_time,1,'3000-12-12 05:00:00')OVER (ORDER BY end_time) AS end_, end_time AS next_start FROM order_entity) " +
            "SELECT next_start FROM next " +
            "WHERE next_start > now() " +
            "AND next_start < start_ " +
            "AND next_start + interval '1 MINUTE' * ?1 < end_ " +
            "ORDER BY start_ LIMIT 1", nativeQuery = true)
    LocalDateTime findNearestByStartTime(Integer fullTime);

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
