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
public class ChangePasswordRequestDto {

    @NotNull
    @NotEmpty
    @NotBlank
    private  String curPassword;

    @NotNull
    @NotEmpty
    @NotBlank
    private  String newPassword;

    @NotNull
    @NotEmpty
    @NotBlank
    private  String confirmPassword;

}
