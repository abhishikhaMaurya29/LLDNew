package model;

public class ChannelConfig {
    private long id;
    public String tenantId;
    public ChannelType channelType;
    public String configJson;  // SMTP configs / API keys

    public ChannelConfig(long id, String tenantId, ChannelType channelType, String configJson) {
        this.id = id;
        this.tenantId = tenantId;
        this.channelType = channelType;
        this.configJson = configJson;
    }
}