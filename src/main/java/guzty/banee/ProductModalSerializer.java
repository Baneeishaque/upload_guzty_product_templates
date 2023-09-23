package guzty.banee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ProductModalSerializer extends JsonSerializer<ProductModel> {

    @Override
    public void serialize(ProductModel value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("createdTime", DateTimeFormatter.ISO_DATE.format(value.createdTime));
        jsonGenerator.writeBooleanField("deleted", value.deleted);
        jsonGenerator.writeBooleanField("available", value.available);
        jsonGenerator.writeStringField("name", value.name);
        jsonGenerator.writeArrayFieldStart("imageUrls");
        for (String image : value.imageUrls) {
            jsonGenerator.writeString(image);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeStringField("shortDescription", value.shortDescription);
        jsonGenerator.writeStringField("longDescription", value.longDescription);
        jsonGenerator.writeNumberField("price", value.price);
        jsonGenerator.writeNumberField("leadingTime", value.leadingTime);
        jsonGenerator.writeStringField("skuSet", value.skuSet);
        jsonGenerator.writeNumberField("specialOffer", value.specialOffer);
        jsonGenerator.writeStringField("productId", value.productId);
        jsonGenerator.writeStringField("vendorId", value.vendorId);
        jsonGenerator.writeBooleanField("mbuVerified", value.mbuVerified);
        jsonGenerator.writeBooleanField("mbuAvailable", value.mbuAvailable);
        jsonGenerator.writeStringField("categoryId", value.categoryId);
        jsonGenerator.writeStringField("categoryName", value.categoryName);

        jsonGenerator.writeNumberField("oneRating", value.oneRating);
        jsonGenerator.writeNumberField("twoRating", value.twoRating);
        jsonGenerator.writeNumberField("threeRating", value.threeRating);
        jsonGenerator.writeNumberField("fourRating", value.fourRating);
        jsonGenerator.writeNumberField("fiveRating", value.fiveRating);
        jsonGenerator.writeBooleanField("veg", value.veg);

        jsonGenerator.writeArrayFieldStart("position");
        value.position.forEach((key, valueOfKey) -> {
            try {
                jsonGenerator.writeObjectField(key, valueOfKey);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        jsonGenerator.writeEndArray();

        jsonGenerator.writeBooleanField("verified", value.verified);

        jsonGenerator.writeArrayFieldStart("ordersType");
        for (String orderType :
                value.ordersType) {
            jsonGenerator.writeString(orderType);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("selectedOrdersType");
        for (String selectedOrderType :
                value.selectedOrdersType) {
            jsonGenerator.writeString(selectedOrderType);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeNumberField("maxOrder", value.maxOrder);
        jsonGenerator.writeNumberField("minOrder", value.minOrder);
        jsonGenerator.writeNumberField("gst", value.gst);

        jsonGenerator.writeArrayFieldStart("search");
        for (String searchKey :
                value.search) {
            jsonGenerator.writeString(searchKey);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("keywords");
        for (String keyword :
                value.keywords) {
            jsonGenerator.writeString(keyword);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeNumberField("lat", value.lat);
        jsonGenerator.writeNumberField("long", value.longitude);
        jsonGenerator.writeBooleanField("localDelicacies", value.localDelicacies);
        jsonGenerator.writeBooleanField("instaKitchen", value.instaKitchen);

        jsonGenerator.writeArrayFieldStart("variants");
        for (Map<String, Object> variant : value.variants
        ) {
            jsonGenerator.writeStartObject();
            variant.forEach((key, valueOfKey) -> {
                try {
                    jsonGenerator.writeObjectField(key, valueOfKey);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("demoProductId", value.demoProductId);
        jsonGenerator.writeStringField("reference", value.reference.getPath());
        jsonGenerator.writeStringField("productTypeId", value.productTypeId);

        jsonGenerator.writeEndObject();
    }
}
