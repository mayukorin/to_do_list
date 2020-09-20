package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="groups")
@NamedQueries ({
    @NamedQuery  (
            name = "getGroup",
            query = "select count(g) from Group as g where g.code = :code and g.password = :pass"),
    @NamedQuery  (
            name = "Group",
            query = "select g from Group as g where g.code = :code and g.password = :pass")
})
@Entity
public class Group extends Account {

    @ManyToOne
    @JoinColumn(name="leader")
    private Person leader;//そのグループのリーダー

    @Column(name = "updated_at")
    private Timestamp updated_at;


    public Person getLeader() {
        return leader;
    }

    public void setLeader(Person leader) {
        this.leader = leader;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }



}
