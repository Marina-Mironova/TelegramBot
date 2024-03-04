package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonIgnoreProperties
public class MaximumTemperature {

    @JsonProperty("Value")
    private double value;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("UnitType")
    private int unitType;
}
