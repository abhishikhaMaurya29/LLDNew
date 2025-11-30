package strategies;

import model.ExpenseContext;
import model.ExpenseSplit;

import java.util.List;

public interface SplitStrategy {
    List<ExpenseSplit> calculateSplit(ExpenseContext ex);
}
