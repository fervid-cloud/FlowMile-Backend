package com.mss.polyflow.shared.security.authorization;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccessInfo {

    Long roleId = 1l;

    String roleName = "default_role";

    List<String> authorities = new ArrayList<>();


}
