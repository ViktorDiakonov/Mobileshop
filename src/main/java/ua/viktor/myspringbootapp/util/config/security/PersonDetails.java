package ua.viktor.myspringbootapp.util.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.viktor.myspringbootapp.models.Person;
import java.util.Collection;
import java.util.Collections;
/**
 * @author Diakonov Viktor
 */
@AllArgsConstructor
public class PersonDetails implements UserDetails {

    private final Person person;

    // метод для авторизации пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUserName();
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

    // todo нужно, чтобы получать данные аутентифицированного пользователя
    public Person getPerson(){
        return this.person;
    }
}
