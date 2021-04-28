package com.mss.polyflow.task_management.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Table(indexes = {
    @Index(columnList = "creation_time desc", name = "creation_time_index"),
    @Index(columnList = "modification_time desc", name = "modification_time_index"),
    @Index(columnList = "task_status", name = "task_status_index"),
    @Index(columnList = "name asc", name = "name_index")
})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "task_status", nullable = false)
    private Integer taskStatus = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false, nullable = false)
    @ToString.Exclude
    private Category category;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "modification_time", nullable = false)
    private LocalDateTime modificationTime;
}
