package org.prezdev.notireminder.notification.channel.model;

public enum Channel {
    CHANNEL_1("channelId1", "Reminder's channel 1", "Reminder's channel description 1"),
    CHANNEL_2("channelId2", "Reminder's channel 2", "Reminder's channel description 2"),
    CHANNEL_3("channelId3", "Reminder's channel 3", "Reminder's channel description 3"),
    CHANNEL_4("channelId4", "Reminder's channel 4", "Reminder's channel description 4");

    private final String id;
    private final String name;
    private final String description;

    Channel(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
