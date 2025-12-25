package model;

public class ChannelConfig {
    private long id;
    private String tenantId;
    private ChannelType channelType;
    private String configJson;

    public ChannelConfig(long id, String tenantId, ChannelType channelType, String configJson) {
        this.id = id;
        this.tenantId = tenantId;
        this.channelType = channelType;
        this.configJson = configJson;
    }
}
