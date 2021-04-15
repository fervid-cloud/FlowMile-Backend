package com.mss.polyflow.task_management.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateTask {

    @NotEmpty
    @NotBlank
    private String name;

    private String description = "";

    @NotNull
    @Min(value = 1l, message = "The value must be positive")
    private Long categoryId;
}
