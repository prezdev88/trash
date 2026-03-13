package org.prezdev.remotetorch.model.response;

public class ToggleLightResponse {
    private boolean lightOn;

    public ToggleLightResponse() {}

    public ToggleLightResponse(boolean lightOn) {
        this.lightOn = lightOn;
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
    }
}
