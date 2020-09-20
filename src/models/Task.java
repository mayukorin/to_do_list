package models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="tasks")
@NamedQueries ({
    @NamedQuery(
            name = "getPersonsTask",
            query = "select t from Task as t where t.account = :account"),
    @NamedQuery(
            name = "openGroupTask",
            query = "select t from Task as t,Show as s where t.account = :account and t = s.task and s.group = :group and t.new_flag = 1"),
    @NamedQuery(
            name = "GroupMemberAllTask",
            query = "select t from Task as t,Show as s  where t = s.task and s.group = :group and t.new_flag = 1")
})
@Entity
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name="memo")
    private String memo;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="deadline")
    private Date deadline;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name="new_flag",nullable = false)
    private Integer new_flag;

    @ManyToOne
    @JoinColumn(name="update_person_id")
    private Person update_person_id;

    @Column(name="origin_task_id")
    private Integer origin_task_id;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(Integer new_flag) {
        this.new_flag = new_flag;
    }



    public Integer getOrigin_task_id() {
        return origin_task_id;
    }

    public void setOrigin_task_id(Integer origin_task_id) {
        this.origin_task_id = origin_task_id;
    }

    public Person getUpdate_person_id() {
        return update_person_id;
    }

    public void setUpdate_person_id(Person update_person_id) {
        this.update_person_id = update_person_id;
    }








}
