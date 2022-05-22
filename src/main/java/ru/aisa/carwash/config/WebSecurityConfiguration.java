package ru.aisa.carwash.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.aisa.carwash.service.ClientService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    ClientService clientService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/").hasRole("USER")
                //Доступ разрешен всем пользователей
//                .antMatchers("/", "/resources/**").permitAll()
                //Все остальные страницы требуют аутентификации
                    .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/main", true)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                .and()
                    .csrf().disable()
                    .exceptionHandling();

    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clientService).passwordEncoder(bCryptPasswordEncoder());
    }
}
