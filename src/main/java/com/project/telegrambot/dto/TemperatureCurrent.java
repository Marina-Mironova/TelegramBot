package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@JsonIgnoreProperties
@Component
@RequiredArgsConstructor
public class TemperatureCurrent {

    private List<TempMetricCurrent> Metric;
}
