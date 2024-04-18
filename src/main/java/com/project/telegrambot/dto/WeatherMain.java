package com.project.telegrambot.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
public class WeatherMain {
    @Value("${weather.now.url}")
    String ACCU_WEATHER_URL_NOW;

    @Value("${weather.key}")
    String ACCU_WEATHER_API_KEY;

    @Value("${weather.daily.forecast.url}")
    String ACCU_WEATHER_URL_DAILY;

    @Value("${weather.location.url}")
    String ACCU_WEATHER_LOCATION_URL;

    public WeatherMain(String ACCU_WEATHER_URL_NOW, String ACCU_WEATHER_API_KEY, String ACCU_WEATHER_URL_DAILY, String ACCU_WEATHER_LOCATION_URL) {
        this.ACCU_WEATHER_URL_NOW = ACCU_WEATHER_URL_NOW;
        this.ACCU_WEATHER_API_KEY = ACCU_WEATHER_API_KEY;
        this.ACCU_WEATHER_URL_DAILY = ACCU_WEATHER_URL_DAILY;
        this.ACCU_WEATHER_LOCATION_URL = ACCU_WEATHER_LOCATION_URL;
    }

    public WeatherMain() {

    }


    protected boolean canEqual(final Object other) {
        return other instanceof WeatherMain;
    }

    public String getACCU_WEATHER_URL_NOW() {
        return this.ACCU_WEATHER_URL_NOW;
    }

    public String getACCU_WEATHER_API_KEY() {
        return this.ACCU_WEATHER_API_KEY;
    }

    public String getACCU_WEATHER_URL_DAILY() {
        return this.ACCU_WEATHER_URL_DAILY;
    }

    public String getACCU_WEATHER_LOCATION_URL() {
        return this.ACCU_WEATHER_LOCATION_URL;
    }

    public void setACCU_WEATHER_URL_NOW(String ACCU_WEATHER_URL_NOW) {
        this.ACCU_WEATHER_URL_NOW = ACCU_WEATHER_URL_NOW;
    }

    public void setACCU_WEATHER_API_KEY(String ACCU_WEATHER_API_KEY) {
        this.ACCU_WEATHER_API_KEY = ACCU_WEATHER_API_KEY;
    }

    public void setACCU_WEATHER_URL_DAILY(String ACCU_WEATHER_URL_DAILY) {
        this.ACCU_WEATHER_URL_DAILY = ACCU_WEATHER_URL_DAILY;
    }

    public void setACCU_WEATHER_LOCATION_URL(String ACCU_WEATHER_LOCATION_URL) {
        this.ACCU_WEATHER_LOCATION_URL = ACCU_WEATHER_LOCATION_URL;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WeatherMain)) return false;
        final WeatherMain other = (WeatherMain) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$ACCU_WEATHER_URL_NOW = this.getACCU_WEATHER_URL_NOW();
        final Object other$ACCU_WEATHER_URL_NOW = other.getACCU_WEATHER_URL_NOW();
        if (this$ACCU_WEATHER_URL_NOW == null ? other$ACCU_WEATHER_URL_NOW != null : !this$ACCU_WEATHER_URL_NOW.equals(other$ACCU_WEATHER_URL_NOW))
            return false;
        final Object this$ACCU_WEATHER_API_KEY = this.getACCU_WEATHER_API_KEY();
        final Object other$ACCU_WEATHER_API_KEY = other.getACCU_WEATHER_API_KEY();
        if (this$ACCU_WEATHER_API_KEY == null ? other$ACCU_WEATHER_API_KEY != null : !this$ACCU_WEATHER_API_KEY.equals(other$ACCU_WEATHER_API_KEY))
            return false;
        final Object this$ACCU_WEATHER_URL_DAILY = this.getACCU_WEATHER_URL_DAILY();
        final Object other$ACCU_WEATHER_URL_DAILY = other.getACCU_WEATHER_URL_DAILY();
        if (this$ACCU_WEATHER_URL_DAILY == null ? other$ACCU_WEATHER_URL_DAILY != null : !this$ACCU_WEATHER_URL_DAILY.equals(other$ACCU_WEATHER_URL_DAILY))
            return false;
        final Object this$ACCU_WEATHER_LOCATION_URL = this.getACCU_WEATHER_LOCATION_URL();
        final Object other$ACCU_WEATHER_LOCATION_URL = other.getACCU_WEATHER_LOCATION_URL();
        if (this$ACCU_WEATHER_LOCATION_URL == null ? other$ACCU_WEATHER_LOCATION_URL != null : !this$ACCU_WEATHER_LOCATION_URL.equals(other$ACCU_WEATHER_LOCATION_URL))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $ACCU_WEATHER_URL_NOW = this.getACCU_WEATHER_URL_NOW();
        result = result * PRIME + ($ACCU_WEATHER_URL_NOW == null ? 43 : $ACCU_WEATHER_URL_NOW.hashCode());
        final Object $ACCU_WEATHER_API_KEY = this.getACCU_WEATHER_API_KEY();
        result = result * PRIME + ($ACCU_WEATHER_API_KEY == null ? 43 : $ACCU_WEATHER_API_KEY.hashCode());
        final Object $ACCU_WEATHER_URL_DAILY = this.getACCU_WEATHER_URL_DAILY();
        result = result * PRIME + ($ACCU_WEATHER_URL_DAILY == null ? 43 : $ACCU_WEATHER_URL_DAILY.hashCode());
        final Object $ACCU_WEATHER_LOCATION_URL = this.getACCU_WEATHER_LOCATION_URL();
        result = result * PRIME + ($ACCU_WEATHER_LOCATION_URL == null ? 43 : $ACCU_WEATHER_LOCATION_URL.hashCode());
        return result;
    }

    public String toString() {
        return "WeatherMain(ACCU_WEATHER_URL_NOW=" + this.getACCU_WEATHER_URL_NOW() + ", ACCU_WEATHER_API_KEY=" + this.getACCU_WEATHER_API_KEY() + ", ACCU_WEATHER_URL_DAILY=" + this.getACCU_WEATHER_URL_DAILY() + ", ACCU_WEATHER_LOCATION_URL=" + this.getACCU_WEATHER_LOCATION_URL() + ")";
    }
}
