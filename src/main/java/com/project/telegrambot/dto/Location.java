package com.project.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
@JsonIgnoreProperties
public class Location {

    @JsonProperty("LocalisedName")
    private String localisedName;

    @JsonProperty("Key")
    private String locationKey;

    @JsonProperty("Type")
    private String locationType;

}
