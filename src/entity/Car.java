package entity;

public class Car {

    private int id;
    private String brandName;
    private int modelId;
    private int bodyTypeId;
    private String color;
    private int yearIssue;
    private int statusId;

    public Car(int id, String brandName, int modelId, int bodyTypeId, String color, int yearIssue, int statusId) {
        this.id = id;
        this.brandName = brandName;
        this.modelId = modelId;
        this.bodyTypeId = bodyTypeId;
        this.color = color;
        this.yearIssue = yearIssue;
        this.statusId = statusId;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(int bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYearIssue() {
        return yearIssue;
    }

    public void setYearIssue(int yearIssue) {
        this.yearIssue = yearIssue;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Car[" +
                "id=" + id +
                ", brandName='" + brandName  +
                ", modelId=" + modelId +
                ", bodyTypeId=" + bodyTypeId +
                ", color='" + color +
                ", yearIssue=" + yearIssue +
                ", statusId=" + statusId +
                ']';
    }
}
