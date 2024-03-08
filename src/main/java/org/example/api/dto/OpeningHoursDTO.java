package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.util.Locale;
import java.time.format.TextStyle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursDTO {

    DayOfWeek day;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    String openTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    String closeTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    String deliveryStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    String deliveryEndTime;

    Boolean status;

    public String getDayDisplayName() {
        String displayName = day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
    }
}
