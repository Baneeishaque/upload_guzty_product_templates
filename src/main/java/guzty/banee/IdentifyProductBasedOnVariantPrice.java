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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class IdentifyProductBasedOnVariantPrice {
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
                            List<Map<String, Object>> variants = (List<Map<String, Object>>) data.get("variants");
                            System.out.println("variants = " + variants);
                            for (Map<String, Object> variant : variants) {
                                if (Integer.parseInt(String.valueOf((long) variant.get("price"))) == 240) {

                                    System.out.println("productDocument = " + data);
                                    System.exit(0);
                                }
                            }
                        }

                    }
                }
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
