package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UpdateDemoProductOrderTypes {
    public static void main(String[] args) {

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection("demoProducts").get();

            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> demoProductDocuments = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot demoProductDocument : demoProductDocuments) {

                Map<String, Object> data = demoProductDocument.getData();

                String ordersTypeText = "ordersType";
                List<String> ordersTypes = (List<String>) data.get(ordersTypeText);

                List<String> newOrdersType = new ArrayList<>();

                for (String orderType : ordersTypes) {

                    if(orderType.equals("Delivery")){

                        newOrdersType.add("Delivery");
                    }
                    if((orderType.equals("Take Away"))||(orderType.equals(""))){


                    }
                }
//                productDocument.getReference().update(Map.ofEntries(Map.entry(ordersTypeText, newOrdersType)));
//                System.out.println("Done");
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
