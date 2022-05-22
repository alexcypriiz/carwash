package ru.aisa.carwash.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "client")
@Data
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Укажите Ваше имя")
    @Size(min = 2, message = "Минимальное количество букв в имени 2")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 5, message = "Минимальное количестве символов в пароле 5")
    private String password;

    @Transient
    @NotEmpty(message = "Повторите пароль")
    private String passwordConfirm;

    @Email(message = "Электронная почта должна быть действительной")
    private String email;

    @NotEmpty(message = "Укажите номер машины")
    private String numberCar;

    @NotEmpty(message = "Укажите марку машины")
    private String brandCar;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Client() {
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }


}
