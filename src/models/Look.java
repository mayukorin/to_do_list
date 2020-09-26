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

@Table(name="looks")
@NamedQueries ({
    @NamedQuery(
            name = "getLookCount",
            query = "select count(l) from Look as l where l.person = :person and l.looked_task = :task"
            ),
    @NamedQuery(
            name = "getLook",
            query = "select l from Look as l where l.person = :person and l.looked_task = :task"
            ),


})
@Entity
public class Look {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="peron_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="looked_task_id")
    private Task looked_task;

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

    public Task getLooked_task() {
        return looked_task;
    }

    public void setLooked_task(Task looked_task) {
        this.looked_task = looked_task;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}
