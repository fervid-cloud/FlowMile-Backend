package com.mss.polyflow.task_management.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
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
    @NotBlank /*The annotated element must not be null and must contain at least one non-whitespace character. Accepts CharSequence.*/
    private String name;

    private String description = "";

    @NotNull
    @Positive(message = "The value must be positive")
    private Long categoryId;
}
