package impl.repo;

import model.Settlement;
import repository.SettlementRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemorySettlementRepository implements SettlementRepository {
    Map<Long, Settlement> store = new ConcurrentHashMap<>();
    Map<String, Long> referenceIdMap = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Long save(Settlement s) {
        if (referenceIdMap.containsKey(s.getReferenceId())) {
            return referenceIdMap.get(s.getReferenceId());
        }

        Long id = idGen.getAndIncrement();
        referenceIdMap.put(s.getReferenceId(), id);
        s.setId(id);
        store.put(id, s);
        return id;
    }

    @Override
    public Settlement findById(Long id) {
        return store.get(id);
    }

    @Override
    public boolean existByReferenceId(String id) {
        return referenceIdMap.containsKey(id);
    }
}