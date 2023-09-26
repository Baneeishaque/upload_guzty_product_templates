package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UpdateVendorProductVariantFields {
    public static void main(String[] args) {

        String imageUrlsText = "imageUrls";

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection("vendors").get();

            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> vendorsDocument = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot vendorDocument : vendorsDocument) {

                Iterable<CollectionReference> collections = vendorDocument.getReference().listCollections();
                for (CollectionReference subCollection : collections) {

                    String subCollectionName = subCollection.getId();

                    if (subCollectionName.equals("products")) {

                        ApiFuture<QuerySnapshot> query2 = subCollection.get();
                        QuerySnapshot querySnapshot2 = query2.get();
                        List<QueryDocumentSnapshot> productDocuments = querySnapshot2.getDocuments();

                        for (QueryDocumentSnapshot productDocument : productDocuments) {

                            Map<String, Object> data = productDocument.getData();
//                            System.out.println("productDocument = " + data);

                            String variantsText = "variants";
                            List<Map<String, Object>> variants = (List<Map<String, Object>>) data.get(variantsText);
//                            System.out.println("variants = " + variants);

                            List<Map<String, Object>> newVariants = new ArrayList<>();

                            String price = "price";
                            String variantName = "variantName";
                            String variantId = "variantId";

                            for (Map<String, Object> variant : variants) {

                                Map<String, Object> newVariant = new HashMap<>();
                                newVariant.put(price, variant.get(price));

                                Object variantIdActual = variant.get(variantId);
                                if (variantIdActual == null) {
                                    variantIdActual = variant.get("varientId");
                                }
                                newVariant.put(variantId, variantIdActual);

                                Object variantNameActual = variant.get(variantName);
                                if (variantNameActual == null) {
                                    variantNameActual = variant.get("varientName");
                                }
                                newVariant.put(variantName, variantNameActual);

                                newVariants.add(newVariant);
                            }

                            productDocument.getReference().update(Map.ofEntries(Map.entry(variantsText, newVariants)));
                        }
                        System.out.println("Done");
                    }
                }
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
