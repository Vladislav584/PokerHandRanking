package ru.kuznetsov;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum CardSuit {

    SPADES('S'),
    HEARTS('H'),
    DIAMONDS('D'),
    CLUBS('C');

    private final char value;

    private static final Map<Character, CardSuit> CARD_SUIT_MAP = new HashMap<>();

    static {
        for (CardSuit cardSuit : CardSuit.values()) {
            CARD_SUIT_MAP.put(cardSuit.getValue(), cardSuit);
        }
    }

    public static CardSuit parse(char c) {
        CardSuit cardSuit = CARD_SUIT_MAP.get(c);
        if (cardSuit == null) {
            throw new IllegalArgumentException("Unknown card suit '" + c + "'");
        }
        return cardSuit;
    }

}
