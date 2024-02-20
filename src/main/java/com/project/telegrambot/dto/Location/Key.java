package com.project.telegrambot.dto.Location;

import kong.unirest.core.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Key {

    // Response to Object
    Key locationKey = Unirest.get("\n" +
                    "\n" +
                    "http://dataservice.accuweather.com/locations/v1/cities/autocomplete\n")
            .asObject(Key.class)
            .getBody();
}