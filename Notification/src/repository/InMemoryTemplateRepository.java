package repository;

import java.util.*;

import model.*;

public class InMemoryTemplateRepository {

    private final Map<String, NotificationTemplate> store = new HashMap<>();

    // Key = templateCode + "_" + channelType
    private String key(String templateCode, ChannelType channelType) {
        return templateCode + "_" + channelType.name();
    }

    public synchronized void save(NotificationTemplate t) {
        store.put(key(t.templateCode, t.channelType), t);
    }

    public synchronized NotificationTemplate find(String templateCode, ChannelType type) {
        return store.get(key(templateCode, type));
    }
}