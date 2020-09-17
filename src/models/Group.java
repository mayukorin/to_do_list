package models;

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
            query = "select g from Group as g where g.code = :code")
})
@Entity
public class Group extends Account {

    @ManyToOne
    @JoinColumn(name="leader")
    private Person leader;//そのグループのリーダー



}
