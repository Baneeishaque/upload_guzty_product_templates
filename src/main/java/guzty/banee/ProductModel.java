package guzty.banee;

import com.google.cloud.firestore.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ProductModel {

    LocalDateTime createdTime;
    boolean deleted;
    boolean available;
    String name;
    List<String> imageUrls;
    String shortDescription;
    String longDescription;
    double price;
    int leadingTime;
    String skuSet;
    int specialOffer;
    String productId;
    String vendorId;
    String vendorName;
    boolean mbuVerified;
    boolean mbuAvailable;
    String categoryId;
    String categoryName;
    int oneRating;
    int twoRating;
    int threeRating;
    int fourRating;
    int fiveRating;
    boolean veg;
    Map position;

    boolean verified;
    List<String> ordersType;
    List<String> selectedOrdersType;
    int maxOrder;
    int minOrder;
    double gst;
    List<String> search;
    List<String> keywords;
    double lat;
    double longitude;
    boolean localDelicacies;
    boolean instaKitchen;
    List variants;
    String demoProductId;
    DocumentReference reference;
    String productTypeId;

    public ProductModel(LocalDateTime createdTime, boolean deleted, boolean available, String name, List<String> imageUrls, String shortDescription, String longDescription, double price, int leadingTime, String skuSet, int specialOffer, String productId, String vendorId, String vendorName, boolean mbuVerified, boolean mbuAvailable, String categoryId, String categoryName, int oneRating, int twoRating, int threeRating, int fourRating, int fiveRating, boolean veg, Map position, boolean verified, List<String> ordersType, List<String> selectedOrdersType, int maxOrder, int minOrder, double gst, List<String> search, List<String> keywords, double lat, double longitude, boolean localDelicacies, boolean instaKitchen, List variants, String demoProductId, DocumentReference reference, String productTypeId) {

        this.createdTime = createdTime;
        this.deleted = deleted;
        this.available = available;
        this.name = name;
        this.imageUrls = imageUrls;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
        this.leadingTime = leadingTime;
        this.skuSet = skuSet;
        this.specialOffer = specialOffer;
        this.productId = productId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.mbuVerified = mbuVerified;
        this.mbuAvailable = mbuAvailable;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.oneRating = oneRating;
        this.twoRating = twoRating;
        this.threeRating = threeRating;
        this.fourRating = fourRating;
        this.fiveRating = fiveRating;
        this.veg = veg;
        this.position = position;
        this.verified = verified;
        this.ordersType = ordersType;
        this.selectedOrdersType = selectedOrdersType;
        this.maxOrder = maxOrder;
        this.minOrder = minOrder;
        this.gst = gst;
        this.search = search;
        this.keywords = keywords;
        this.lat = lat;
        this.longitude = longitude;
        this.localDelicacies = localDelicacies;
        this.instaKitchen = instaKitchen;
        this.variants = variants;
        this.demoProductId = demoProductId;
        this.reference = reference;
        this.productTypeId = productTypeId;
    }
}
