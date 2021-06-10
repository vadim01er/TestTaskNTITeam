package com.github.vadim01er.testtaskntiteam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO class use for validation data for {@link Lord}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LordDto {
    @NotBlank
    @Length(min = 1, max = 250)
    private String name;

    @NotNull
    @Min(1)
    @Max(200)
    private Integer age;
}
