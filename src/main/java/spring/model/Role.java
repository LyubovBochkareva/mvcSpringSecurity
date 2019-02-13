package spring.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


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

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
