package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="accounts")
@Entity
@NamedQueries ({
    @NamedQuery (

            name = "checkRegisteredCode",
            query = "SELECT COUNT(a) FROM Account AS a WHERE a.code = :code"
     ),
    @NamedQuery(
            name = "checkLoginCodeAndPassword",
            query = "SELECT a FROM Account AS a WHERE  a.code = :code AND a.password = :pass"
            )
})
public abstract class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "password",length = 64,nullable = false)
    private String password;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
