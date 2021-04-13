package com.mss.polyflow.task_management.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Indexed;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
/*
indexes are automatically created on foreign keys in mysql
@Table(name = "school_admin", indexes = {
    @Index(columnList = "school_code", name = "school_code_idx")
})
*/

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private boolean taskStatus = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false, nullable = false)
    private Category category;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @CreationTimestamp
    @Column(nullable = false)
    private Date creationTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date modificationTime;
}
