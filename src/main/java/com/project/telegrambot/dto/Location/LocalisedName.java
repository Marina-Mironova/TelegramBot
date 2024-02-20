package com.project.telegrambot.dto.Location;

import kong.unirest.core.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalisedName {

    // Response to Object
    LocalisedName cityEngName = Unirest.get("\n" +
                    "\n" +
                    "http://dataservice.accuweather.com/locations/v1/cities/autocomplete\n")
            .asObject(LocalisedName.class)
            .getBody();

    String cityEngNameValue = Value; // TODO вспомни, как записывается значение, чтобы вывело строку. Сейчас у тебя хэш-код выведет




}
