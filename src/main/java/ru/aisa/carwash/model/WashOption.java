package ru.aisa.carwash.model;

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
    private Long id;

    @NotEmpty(message = "Название не может быть пустым")
    private String name;

    @NotNull(message = "Укажите стоимость в рублях")
    private Integer cost;

    @NotNull(message = "Укажите затрачиваемое время в минутах")
    private Integer time;

    @Transient
    @ManyToMany(mappedBy = "wash_options")
    private Set<OrderEntity> orders;

    public WashOption() {
    }
}
