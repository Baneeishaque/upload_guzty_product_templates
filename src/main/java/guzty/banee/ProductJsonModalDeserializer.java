package guzty.banee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductJsonModalDeserializer extends JsonDeserializer<ProductJsonModal> {
    @Override
    public ProductJsonModal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String productName = node.get("Product name") == null ? node.get("product name").asText() : node.get("Product name").asText();
        productName = Capital(productName);
        double price = node.get("Price").asDouble();
        String productType = node.get("Product Type").asText();
        if(productType.equals("non-veg")){
            productType = "Non-Veg";
        }
        String skuSet = node.get("Sku set").asText();
        double gst = node.get("GST").asDouble();
        int minimumCount = node.get("Min count").asInt();
        int maximumCount = node.get("Max count").asInt();
        String productCategory = node.get("Product Category").asText();

        List<String> orderTypeList = new ArrayList<>();
        JsonNode orderTypesNode = node.get("Order type");
        if (orderTypesNode.isArray()) {

            for (JsonNode orderType :
                    orderTypesNode) {

                if (orderType.asText().equals("Delivery")) {

                    orderTypeList.add(orderType.asText());

                } else if (orderType.asText().equals("Take away")) {

                    orderTypeList.add("Take Away");

                } else if (orderType.asText().equals("Dine-in")) {

                    orderTypeList.add("Dine In");

                }else{
                    System.out.println("orderType = " + orderType);
                    System.exit(1);
                }
            }
        }

        int leadTime = node.get("Lead time").asInt();

        List<Map<String, Object>> varients = new ArrayList<>();
        if (node.get("Varients") != null) {

            JsonNode varientsNode = node.get("Varients");
            if (varientsNode.isArray()) {

                for (JsonNode varientNode : varientsNode) {

                    Map<String, Object> varient = new HashMap<>();
                    String varientName = varientNode.get("varient name").asText();
                    varient.put("variantName", varientName);
                    varient.put("price", varientNode.get("varient price").asText());
                    varient.put("variantId", getVarientId(varientName));
                    varients.add(varient);
                }
            }
        }
        String shortDescription = node.get("Short Description").asText();
        String longDescription = node.get("Long Description").asText();
        boolean localDelicacies = node.get("Local Delicacies").asBoolean();

        return new ProductJsonModal(productName, price, productType, skuSet, gst, minimumCount, maximumCount, productCategory, orderTypeList, leadTime, varients, shortDescription, longDescription, localDelicacies);
    }

    String getVarientId(String varientName) {
        switch (varientName) {
            case "1 Ltr":
                return "GZV1014";
            case "1.5 Ltr":
                return "GZV1015";
            case "2 Ltr":
                return "GZV1016";
            case "250":
                return "GZV1017";
            case "100":
                return "GZV1018";
            case "300":
                return "GZV1019";
            case "1kg":
                return "GZV1021";
            case "1.5kg":
                return "GZV1022";
            case "2kg":
                return "GZV1023";
            case "3kg":
                return "GZV1024";
            case "5kg":
                return "GZV1025";
            case "250gm":
                return "GZV1026";
            case "500gm":
                return "GZV1027";
            case "750gm":
                return "GZV1028";
            case "1000gm":
                return "GZV1029";
            case "0.5kg":
                return "GZV1030";
            case "0.5ltr":
                return "GZV1031";
            case "3200ml":
                return "GZV1032";
            case "4500ml":
                return "GZV1033";
            case "500":
                return "GZV1034";
            case "100ml":
                return "GZV1035";
            case "250ml":
                return "GZV1036";
            case "500ml":
                return "GZV1037";
            case "750ml":
                return "GZV1038";
            case "1000ml":
                return "GZV1039";
            case "Large":
                return "GZVID1001";
            case "Small":
                return "GZVID1002";
            case "Medium":
                return "GZVID1003";
            case "Extra Large":
                return "GZVID1009";
            case "Full":
                return "GZVID1011";
            case "Half":
                return "GZVID1012";
            case "Quarter":
                return "GZVID1013";
            default:
                System.out.println("No ID for " + varientName);
                System.exit(1);
                return "";
        }
    }

    public static String Capital(String myinput) { // user-defined method
        // store each character to a char array
        char[] charAray = myinput.toCharArray();
        // for loop to capitalize first letter 
        for(int i = 0; i < charAray.length; i++) {
           // capitalizing first letter of first word
           charAray[0] = Character.toUpperCase(charAray[0]);
           // loop to check if there is space between two letters
           if(charAray[i] == ' ') {
              // capitalizing first letter of rest of the word
              charAray[i+1] = Character.toUpperCase(charAray[i+1]);
           }
        }
        // converting the character array to the string
        myinput = String.valueOf(charAray);
        // to print the final result
        // System.out.println("After capitalizing the first letter: " + myinput); 
        return myinput;
     }
}
