package service;

import model.*;
import repository.GroupBalanceRepository;
import repository.UserToUserBalanceRepository;
import utilities.BalanceManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BalanceService {
    private final GroupBalanceRepository groupBalanceRepository;
    private final UserToUserBalanceRepository userToUserBalanceRepository;

    public BalanceService(GroupBalanceRepository groupBalanceRepository,
                          UserToUserBalanceRepository userToUserBalanceRepository) {
        this.groupBalanceRepository = groupBalanceRepository;
        this.userToUserBalanceRepository = userToUserBalanceRepository;
    }

    public void updateBalance(Expense expense) {
        List<ExpenseSplit> expenseSplitList = expense.getSplits();
        User payer = expense.getPaidBy();
        Long groupId = expense.getGroup().getId();

        for (ExpenseSplit expenseSplit : expenseSplitList) {
            if (expenseSplit.getId().equals(payer.getId())) continue;

            User participant = expenseSplit.getUser();
            BigDecimal amount = expenseSplit.getAmount();

            ReentrantLock payerLock = BalanceManager.getLock(groupId + "_" + payer.getId());
            ReentrantLock participantLock = BalanceManager.getLock(groupId + "_" + participant.getId());

            payerLock.lock();
            participantLock.lock();

            try {
                GroupBalance gbPayer = groupBalanceRepository.find(groupId,
                        payer.getId());
                gbPayer.addBalance(expenseSplit.getAmount());
                groupBalanceRepository.save(gbPayer);

                GroupBalance gbUser = groupBalanceRepository.find(expense.getGroup().getId(),
                        participant.getId());
                gbPayer.subtractBalance(expenseSplit.getAmount());
                groupBalanceRepository.save(gbUser);
            } finally {
                payerLock.unlock();
                participantLock.unlock();
            }

            // user-user balances

            Long a = expense.getPaidBy().getId();
            Long b = expenseSplit.getUser().getId();
            Long min = Math.min(a, b);
            Long max = Math.max(a, b);

            ReentrantLock pairLock = BalanceManager.getLock(min + "_" + max);
            pairLock.lock();

            try {
                UserToUserBalance bal = userToUserBalanceRepository.find(min, max);
                if (bal == null) {
                    bal = new UserToUserBalance(min, max, BigDecimal.ZERO);
                }
                // simple convention: amount > 0 means user1 owes user2
                // Here: participant owes payer
                if (participant.getId().equals(min)) {
                    // participant is user1, owes user2
                    bal.add(amount);
                } else if (payer.getId().equals(min)) {
                    // payer is user1, participant is user2, participant owes payer => user2 owes user1 => amount negative
                    bal.subtract(amount);
                }
                userToUserBalanceRepository.save(bal);
            } finally {
                pairLock.unlock();
            }
        }
    }

    public void applySettlement(Settlement s) {
        Long groupId = s.getGroup().getId();
        User from = s.getFromUser();
        User to = s.getToUser();
        BigDecimal amount = s.getAmount();

        ReentrantLock payerLock = BalanceManager.getLock(groupId + "_" + from.getId());
        ReentrantLock participantLock = BalanceManager.getLock(groupId + "_" + to.getId());

        payerLock.lock();
        participantLock.lock();

        try {
            GroupBalance gbFrom = groupBalanceRepository.find(groupId, from.getId());
            gbFrom.addBalance(amount); // from user pays, so owes less -> balance moves towards positive
            groupBalanceRepository.save(gbFrom);

            GroupBalance gbTo = groupBalanceRepository.find(groupId, to.getId());
            gbTo.subtractBalance(amount);
            groupBalanceRepository.save(gbTo);
        } finally {
            payerLock.unlock();
            participantLock.unlock();
        }

        // Adjust user-to-user balance
        Long a = from.getId();
        Long b = to.getId();
        Long min = Math.min(a, b);
        Long max = Math.max(a, b);

        ReentrantLock pairLock = BalanceManager.getLock(min + "_" + max);
        pairLock.lock();

        try {
            UserToUserBalance bal = userToUserBalanceRepository.find(min, max);
            if (bal == null) bal = new UserToUserBalance(min, max, BigDecimal.ZERO);

            // from pays to; they owe less
            if (from.getId().equals(min)) {
                // from is user1, paying user2
                bal.subtract(amount);
            } else {
                // from is user2, paying user1
                bal.add(amount);
            }
            userToUserBalanceRepository.save(bal);
        } finally {
            pairLock.unlock();
        }
    }
}
