package spring.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "roles", schema = "public")
public class Role implements GrantedAuthority {

    @Id
    @TableGenerator(name = "role_gen",
            table = "sequences",
            pkColumnName = "name",
            valueColumnName = "number",
            pkColumnValue = "roles",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "role_gen")
    @Column(name = "id")
    private  Long id;

    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
