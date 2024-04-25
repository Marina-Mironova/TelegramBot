package com.project.telegrambot.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Data
@JsonIgnoreProperties
public class LocationAuto extends Location {

    List<Location> locationList;
}
