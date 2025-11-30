package service;

import dto.SettlementRequest;
import model.Settlement;
import repository.SettlementRepository;

public class SettlementService {
    private final SettlementRepository settlementRepo;
    private final BalanceService balanceService;

    public SettlementService(SettlementRepository repo, BalanceService balanceService) {
        this.settlementRepo = repo;
        this.balanceService = balanceService;
    }

    public Long settle(SettlementRequest req) {
        if (settlementRepo.existByReferenceId(req.getReferenceId())) {
            // Idempotent
            return settlementRepo.save(new Settlement(
                    null,
                    req.getFromUser(),
                    req.getToUser(),
                    req.getGroup(),
                    req.getReferenceId(),
                    req.getAmount()
            ));
        }

        Settlement s = new Settlement(
                null,
                req.getFromUser(),
                req.getToUser(),
                req.getGroup(),
                req.getReferenceId(),
                req.getAmount()
        );
        Long id = settlementRepo.save(s);
        balanceService.applySettlement(s);
        return id;
    }
}
