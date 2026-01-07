package repository;

import java.util.HashMap;
import java.util.Map;

public class InPaymentRepository {
    private final Map<String, String> processedKeys = new HashMap<>();

    public boolean isProcessed(String key) {
        return processedKeys.containsKey(key);
    }

    public void markProcessed(String key) {
        processedKeys.put(key, "DONE");
    }
}