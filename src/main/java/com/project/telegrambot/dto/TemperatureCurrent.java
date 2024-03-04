package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@JsonIgnoreProperties
@Component
@RequiredArgsConstructor
public class TemperatureCurrent {

    @JsonProperty("Metric")
    private TempMetricCurrent tempMetricCurrent;
}
