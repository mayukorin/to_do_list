package models;

import java.sql.Timestamp;

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

@Table(name="comments")
@NamedQueries ({
        @NamedQuery (
                name ="getComments",
                query = "select c from Comment as c where c.for_task = :task and c.for_comment = null order by c.updated_at"),
        @NamedQuery(
                name ="getReturnComments",
                query = "select c from Comment as c where c.for_comment = :comment and c.delete_flag = 0")
})
@Entity
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name="for_task")
    private Task for_task;

    @ManyToOne
    @JoinColumn(name="comment_person")
    private Person comment_person;

    @ManyToOne
    @JoinColumn(name="for_comment")
    private Comment for_comment;

    @Column(name="delete_flag",nullable=false)
    private Integer delete_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Task getFor_task() {
        return for_task;
    }

    public void setFor_task(Task for_task) {
        this.for_task = for_task;
    }

    public Person getComment_person() {
        return comment_person;
    }

    public void setComment_person(Person comment_person) {
        this.comment_person = comment_person;
    }

    public Comment getFor_comment() {
        return for_comment;
    }

    public void setFor_comment(Comment for_comment) {
        this.for_comment = for_comment;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
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



}
