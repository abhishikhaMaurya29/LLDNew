package impl.strategy;

import model.ExpenseContext;
import model.ExpenseSplit;
import model.User;
import strategies.SplitStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplit(ExpenseContext ex) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal amount = ex.getAmount();
        List<ExpenseSplit> result = new ArrayList<>();
        List<User> users = ex.getParticipants();

        for (BigDecimal amt : ex.getExactAmounts()) {
            sum = sum.add(amt);
        }

        if (amount == null || ex.getParticipants().size() != ex.getExactAmounts().size()) {
            throw new IllegalArgumentException("Exact amounts size mismatch");
        }

        if (sum.compareTo(amount) != 0) {
            throw new IllegalArgumentException("Exact amount do not sum to total.");
        }

        for (int i = 0; i < ex.getParticipants().size(); i++) {
            result.add(new ExpenseSplit(users.get(i), ex.getExactAmounts().get(i)));
        }

        return result;
    }
}