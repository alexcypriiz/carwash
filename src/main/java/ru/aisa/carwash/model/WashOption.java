package ru.aisa.carwash.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "wash_option")
@Data
public class WashOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Уникальный id услуги")
    private Long id;

    @NotEmpty(message = "Название не может быть пустым")
    @ApiModelProperty("Название услуги")
    private String name;

    @NotNull(message = "Укажите стоимость в рублях")
    @ApiModelProperty("Цена услуги(в рублях)")
    private Integer cost;

    @NotNull(message = "Укажите затрачиваемое время в минутах")
    @ApiModelProperty("Время выполнения услуги(в минутах)")
    private Integer time;

    @Transient
    @ManyToMany(mappedBy = "wash_options")
    private Set<OrderEntity> orders;

    public WashOption() {
    }
}
