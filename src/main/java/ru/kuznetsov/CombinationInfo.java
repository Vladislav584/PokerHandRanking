package ru.kuznetsov;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
public class CombinationInfo implements Comparable<CombinationInfo> {

    private final Combination combination;

    private final List<Pair<DenominationCard, Long>> sortedDenominationAndCount;

    @Override
    public int compareTo(CombinationInfo anotherCombinationInfo) {
        int combinationCompare = combination.compareTo(anotherCombinationInfo.getCombination());
        if (combinationCompare != 0) {
            return combinationCompare;
        }
        for (int i = 0; i < sortedDenominationAndCount.size(); i++) {
            DenominationCard denominationCard = sortedDenominationAndCount.get(i).getKey();
            DenominationCard anotherDenominationCard = anotherCombinationInfo.getSortedDenominationAndCount().get(i).getKey();
            int compareDenominationI = denominationCard.compareTo(anotherDenominationCard);
            if (compareDenominationI != 0) {
                return compareDenominationI;
            }
        }
        return 0;
    }
}
