package impl.strategy;

import model.ExpenseContext;
import model.ExpenseSplit;
import model.User;
import strategies.SplitStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplit(ExpenseContext ex) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal amount = ex.getAmount();
        List<ExpenseSplit> result = new ArrayList<>();
        List<User> users = ex.getParticipants();
        List<BigDecimal> percentage = ex.getPercents();

        for (BigDecimal perc : ex.getPercents()) {
            sum = sum.add(perc);
        }

        if (amount == null || users.size() != percentage.size()) {
            throw new IllegalArgumentException("Percent amounts size mismatch");
        }

        if (sum.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new IllegalArgumentException("Percent must sum to 100.");
        }

        for (int i = 0; i < users.size(); i++) {
            BigDecimal amt = ex.getAmount().multiply(percentage.get(i)).divide(BigDecimal.valueOf(100),
                    2, BigDecimal.ROUND_HALF_UP);
            result.add(new ExpenseSplit(users.get(i), amt));
        }

        return result;
    }
}
