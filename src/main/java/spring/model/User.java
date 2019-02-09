package spring.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails {

    @Id
    @TableGenerator(name = "user_gen",
            table = "sequences",
            pkColumnName = "name",
            valueColumnName = "number",
            pkColumnValue = "users",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    @Column(name = "id")
    private Long id;


    @Column(name = "username", unique = true, updatable = true)
    private String username;


    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "name",  updatable = true)
    private String name;

    @Column(name = "age",  updatable = true)
    private int age;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "permissions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public User() {
    }

    public User(String username, String password, String name, int age, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    @Override
    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                age == user.age &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}

