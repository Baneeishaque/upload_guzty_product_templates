package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        // Use the application default credentials
        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection("productCategory").where(Filter.equalTo("deleted", false)).get();
            QuerySnapshot querySnapshot;
            try {
                querySnapshot = query.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
//                System.out.println("document = " + document.getData());

                Map<String, Object> data = document.getData();
                for (final File fileEntry : Objects.requireNonNull((new File("~/upload_guzty_product_templates/assets")).listFiles())) {

                    String filePath = fileEntry.getPath().replaceFirst("~/upload_guzty_product_templates/assets/", "");
                    if (data.get("name") == filePath.substring(0, filePath.indexOf("/"))) {

                        if (filePath.contains(".json")) {

                            System.out.println("Write : " + filePath);

                        } else {

                            System.out.println("Upload : " + filePath);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
