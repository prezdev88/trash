package org.prezdev.notireminder.notification.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationStatus {
    private boolean permanent;
    private boolean autoClose;
}
