package repository;

import model.Settlement;

public interface SettlementRepository {
    Long save(Settlement s);

    Settlement findById(Long id);

    boolean existByReferenceId(String id);
}
