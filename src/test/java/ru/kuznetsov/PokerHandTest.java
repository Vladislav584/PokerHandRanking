package ru.kuznetsov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PokerHandTest {

    @Test
    public void identicalCardsEnteredTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("KS KS 5C JD TD"));
        assertEquals("Identical cards entered", exception.getMessage());
    }

    @Test
    public void unknownCardSuitTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("KS KB 5C JD TD"));
        assertEquals("Unknown card suit 'B'", exception.getMessage());
    }

    @Test
    public void incorrectNumberTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("KS KB"));
        assertEquals("Incorrect number of cards entered. The number of cards must equal - 5!", exception.getMessage());
    }

    @Test
    public void incorrectCardValueTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("KSSS KC 5C JD TD"));
        assertEquals("Incorrect card value. The value must be two characters long!", exception.getMessage());
    }

    @Test
    public void incorrectCardValueLessTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("K KC 5C JD TD"));
        assertEquals("Incorrect card value. The value must be two characters long!", exception.getMessage());
    }

    @Test
    public void nullTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand(null));
        assertEquals("Passed an empty value. Must be a five-card poker hand", exception.getMessage());
    }

    @Test
    public void emptyStringTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand(""));
        assertEquals("Passed an empty line. Must be a five-card poker hand", exception.getMessage());
    }

    @Test
    public void unknownValueDenominationTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new PokerHand("1D 6H 5H 5C 9D"));
        assertEquals("Unknown value denomination card: '1'", exception.getMessage());
    }

    @Test
    public void checkAllCombinationTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("KS 2H 5C JD TD"));
        pokerHandList.add(new PokerHand("2D 2C AC 4C 5C"));
        pokerHandList.add(new PokerHand("AD 4C AC 4H 3S"));
        pokerHandList.add(new PokerHand("8C 8D 8H JC QC"));
        pokerHandList.add(new PokerHand("8C 9D TC JC QC"));
        pokerHandList.add(new PokerHand("8C 2C 9C JC QC"));
        pokerHandList.add(new PokerHand("8C 8D 8H JS JC"));
        pokerHandList.add(new PokerHand("8C 8D 8H 8S QC"));
        pokerHandList.add(new PokerHand("4C 5C 6C 7C 8C"));
        pokerHandList.add(new PokerHand("TC JC QC KC AC"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: STRAIGHT_FLUSH, cards: {TC JC QC KC AC}, " +
                "Combination: STRAIGHT_FLUSH, cards: {4C 5C 6C 7C 8C}, " +
                "Combination: FOUR_OF_A_KIND, cards: {8C 8D 8H 8S QC}, " +
                "Combination: FULL_HOUSE, cards: {8C 8D 8H JS JC}, " +
                "Combination: FLUSH, cards: {8C 2C 9C JC QC}, " +
                "Combination: STRAIGHT, cards: {8C 9D TC JC QC}, " +
                "Combination: THREE_OF_A_KIND, cards: {8C 8D 8H JC QC}, " +
                "Combination: TWO_PAIRS, cards: {AD 4C AC 4H 3S}, " +
                "Combination: PAIR, cards: {2D 2C AC 4C 5C}, " +
                "Combination: HIGH_CARD, cards: {KS 2H 5C JD TD}]", actual);
    }

    @Test
    public void fourPlayerTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 3H 4C 5D 6D"));
        pokerHandList.add(new PokerHand("2C 3C AC 4C 5C"));
        pokerHandList.add(new PokerHand("3C 3H 3S 4C 5D"));
        pokerHandList.add(new PokerHand("8C 8D 8H 8S QC"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: FOUR_OF_A_KIND, cards: {8C 8D 8H 8S QC}, " +
                "Combination: FLUSH, cards: {2C 3C AC 4C 5C}, " +
                "Combination: STRAIGHT, cards: {2S 3H 4C 5D 6D}, " +
                "Combination: THREE_OF_A_KIND, cards: {3C 3H 3S 4C 5D}]", actual);
    }

    @Test
    public void headsUpTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2C 3H 4C 5D 6S"));
        pokerHandList.add(new PokerHand("2S 3C TD 4C 5C"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: STRAIGHT, cards: {2C 3H 4C 5D 6S}, " +
                "Combination: HIGH_CARD, cards: {2S 3C TD 4C 5C}]", actual);
    }

    @Test
    public void sixPlayerTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 3H 4C 5D 6D"));
        pokerHandList.add(new PokerHand("TS 3C AC 4C 5C"));
        pokerHandList.add(new PokerHand("3C 3H 3S 4C 5D"));
        pokerHandList.add(new PokerHand("8C 8D 8H 8S QC"));
        pokerHandList.add(new PokerHand("2C 3H QC 5D 6S"));
        pokerHandList.add(new PokerHand("2S 3C TD 4C KC"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: FOUR_OF_A_KIND, cards: {8C 8D 8H 8S QC}, " +
                "Combination: STRAIGHT, cards: {2S 3H 4C 5D 6D}, " +
                "Combination: THREE_OF_A_KIND, cards: {3C 3H 3S 4C 5D}, " +
                "Combination: HIGH_CARD, cards: {TS 3C AC 4C 5C}, " +
                "Combination: HIGH_CARD, cards: {2S 3C TD 4C KC}, " +
                "Combination: HIGH_CARD, cards: {2C 3H QC 5D 6S}]", actual);
    }

    @Test
    public void PairTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 2H 4C 5D 6D"));
        pokerHandList.add(new PokerHand("2S 2C AC 4C 5C"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: PAIR, cards: {2S 2C AC 4C 5C}, " +
                "Combination: PAIR, cards: {2S 2H 4C 5D 6D}]", actual);
    }

    @Test
    public void TwoPairTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 2H 4C 4D 6D"));
        pokerHandList.add(new PokerHand("2S 2C TS TC 5C"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: TWO_PAIRS, cards: {2S 2C TS TC 5C}, " +
                "Combination: TWO_PAIRS, cards: {2S 2H 4C 4D 6D}]", actual);
    }

    @Test
    public void FullHouseTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 2H 4C 4D 4S"));
        pokerHandList.add(new PokerHand("2S 2C TS TC TD"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: FULL_HOUSE, cards: {2S 2C TS TC TD}, " +
                "Combination: FULL_HOUSE, cards: {2S 2H 4C 4D 4S}]", actual);
    }

    @Test
    public void FlushTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 5S 4S TS QS"));
        pokerHandList.add(new PokerHand("2D 3D TD JD AD"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: FLUSH, cards: {2D 3D TD JD AD}, " +
                "Combination: FLUSH, cards: {2S 5S 4S TS QS}]", actual);
    }

    @Test
    public void StraightTest() {
        List<PokerHand> pokerHandList = new ArrayList<>();
        pokerHandList.add(new PokerHand("2S 3S 4S 5S 6D"));
        pokerHandList.add(new PokerHand("JD TS QD KD AD"));
        Collections.sort(pokerHandList);
        String actual = String.valueOf(pokerHandList);
        assertEquals("[Combination: STRAIGHT, cards: {JD TS QD KD AD}, " +
                "Combination: STRAIGHT, cards: {2S 3S 4S 5S 6D}]", actual);
    }



}