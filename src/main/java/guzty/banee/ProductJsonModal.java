package guzty.banee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductJsonModal {
    private String productName;
    private double price;
    private String productType;
    private String skuSet;
    private double gst;
    private int minCount;
    private int maxCount;
    private String productCategory;
    private String[] orderType;
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

    public String[] getOrderType() {
        return orderType;
    }

    public void setOrderType(String[] value) {
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

    public ProductJsonModal(String productName, double price, String productType, String skuSet, double gst, int minCount, int maxCount, String productCategory, String[] orderType, int leadTime, List<Map<String, Object>> varients, String shortDescription, String longDescription, boolean localDelicacies) {

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
                ", orderType=" + Arrays.toString(orderType) +
                ", leadTime=" + leadTime +
                ", varients=" + varients +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", localDelicacies=" + localDelicacies +
                '}';
    }

    String getVarientId(String varientName) {
        return switch (varientName) {
            case "1 Ltr" -> "GZV1014";
            case "1.5 Ltr" -> "GZV1015";
            case "2 Ltr" -> "GZV1016";
            case "250" -> "GZV1017";
            case "100" -> "GZV1018";
            case "300" -> "GZV1019";
            case "1kg" -> "GZV1021";
            case "1.5kg" -> "GZV1022";
            case "2kg" -> "GZV1023";
            case "3kg" -> "GZV1024";
            case "5kg" -> "GZV1025";
            case "250gm" -> "GZV1026";
            case "500gm" -> "GZV1027";
            case "750gm" -> "GZV1028";
            case "1000gm" -> "GZV1029";
            case "0.5kg" -> "GZV1030";
            case "0.5ltr" -> "GZV1031";
            case "3200ml" -> "GZV1032";
            case "4500ml" -> "GZV1033";
            case "500" -> "GZV1034";
            case "100ml" -> "GZV1035";
            case "250ml" -> "GZV1036";
            case "500ml" -> "GZV1037";
            case "750ml" -> "GZV1038";
            case "1000ml" -> "GZV1039";
            case "Large" -> "GZVID1001";
            case "Small" -> "GZVID1002";
            case "Medium" -> "GZVID1003";
            case "Extra Large" -> "GZVID1009";
            case "Full" -> "GZVID1011";
            case "Half" -> "GZVID1012";
            case "Quarter" -> "GZVID1013";
            default -> {
                System.out.println("No ID for " + varientName);
                yield "";
            }
        };
    }
}
