package org.example.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Category {
    APPETIZER("Appetizer"),
    SOUP("Soup"),
    MAIN_COURSE("Main course"),
    DESSERT("Dessert");

    private final String description;


    public static Category fromDescription(String description) {
        for (Category category : Category.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found with description: " + description);
    }
}
