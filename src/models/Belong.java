package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "belongs")
@NamedQueries({
    @NamedQuery (
            name = "getGroupBelong",
            query = "select count(b) from Belong as b where b.person = :person and b.group = :group"
            ),
    @NamedQuery (
            name = "getGroupPersonBelong",
            query = "select b from Belong as b where b.person = :person and b.group = :group"
            ),
    @NamedQuery (
            name = "getGroupsBelong",
            query = "select b.group from Belong as b where b.person = :person"),
    @NamedQuery(
            name = "getPersons",
            query = "select b.person from Belong as b where b.group = :group")
})
@Entity
public class Belong {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @Column(name="position")
    private String position;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }



}
