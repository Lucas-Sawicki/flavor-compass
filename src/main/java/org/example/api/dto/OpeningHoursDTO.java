package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;
import java.util.Locale;
import java.time.format.TextStyle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursDTO {

    DayOfWeek day;
    String openTime;
    String closeTime;
    String deliveryStartTime;
    String deliveryEndTime;

    Boolean status;

    public String getDayDisplayName() {
        String displayName = day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
    }
}
