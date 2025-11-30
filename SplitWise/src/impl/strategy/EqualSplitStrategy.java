package impl.strategy;

import model.ExpenseContext;
import model.ExpenseSplit;
import model.User;
import strategies.SplitStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplit(ExpenseContext ex) {
        List<User> users = ex.getParticipants();
        BigDecimal amt = ex.getAmount();
        List<ExpenseSplit> expenseSplits = new ArrayList<>();

        BigDecimal perHead = amt.divide(BigDecimal.valueOf(users.size()), 2, BigDecimal.ROUND_HALF_UP);

        for (User user : users) {
            expenseSplits.add(new ExpenseSplit(user, perHead));
        }

        return expenseSplits;
    }
}