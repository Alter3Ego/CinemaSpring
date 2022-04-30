package ua.omelchenko.cinema.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "([A-Z]([a-z]+){1,50}|[А-ЩЬЮЯҐІЇЄ]([а-щьюяґіїє']){1,50})", message = "{signUp.firstName.error}")
    private String firstName;
    @Pattern(regexp = "([A-Z]([a-z]+){1,50}|[А-ЩЬЮЯҐІЇЄ]([а-щьюяґіїє']){1,50})", message = "{signUp.lastName.error}")
    private String lastName;
    @Email(message = "{signUp.email.error}")
    private String email;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "{signUp.password.info}")
    private String password;
    @Min(value = 0)
    private BigDecimal balance;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String password, BigDecimal balance, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.roles = roles;
    }

    public User(Long id, String firstName, String lastName, String email, String password, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
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
}