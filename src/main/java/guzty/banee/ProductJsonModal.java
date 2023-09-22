package guzty.banee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

@JsonDeserialize(using = ItemDeserializer.class)
public class ProductJsonModal {
    private String productName;
    private double price;
    private String productType;
    private String skuSet;
    private double gst;
    private int minCount;
    private int maxCount;
    private String productCategory;
    private List<String> orderType;
    private int leadTime;
    private List<Map<String, Object>> varients;
    private String shortDescription;
    private String longDescription;
    private boolean localDelicacies;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        this.price = value;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String value) {
        this.productType = value;
    }

    public String getSkuSet() {
        return skuSet;
    }

    public void setSkuSet(String value) {
        this.skuSet = value;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double value) {
        this.gst = value;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int value) {
        this.minCount = value;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int value) {
        this.maxCount = value;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String value) {
        this.productCategory = value;
    }

    public List<String> getOrderType() {
        return orderType;
    }

    public void setOrderType(List<String> value) {
        this.orderType = value;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(int value) {
        this.leadTime = value;
    }

    public List<Map<String, Object>> getVarients() {
        return varients;
    }

    public void setVarients(List<Map<String, Object>> value) {
        this.varients = value;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    public boolean getLocalDelicacies() {
        return localDelicacies;
    }

    public void setLocalDelicacies(boolean value) {
        this.localDelicacies = value;
    }

    public ProductJsonModal(String productName, double price, String productType, String skuSet, double gst, int minCount, int maxCount, String productCategory, List<String> orderType, int leadTime, List<Map<String, Object>> varients, String shortDescription, String longDescription, boolean localDelicacies) {

        this.productName = productName;
        this.price = price;
        this.productType = productType;
        this.skuSet = skuSet;
        this.gst = gst;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.productCategory = productCategory;
        this.orderType = orderType;
        this.leadTime = leadTime;
        this.varients = varients;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.localDelicacies = localDelicacies;
    }

    @Override
    public String toString() {
        return "ProductJsonModal{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", productType='" + productType + '\'' +
                ", skuSet='" + skuSet + '\'' +
                ", gst=" + gst +
                ", minCount=" + minCount +
                ", maxCount=" + maxCount +
                ", productCategory='" + productCategory + '\'' +
                ", orderType=" + orderType.toString() +
                ", leadTime=" + leadTime +
                ", varients=" + varients +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", localDelicacies=" + localDelicacies +
                '}';
    }
}
