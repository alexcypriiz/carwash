package ru.aisa.carwash.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "order_entity")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Уникальный id записи")
    private Long id;

    @ApiModelProperty("Имя клиента записи")
    private String username;

    @ApiModelProperty("Электронная почта клиента записи")
    private String email;

    @Column(columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @ApiModelProperty("Время начала записи")
    private LocalDateTime startTime;

    @Column(columnDefinition = "TIMESTAMP")
    @ApiModelProperty("Время конца записи")
    private LocalDateTime endTime;

    @ApiModelProperty("Стоимость оказания услуги(в рублях)")
    private Integer costCarWash;

    @ApiModelProperty("Номер машины клиента записи")
    private String numberCar;

    @ApiModelProperty("Марка машины клиента записи")
    private String brandCar;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty("Выбранные клиентом услуги")
    private Set<WashOption> washOptions;

    public OrderEntity() {
    }

}
