package ua.viktor.myspringbootapp.util.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.viktor.myspringbootapp.services.PersonDetailsService;
/**
 * @author Diakonov Viktor
 */
//@EnableWebSecurity
//@AllArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    private final PersonDetailsService personDetailsService;
//
//    // configure spring security and authorization
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable() // защита от меж сайтовой подделки запросов
//                .authorizeRequests()
//                .antMatchers("/mobileshop/admin_page").hasRole("ADMIN") //для этой страницы нужна роль Админ
//                //.antMatchers("/mobileshop/{id}/neworder").hasRole("USER") //для этой страницы нужна роль Юзер
//                .antMatchers("/**").permitAll() //эти страницы вход без пароля
//                .anyRequest().hasAnyRole("USER", "ADMIN") // для юзера и админа вход на остальные страницы открыт
//                .and()
//                .formLogin().loginPage("/mobileshop/auth/login") // моя форма регистрации
//                .loginProcessingUrl("/process_login") // адрес, где обрабатываются данные с формы
//                .defaultSuccessUrl("/mobileshop/", true) // страница куда перенаправляет потом
//                .failureUrl("/mobileshop/auth/login?error") //адрес, куда перенаправит, если не верный логин или пароль
//                .and()
//                .logout()
//                .logoutUrl("/logout") // страница разлогинивания (удаляются cookie и сессия)
//                .logoutSuccessUrl("/auth/login"); // страница перехода после разлогинивания
//    }
//
//    // configure authentication
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
//    }
//
//    // configure BCrypt coding
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // защита от меж сайтовой подделки запросов
                .authorizeRequests()
                .antMatchers("/mobileshop/admin_page").hasRole("ADMIN") // для этой страницы нужна роль Админ
                //.antMatchers("/mobileshop/{id}/neworder").hasRole("USER") // для этой страницы нужна роль Юзер
                .antMatchers("/**").permitAll() // эти страницы вход без пароля
                .anyRequest().hasAnyRole("USER", "ADMIN") // для юзера и админа вход на остальные страницы открыт
                .and()
                .formLogin().loginPage("/mobileshop/auth/login") // моя форма регистрации
                .loginProcessingUrl("/process_login") // адрес, где обрабатываются данные с формы
                .defaultSuccessUrl("/mobileshop/", true) // страница куда перенаправляет потом
                .failureUrl("/mobileshop/auth/login?error") // адрес, куда перенаправит, если неверный логин или пароль
                .and()
                .logout()
                .logoutUrl("/logout") // страница разлогинивания (удаляются cookie и сессия)
                .logoutSuccessUrl("/auth/login"); // страница перехода после разлогинивания

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }

    // configure BCrypt coding
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}