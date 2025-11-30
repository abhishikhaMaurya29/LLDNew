package service;

import dto.AddExpenseRequest;
import dto.CreateExpenseResponse;
import factory.SplitStrategyFactory;
import model.Expense;
import model.ExpenseContext;
import model.ExpenseSplit;
import repository.ExpenseRepository;
import strategies.SplitStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final BalanceService balanceService;

    public ExpenseService(ExpenseRepository expenseRepository, BalanceService balanceService) {
        this.expenseRepository = expenseRepository;
        this.balanceService = balanceService;
    }

    public CreateExpenseResponse addExpense(AddExpenseRequest addExpenseRequest) {
        SplitStrategy splitStrategy = SplitStrategyFactory.createSplitStrategy(addExpenseRequest.getSplitType());

        ExpenseContext expenseContext = new ExpenseContext(addExpenseRequest.getPaidBy(), addExpenseRequest.getAmount(),
                addExpenseRequest.getParticipants(), addExpenseRequest.getExactAmounts(), addExpenseRequest.getPercents());

        List<ExpenseSplit> expenseSplits = splitStrategy.calculateSplit(expenseContext);

        Expense expense = new Expense(null, addExpenseRequest.getDescription(), addExpenseRequest.getAmount(),
                addExpenseRequest.getPaidBy(), addExpenseRequest.getGroup(), expenseSplits,
                addExpenseRequest.getSplitType(), LocalDateTime.now());

        Long id = expenseRepository.save(expense);
        balanceService.updateBalance(expense);
        return new CreateExpenseResponse(id);
    }
}
