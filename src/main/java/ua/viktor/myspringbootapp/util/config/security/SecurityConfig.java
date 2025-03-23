package ua.viktor.myspringbootapp.util.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.viktor.myspringbootapp.services.PersonDetailsService;
/**
 * @author Diakonov Viktor
 */
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable() // защита от меж сайтовой подделки запросов
//                .authorizeRequests()
//                .antMatchers("/mobileshop/admin_page").hasRole("ADMIN") // для этой страницы нужна роль Админ
//                .antMatchers("/**").permitAll() // эти страницы вход без пароля
//                .anyRequest().hasAnyRole("USER", "ADMIN") // для юзера и админа вход на остальные страницы открыт
//                .and()
//                .formLogin().loginPage("/mobileshop/auth/login") // моя форма регистрации
//                .loginProcessingUrl("/process_login") // адрес, где обрабатываются данные с формы
//                .defaultSuccessUrl("/mobileshop/my-orders", true) // страница куда перенаправляет потом
//                .failureUrl("/mobileshop/auth/login?error") // адрес, куда перенаправит, если неверный логин или пароль
//                .and()
//                .logout()
//                .logoutUrl("/mobileshop/logout") // страница разлогинивания (удаляются cookie и сессия)
//                .logoutSuccessUrl("/mobileshop/auth/login"); // страница перехода после разлогинивания
//
//        return http.build();
//    }
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 1. Отключаем CSRF (если используешь формы, лучше удалить эту строку)
//                .csrf().disable()
//
//                // 2. Настраиваем авторизацию
//                .authorizeRequests()
//                .antMatchers(
//                        "/mobileshop/admin_page/**",
//                        "/mobileshop/orders",
//                        "/mobileshop/{orderId}/order",
//                        ".antMatchers(\"/mobileshop/orders/p\").hasRole(\"ADMIN\")",
//                        "/mobileshop/new_phone",
//                        "/mobileshop/{phoneId}/edit_phone"
//                ).hasRole("ADMIN") // Доступ только для ADMIN
//                .antMatchers("/mobileshop/my-orders").authenticated() // Доступ только после аутентификации
//                .antMatchers("/**").permitAll() // Все остальные страницы доступны всем
//                //.anyRequest().hasAnyRole("USER", "ADMIN") // Для USER и ADMIN остальные страницы
//
//                // 3. Настраиваем форму входа
//                .and()
//                .formLogin()
//                .loginPage("/mobileshop/auth/login") // Кастомная страница логина
//                .loginProcessingUrl("/process_login") // URL для обработки логина
//                .failureUrl("/mobileshop/auth/login?error") // Ошибка входа
//                .successHandler((request, response, authentication) -> {
//                    response.sendRedirect(authentication.getAuthorities().stream()
//                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
//                            ? "/mobileshop/admin_page"
//                            : "/mobileshop/my-orders");
//                }) // Перенаправление в зависимости от роли
//
//                // 4. Настраиваем выход из системы
//                .and()
//                .logout()
//                .logoutUrl("/mobileshop/logout") // URL выхода
//                .logoutSuccessUrl("/mobileshop/auth/login"); // После выхода
//
//        return http.build();
//    }
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Отключаем CSRF (если используешь формы, лучше удалить эту строку)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Настраиваем авторизацию
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/mobileshop/admin_page/**"),
                                new AntPathRequestMatcher("/mobileshop/orders"),
                                new AntPathRequestMatcher("/mobileshop/{orderId}/order"),
                                new AntPathRequestMatcher("/mobileshop/orders/p"),
                                new AntPathRequestMatcher("/mobileshop/new_phone"),
                                new AntPathRequestMatcher("/mobileshop/{phoneId}/edit_phone")
                        ).hasRole("ADMIN") // Доступ только для ADMIN

                        .requestMatchers(new AntPathRequestMatcher("/mobileshop/my-orders")).authenticated() // Доступ только после аутентификации

                        .anyRequest().permitAll() // Все остальные страницы доступны всем
                )

                // 3. Настраиваем форму входа
                .formLogin(form -> form
                        .loginPage("/mobileshop/auth/login") // Кастомная страница логина
                        .loginProcessingUrl("/process_login") // URL для обработки логина
                        .failureUrl("/mobileshop/auth/login?error") // Ошибка входа
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
                            response.sendRedirect(isAdmin ? "/mobileshop/admin_page" : "/mobileshop/my-orders");
                        }) // Перенаправление в зависимости от роли
                )

                // 4. Настраиваем выход из системы
                .logout(logout -> logout
                        .logoutUrl("/mobileshop/logout") // URL выхода
                        .logoutSuccessUrl("/mobileshop/auth/login") // После выхода
                        .invalidateHttpSession(true) // Удаление сессии
                        .deleteCookies("JSESSIONID") // Удаление cookies
                );

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