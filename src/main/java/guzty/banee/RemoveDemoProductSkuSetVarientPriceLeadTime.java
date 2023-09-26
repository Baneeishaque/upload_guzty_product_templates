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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RemoveDemoProductSkuSetVarientPriceLeadTime {
    public static void main(String[] args) {

        int i;
        Firestore db;
        String demoProducts = "demoProducts";

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection(demoProducts).get();
            QuerySnapshot querySnapshot = query.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("skuSet", "");
                hashMap.put("variants", new ArrayList<>());
                hashMap.put("price", 0);
                hashMap.put("leadingTime", 0);

                document.getReference().update(hashMap);
            }
        } catch (IOException | ExecutionException | InterruptedException e) {

            throw new RuntimeException(e);
        }
    }
}
