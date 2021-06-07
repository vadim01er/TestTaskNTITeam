package com.github.vadim01er.testtaskntiteam.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class JsonResponse {
    @JsonProperty("status_code")
    private Integer statusCode;
    @JsonProperty("status_name")
    private String statusName;

    public JsonResponse(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        this.statusName = httpStatus.name();
    }
}
