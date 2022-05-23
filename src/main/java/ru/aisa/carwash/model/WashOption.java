package ru.aisa.carwash.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    public WashOption() {
    }
}
