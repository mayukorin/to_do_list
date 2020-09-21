package models;


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

@Table(name="shows")
@NamedQueries ({
        @NamedQuery(
                name = "getShowGroupTask",
                query = "select s from Show as s where s.group = :group and s.task = :task"),
        @NamedQuery(
                name = "getGroupShow",
                query = "select s.group from Show as s where s.task = :task"),
        @NamedQuery(
                name = "getShows",
                query = "select s from Show as s where s.task = :task")

})
@Entity
public class Show {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}