package com.mss.polyflow.shared.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class EditUserDetailDto {

    @NotNull
    @NotEmpty
    @NotBlank(message = "first name is mandatory")
    private String firstName;

    @NotNull
    @NotEmpty
    @NotBlank(message = "second name is mandatory")
    private String lastName;

    private String phoneNumber;

}
