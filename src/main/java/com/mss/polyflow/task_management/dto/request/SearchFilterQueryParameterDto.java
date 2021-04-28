package com.mss.polyflow.task_management.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class SearchFilterQueryParameterDto {

    private String type;

    private String sort;

    private String name;

    @Positive
    private Long page = 1l;

    @Range(min = 10, max = 30)
    private long pageSize = 24;

}
