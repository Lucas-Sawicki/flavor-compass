package org.example.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {

    private List<OrderItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }
}
