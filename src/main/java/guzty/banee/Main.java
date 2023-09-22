package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            StorageClient storageClient = StorageClient.getInstance();

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
                String categoryName = data.get("name").toString();
                System.out.println("Category Name = " + categoryName);
                File rootFolder = new File("/home/guzty_tech/upload_guzty_product_templates/assets");
                for (final File fileEntry : Objects.requireNonNull(rootFolder.listFiles())) {

                    for (final File fileEntry2 : Objects.requireNonNull(fileEntry.listFiles())) {

                        for (final File fileEntry3 : Objects.requireNonNull(fileEntry2.listFiles())) {

                            String filePath = fileEntry3.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets/", "");
//                            System.out.println("filePath = " + filePath);

                            if (Objects.equals(categoryName, filePath.substring(0, filePath.indexOf("/")))) {

                                if (filePath.contains(".json")) {

                                    System.out.println("Write : " + filePath);
                                    System.exit(0);

                                } else {

                                    for (final File fileEntry4 : Objects.requireNonNull(fileEntry3.listFiles())) {

                                        System.out.println("Upload : " + fileEntry4.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets/", ""));

                                        try (InputStream image = new FileInputStream(fileEntry4.getPath())) {
                                            LocalDateTime localDateTime = LocalDateTime.now();
                                            String blobString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDateTime) + "/" + DateTimeFormatter.ofPattern("HH:mm:ss").format(localDateTime);
                                            StorageClient.getInstance().bucket("guzty-c2dc5.appspot.com").create(blobString, image, "image/jpeg", Bucket.BlobWriteOption.doesNotExist());
                                        }
//                                        System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
