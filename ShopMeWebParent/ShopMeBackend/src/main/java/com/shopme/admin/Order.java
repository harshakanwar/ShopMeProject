package com.shopme.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    Grocery groc;

    @Getter
    @Setter
    public static class Grocery {
        List<Items> item;
    }

    @Getter
    @Setter
    public static class Items {
        List<payments> pay;
    }

    @Getter
    @Setter
    public static class payments {
        List<Card> card;
        Chips chip;

    }

    @Getter
    @Setter
    public static class Card {
        String name;
        String value;
    }

    @Getter
    @Setter
    public static class Chips {
        String key;
        String domain;
    }


    public Order.Chips findCardByValue(String key) {

        Map<String, Pair<String, String>> exampleMap = new HashMap<>();

        exampleMap.put("cc", Pair.of("", ""));
        if (groc == null || groc.item == null || groc.item.isEmpty()) {
            return null; // No matching Card found
        }

        return groc.item.stream()
                .flatMap(item -> item.pay.stream())
                .filter(payment -> payment.card.stream()
                        .anyMatch(card -> key.equals(card.value)))
                .findFirst()
                .map(payment -> payment.chip)
                .orElse(null);
    }

}
