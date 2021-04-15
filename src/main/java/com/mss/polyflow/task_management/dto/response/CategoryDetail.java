package com.mss.polyflow.task_management.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CategoryDetail {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime creationTime;

    private LocalDateTime modificationTime;

}
