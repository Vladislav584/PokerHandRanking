package ru.kuznetsov;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class PokerHand implements Comparable<PokerHand> {

    private final List<Pair<DenominationCard, CardSuit>> cards = new ArrayList<>();
    private final CombinationInfo combinationInfo;

    public PokerHand(String hand) {
        if (Objects.isNull(hand)) {
            throw new IllegalArgumentException("Passed an empty value. Must be a five-card poker hand");
        }
        if (hand == "") {
            throw new IllegalArgumentException("Passed an empty line. Must be a five-card poker hand");
        }
        String[] cardArray = hand.split(StringUtils.SPACE);
        if (cardArray.length != 5) {
            throw new IllegalArgumentException("Incorrect number of cards entered. The number of cards must equal - 5!");
        }
        if (Arrays.stream(cardArray).distinct().count() != 5) {
            throw new IllegalArgumentException("Identical cards entered");
        }
        for (String cardItem : cardArray) {
            char[] splitCard = cardItem.toCharArray();
            if (splitCard.length != 2) {
                throw new IllegalArgumentException("Incorrect card value. The value must be two characters long!");
            }
            cards.add(new ImmutablePair<>(DenominationCard.parse(splitCard[0]), CardSuit.parse(splitCard[1])));
        }
        combinationInfo = getCombination(this);
    }

    @Override
    public int compareTo(PokerHand anotherPokerHand) {
        return combinationInfo.compareTo(anotherPokerHand.getCombinationInfo()) * -1; // По концепции естественного упорядочивания логична
        // сортировка по возрастанию, но так как в условии задания указано, что сортировка должна быть от сильной к младшей
        // руке, сделал инверсию (умножил на -1). Хотя для таких случаев обычно используются компараторы с инверсией:
        // Collections.sort(hands, Collections.reverseOrder());
    }

    private CombinationInfo getCombination(PokerHand pokerHand) {
        boolean isFlash = pokerHand.getCards().stream()
                .map(Pair::getRight)
                .distinct()
                .count() == 1;

        List<DenominationCard> cards = pokerHand.getCards().stream()
                .map(Pair::getLeft)
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        boolean isStraight = cards.size() == 5 && (cards.get(4).getWeight() - cards.get(0).getWeight() == 4);

        Map<DenominationCard, Long> cardAndCount = pokerHand.getCards().stream()
                .map(Pair::getLeft)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Pair<DenominationCard, Long>> cardAndCountSorted = cardAndCount.entrySet().stream()
                .sorted(Map.Entry.<DenominationCard, Long>comparingByValue(Collections.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey(Collections.reverseOrder())))
                .map(entry -> new ImmutablePair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        if (isFlash && isStraight) {
            return new CombinationInfo(Combination.STRAIGHT_FLUSH, cardAndCountSorted);
        }
        Long firstValue = cardAndCountSorted.get(0).getValue();
        if (firstValue == 5) {
            throw new IllegalStateException("5 identical poker cards entered");
        }
        if (firstValue == 4) {
            return new CombinationInfo(Combination.FOUR_OF_A_KIND, cardAndCountSorted);
        }
        Long secondValue = cardAndCountSorted.get(1).getValue();
        if (firstValue == 3 && secondValue == 2) {
            return new CombinationInfo(Combination.FULL_HOUSE, cardAndCountSorted);
        }
        if (isFlash) {
            return new CombinationInfo(Combination.FLUSH, cardAndCountSorted);
        }
        if (isStraight) {
            return new CombinationInfo(Combination.STRAIGHT, cardAndCountSorted);
        }
        if (firstValue == 3) {
            return new CombinationInfo(Combination.THREE_OF_A_KIND, cardAndCountSorted);
        }
        if (firstValue == 2) {
            if (secondValue == 2) {
                return new CombinationInfo(Combination.TWO_PAIRS, cardAndCountSorted);
            }
            return new CombinationInfo(Combination.PAIR, cardAndCountSorted);
        }
        return new CombinationInfo(Combination.HIGH_CARD, cardAndCountSorted);
    }

    @Override
    public String toString() {
        return "Combination: " + combinationInfo.getCombination() +
                ", cards: {" +
                cards.stream()
                        .map(it -> "" + it.getKey().getStrDenomination() + it.getValue().getValue())
                        .collect(Collectors.joining(" ")) +
                '}';
    }
}
