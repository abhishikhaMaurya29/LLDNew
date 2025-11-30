package impl.repo;

import model.Expense;
import repository.ExpenseRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryExpenseRepository implements ExpenseRepository {
    Map<Long, Expense> expenses = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @Override
    public Long save(Expense expense) {
        Long id = idGen.incrementAndGet();
        expense.setId(id);
        expenses.put(id, expense);
        return id;
    }

    @Override
    public Expense findById(Long id) {
        return expenses.get(id);
    }
}