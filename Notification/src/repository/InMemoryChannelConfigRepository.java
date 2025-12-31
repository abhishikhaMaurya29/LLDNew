package repository;

import java.util.*;
import model.*;

public class InMemoryChannelConfigRepository {

    private final Map<String, ChannelConfig> store = new HashMap<>();

    // Key = tenantId + "_" + channelType
    private String key(String tenantId, ChannelType channelType) {
        return tenantId + "_" + channelType.name();
    }

    public synchronized void save(ChannelConfig config) {
        store.put(key(config.tenantId, config.channelType), config);
    }

    public synchronized ChannelConfig get(String tenantId, ChannelType type) {
        return store.get(key(tenantId, type));
    }
}
