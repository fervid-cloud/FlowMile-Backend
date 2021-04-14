package com.mss.polyflow.shared.dto.response;

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
public class UserAuthenticatedResponseDto {

    private String username;

    private String accessToken;

    private String message;

}
