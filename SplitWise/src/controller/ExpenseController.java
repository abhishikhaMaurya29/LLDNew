package controller;

import dto.AddExpenseRequest;
import dto.CreateExpenseResponse;
import service.ExpenseService;

public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public CreateExpenseResponse addExpense(AddExpenseRequest addExpenseRequest) {
        return expenseService.addExpense(addExpenseRequest);
    }
}
