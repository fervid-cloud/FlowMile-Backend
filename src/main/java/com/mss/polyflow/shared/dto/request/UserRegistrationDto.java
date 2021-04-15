package com.mss.polyflow.shared.dto.request;


import com.mss.polyflow.shared.custom_annotations.EmailValidator;
import javax.validation.constraints.Email;
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
@Builder
@Data
@Accessors(chain = true)
public class UserRegistrationDto {

    @NotNull
    @NotEmpty
    @NotBlank(message = "username is mandatory")
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank // must be not null and their trimmed length must be greater than zero.
    private String password;

    @NotNull
    @NotEmpty
    @EmailValidator
    @NotBlank(message = "email is mandatory")
    private String email;

    private String phoneNumber;

    @NotNull
    @NotEmpty
    @NotBlank(message = "first name is mandatory")
    private String firstName;

    @NotNull
    @NotEmpty
    @NotBlank(message = "second name is mandatory")
    private String lastName;
}
