package ru.aisa.carwash.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "status")
@Data
public class Role implements GrantedAuthority {
    @Id
    @ApiModelProperty("Уникальный id роли")
    private Long id;

    @ApiModelProperty("Название роли")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    @ApiModelProperty("Список клиентов")
    private Set<Client> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return getName();
    }
}
