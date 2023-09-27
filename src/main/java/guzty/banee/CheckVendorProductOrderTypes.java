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
import java.util.concurrent.ExecutionException;

public class CheckVendorProductOrderTypes {
    public static void main(String[] args) {

        String ordersTypeText = "ordersType";

        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
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
//                    System.out.println("Found sub collection with id: " + subCollectionName);

                    if (subCollectionName.equals("products")) {

                        ApiFuture<QuerySnapshot> query2 = subCollection.get();

                        QuerySnapshot querySnapshot2 = query2.get();
                        List<QueryDocumentSnapshot> productDocuments = querySnapshot2.getDocuments();

                        for (QueryDocumentSnapshot productDocument : productDocuments) {

                            List<String> ordersType = (List<String>) productDocument.getData().get(ordersTypeText);
//                                System.out.println("ordersType = " + ordersType);

                            for (String orderType : ordersType) {

                                if (!orderType.equals("Dine In") && !orderType.equals("Delivery") && !orderType.equals("Take Away")) {

                                    if (!ordersType.equals("Take away")) {

                                        System.out.println("orderType = " + orderType);
                                    }
//                                    System.out.println("productDocument = " + productDocument.getReference());
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
