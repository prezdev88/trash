package org.prezdev.remotetorch.model.response;

public class PhoneInfoResponse {
    private String model;
    private String product;
    private String androidVersion;
    private String brand;

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "PhoneInfoResponse{" +
                "model='" + model + '\'' +
                ", product='" + product + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
