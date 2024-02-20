
package com.project.telegrambot.dto.Location.Country;

import kong.unirest.core.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalisedName {
    LocalisedName country = Unirest.get("\n" +
                    "\n" +
                    "http://dataservice.accuweather.com/locations/v1/cities/autocomplete\n")
            .asObject(LocalisedName.class)
            .getBody();
}

