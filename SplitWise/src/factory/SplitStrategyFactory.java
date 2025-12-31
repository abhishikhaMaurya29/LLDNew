package factory;

import impl.strategy.EqualSplitStrategy;
import impl.strategy.ExactSplitStrategy;
import impl.strategy.PercentageSplitStrategy;
import model.SplitType;
import strategies.SplitStrategy;

public class SplitStrategyFactory {
    public static SplitStrategy createSplitStrategy(SplitType splitType) {
        return switch (splitType) {
            case EQUAL -> new EqualSplitStrategy();
            case EXACT -> new ExactSplitStrategy();
            case PERCENTAGE -> new PercentageSplitStrategy();
        };
    }
}