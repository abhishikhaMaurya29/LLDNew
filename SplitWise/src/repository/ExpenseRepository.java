package repository;

import model.Expense;

public interface ExpenseRepository {
    Long save(Expense expense);
    Expense findById(Long id);
}