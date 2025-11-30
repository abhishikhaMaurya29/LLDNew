package controller;

import dto.SettlementRequest;
import service.SettlementService;

public class SettlementController {
    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    public Long settle(SettlementRequest settlementRequest) {
        return settlementService.settle(settlementRequest);
    }
}
