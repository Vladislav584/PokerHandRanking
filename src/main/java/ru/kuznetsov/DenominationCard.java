package ru.kuznetsov;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum DenominationCard {

    TWO('2', 0),
    THREE('3', 1),
    FOUR('4', 2),
    FIVE('5', 3),
    SIX('6', 4),
    SEVEN('7', 5),
    EIGHT('8', 6),
    NINE('9', 7),
    TEN('T', 8),
    JACK('J', 9),
    QUEEN('Q', 10),
    KING('K', 11),
    ACE('A', 12);

    private final char strDenomination;
    private final int weight;

    public static final Map<Character, DenominationCard> DENOMINATION_CARD_MAP = new HashMap<>();

    static {
        for (DenominationCard denominationCard : DenominationCard.values()) {
            DENOMINATION_CARD_MAP.put(denominationCard.getStrDenomination(), denominationCard);
        }
    }

    public static DenominationCard parse(char c) {
        DenominationCard denominationCard = DENOMINATION_CARD_MAP.get(c);
        if (denominationCard == null) {
            throw new IllegalArgumentException("Unknown value denomination card: '" + c + "'");
        }
        return denominationCard;
    }

}
