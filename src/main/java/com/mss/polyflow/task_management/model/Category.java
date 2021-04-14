package com.mss.polyflow.task_management.model;

import com.mss.polyflow.shared.model.User;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


/*

@Table(name = "school_admin", indexes = {
    @Index(columnList = "school_code", name = "school_code_idx")
})
*/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    /**
     * has been made only for cascade property, so when category is deleted, all associated tasks will also be
     * deleted from the task table, others are there for preventing any insertion, and avoiding to make hibernate
     * generate the query for fetching the task along with fetching category , and null validation is there that
     * foreign key of task will be not be null while inserting, orphanRemoval will remove any task will null foreign key
     * after there a disassociation of category will it's task in the java code and after that the category is persisted
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "category_id", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude // to avoid the bug of lombok to fetch the associated entities while in it's generated toString method
    private List<Task> tasks;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(nullable = false)
    private Date creationTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date modificationTime;
}