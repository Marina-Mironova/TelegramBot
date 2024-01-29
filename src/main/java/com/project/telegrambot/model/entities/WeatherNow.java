package com.project.telegrambot.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity(name = "weatherNow")
public class WeatherNow {


        @Id
        private Long id;



    }
