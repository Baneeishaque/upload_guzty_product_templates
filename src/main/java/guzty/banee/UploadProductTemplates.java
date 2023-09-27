package guzty.banee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class UploadProductTemplates {
    public static void main(String[] args) {

        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();
            Storage storage = StorageOptions.getDefaultInstance().getService();

            DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            ApiFuture<QuerySnapshot> query = db.collection("productCategory").where(Filter.equalTo("deleted", false)).get();
            QuerySnapshot querySnapshot = query.get();
            List<String> images = new ArrayList<>();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
//                System.out.println("document = " + document.getData());

                Map<String, Object> data = document.getData();
                String categoryName = data.get("name").toString();
                System.out.println("Category Name = " + categoryName);
                File rootFolder = new File("/home/guzty_tech/upload_guzty_product_templates/assets2");
                for (final File fileEntry : Objects.requireNonNull(rootFolder.listFiles())) {

                    for (final File fileEntry2 : Objects.requireNonNull(fileEntry.listFiles())) {

                        for (final File fileEntry3 : Objects.requireNonNull(fileEntry2.listFiles())) {

                            String filePath = fileEntry3.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets2/", "");
//                            System.out.println("filePath = " + filePath);

                            if (Objects.equals(categoryName, filePath.substring(0, filePath.indexOf("/")))) {

                                if (filePath.contains(".json")) {

                                    System.out.println("Write : " + filePath);

                                    ObjectMapper mapper = new ObjectMapper();
                                    ProductJsonModal productJsonModal = mapper.readValue(FileUtils.readFileToString(fileEntry3, StandardCharsets.UTF_8), ProductJsonModal.class);
//                                    System.out.println("productJsonModal = " + productJsonModal);

                                    ApiFuture<DocumentSnapshot> querySnapshotApiFuture = db.collection("settings").document("settings").get();
                                    DocumentSnapshot settingsDocument = querySnapshotApiFuture.get();
                                    int demoProductId = Integer.parseInt(Objects.requireNonNull(settingsDocument.get("demoProductId")).toString());
                                    String id = "GZDP" + demoProductId;
//                                        System.out.println("id = " + id);

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("demoProductId", String.valueOf(++demoProductId));

                                    settingsDocument.getReference().update(hashMap);

                                    DocumentReference demoProductReference = db.collection("demoProducts").document(id);

                                    ProductModel productModal = new ProductModel(LocalDateTime.now(), false, true, productJsonModal.getProductName().trim(), images, productJsonModal.getShortDescription(), productJsonModal.getLongDescription(), productJsonModal.getPrice(), productJsonModal.getLeadTime(), productJsonModal.getSkuSet(), 0, "", "", "", true, true, data.get("id").toString(), categoryName, 0, 0, 0, 0, 0, productJsonModal.getProductType().equals("Veg"), new HashMap<>(), true, productJsonModal.getOrderType(), productJsonModal.getOrderType(), productJsonModal.getMaxCount(), productJsonModal.getMinCount(), productJsonModal.getGst(), new ArrayList<>() {
                                    }, new ArrayList<>(), 0, 0, productJsonModal.getLocalDelicacies(), false, productJsonModal.getVarients(), demoProductReference.getId(), demoProductReference, getProductTypeId(productJsonModal.getProductType(), filePath));

//                                                search: setSearchParam(productJson.productName.trim()),

//                                        System.out.println("productModal = " + productModal);

                                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//                                        System.out.println("productModal JSON = " + ow.writeValueAsString(productModal));

                                    Map<String, Object> demoProductDocument = new HashMap<>();
                                    demoProductDocument.put("createdTime", FieldValue.serverTimestamp());
                                    demoProductDocument.put("deleted", productModal.deleted);
                                    demoProductDocument.put("available", productModal.available);
                                    demoProductDocument.put("name", productModal.name);

                                    demoProductDocument.put("imageUrls", new ArrayList<>(productModal.imageUrls));

                                    demoProductDocument.put("shortDescription", productModal.shortDescription);
                                    demoProductDocument.put("longDescription", productModal.longDescription);
                                    demoProductDocument.put("price", 0);
                                    demoProductDocument.put("leadingTime", 0);
                                    demoProductDocument.put("skuSet", "");
                                    demoProductDocument.put("specialOffer", productModal.specialOffer);
                                    demoProductDocument.put("productId", productModal.productId);
                                    demoProductDocument.put("vendorId", productModal.vendorId);
                                    demoProductDocument.put("mbuVerified", productModal.mbuVerified);
                                    demoProductDocument.put("mbuAvailable", productModal.mbuAvailable);
                                    demoProductDocument.put("categoryId", productModal.categoryId);
                                    demoProductDocument.put("categoryName", productModal.categoryName);

                                    demoProductDocument.put("oneRating", productModal.oneRating);
                                    demoProductDocument.put("twoRating", productModal.twoRating);
                                    demoProductDocument.put("threeRating", productModal.threeRating);
                                    demoProductDocument.put("fourRating", productModal.fourRating);
                                    demoProductDocument.put("fiveRating", productModal.fiveRating);
                                    demoProductDocument.put("veg", productModal.veg);

                                    demoProductDocument.put("position", productModal.position);

                                    demoProductDocument.put("verified", productModal.verified);

                                    demoProductDocument.put("ordersType", new ArrayList<>(productModal.ordersType));
                                    demoProductDocument.put("selectedOrdersType", new ArrayList<>(productModal.selectedOrdersType));

                                    demoProductDocument.put("maxOrder", productModal.maxOrder);
                                    demoProductDocument.put("minOrder", productModal.minOrder);
                                    demoProductDocument.put("gst", productModal.gst);

                                    demoProductDocument.put("search", new ArrayList<>(productModal.search));

                                    demoProductDocument.put("keywords", new ArrayList<>(productModal.keywords));

                                    demoProductDocument.put("lat", productModal.lat);
                                    demoProductDocument.put("long", productModal.longitude);
                                    demoProductDocument.put("localDelicacies", productModal.localDelicacies);
                                    demoProductDocument.put("instaKitchen", productModal.instaKitchen);

                                    demoProductDocument.put("variants", List.of());

                                    demoProductDocument.put("demoProductId", productModal.demoProductId);
                                    demoProductDocument.put("reference", productModal.reference);
                                    demoProductDocument.put("productTypeId", productModal.productTypeId);

//                                    System.out.println("demoProductDocument = " + demoProductDocument);

                                    ApiFuture<WriteResult> writeResultApiFuture = db.collection("demoProducts").document(id).set(demoProductDocument);
                                    System.out.println("Update time : " + writeResultApiFuture.get().getUpdateTime());

//                                    System.exit(0);

                                    images.clear();

                                } else {

                                    for (final File fileEntry4 : Objects.requireNonNull(fileEntry3.listFiles())) {

                                        String fileName = fileEntry4.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets/", "");
                                        System.out.println("Upload : " + fileName);

                                        LocalDateTime localDateTime = LocalDateTime.now();
                                        String blobString = dateTimeFormatterDate.format(localDateTime) + "/" + dateTimeFormatterTime.format(localDateTime) + "." + FilenameUtils.getExtension(fileEntry4.getPath());

                                        BlobId blobId = BlobId.of("guzty-c2dc5.appspot.com", blobString);
                                        try {
                                            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                                    .setMetadata(Map.ofEntries(Map.entry("picked-file-path", fileName)))
                                                    .setContentType(Magic.getMagicMatch(fileEntry4, false).getMimeType())
                                                    .build();
                                            Blob blob = storage.createFrom(blobInfo, fileEntry4.toPath(), Storage.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));
//                                            System.out.println("image = " + blob.getMediaLink());
                                            images.add(blob.getMediaLink());

//                                            try (InputStream image = new FileInputStream(fileEntry4.getPath())) {
//
//                                                Blob blob = storageClient.bucket("guzty-c2dc5.appspot.com").create(blobString, image, Magic.getMagicMatch(fileEntry4, false).getMimeType(), Bucket.BlobWriteOption.doesNotExist());
//                                            }

                                        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
                                            throw new RuntimeException(e);
                                        }
//                                        System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static String getProductTypeId(String productType, String filePath) {
        switch (productType.toLowerCase().replaceAll(" ", "")) {
            case "non-veg":
                return "PDT1001";
            case "veg":
                return "PDT1002";
            case "diary":
                return "PDT1003";
            default: {
                System.out.println("Product Type Not Exist for" + productType + " in " + filePath);
                return "";
            }
        }
    }
}
