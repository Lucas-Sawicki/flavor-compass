package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.UserEntity;

@UtilityClass
public class EntityFixtures {
    public static OwnerEntity someOwner1() {
            return OwnerEntity.builder()
                    .name("Marek")
                    .surname("Ogarek")
                    .phone("+48 777 666 555")
                    .user(UserEntity.builder()
                            .email("m.ogarek@com.pl")
                            .password("test")
                            .active(true)
                            .build())
                    .build();
        }

    public static OwnerEntity someOwner2() {
        return OwnerEntity.builder()
                .name("Stanisław")
                .surname("Dżejson")
                .phone("+48 11 222 333")
                .user(UserEntity.builder()
                        .email("s.dżejson@com.pl")
                        .password("test")
                        .active(true)
                        .build())
                .build();
    }

    public static OwnerEntity someOwner3() {
        return OwnerEntity.builder()
                .name("Ignacy")
                .surname("Mickiewicz")
                .phone("+48 123 345 567")
                .user(UserEntity.builder()
                        .email("i.mickiewicz@com.pl")
                        .password("test")
                        .active(true)
                        .build())
                .build();
    }
}
