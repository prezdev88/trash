package cl.prezdev.model.response;

import lombok.Data;

@Data
public class AvlDevice {
    private Long id;
    private String imei;
    private String provider;
    private boolean started;
}
