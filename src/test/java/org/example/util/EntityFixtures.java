package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.infrastructure.database.entity.AddressEntity;
import org.example.infrastructure.database.entity.CustomerEntity;
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
    public static CustomerEntity someCustomer1() {
        return CustomerEntity.builder()
                .name("Romek")
                .surname("Atomek")
                .phone("+48 321 543 765")
                .user(UserEntity.builder()
                        .email("r.atomek@com.pl")
                        .password("test")
                        .active(true)
                        .build())
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Warszawa")
                        .street("Krakowskie Przedmieście 46")
                        .postalCode("19-200")
                        .build())
                .build();
    }
    public static CustomerEntity someCustomer2() {
        return CustomerEntity.builder()
                .name("Ada")
                .surname("Włada")
                .phone("+48 222 111 999")
                .user(UserEntity.builder()
                        .email("a.włada@com.pl")
                        .password("test")
                        .active(true)
                        .build())
                .address(AddressEntity.builder()
                        .country("Monako")
                        .city("Monako")
                        .street("Zwycięstwa 12")
                        .postalCode("19-200")
                        .build())
                .build();
    }
    public static CustomerEntity someCustomer3() {
        return CustomerEntity.builder()
                .name("Monika")
                .surname("Ratowniczka")
                .phone("+48 555 444 999")
                .user(UserEntity.builder()
                        .email("m.ratownik@com.pl")
                        .password("test")
                        .active(true)
                        .build())
                .address(AddressEntity.builder()
                        .country("Polska")
                        .city("Warszawa")
                        .street("plac Defilad 1")
                        .postalCode("19-200")
                        .build())
                .build();
    }
}
