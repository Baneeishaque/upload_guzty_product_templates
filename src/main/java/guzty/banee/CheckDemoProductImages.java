package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckDemoProductImages {
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

            ApiFuture<QuerySnapshot> query = db.collection("demoProducts").get();
            QuerySnapshot querySnapshot;
            try {

                querySnapshot = query.get();

            } catch (InterruptedException | ExecutionException e) {

                throw new RuntimeException(e);
            }
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {

                List<String> imageUrls = (List<String>) document.getData().get("imageUrls");
//                System.out.println("imageUrls = " + imageUrls);

                for (String image : imageUrls) {

                    File destination = new File(FilenameUtils.getName(getUrlWithoutParameters(image)));
                    FileUtils.copyURLToFile(new URL(image), destination);
                    System.out.println("destination = " + destination.getName());
                }
            }
        } catch (IOException | URISyntaxException e) {

            System.out.println("exception = " + e);
            throw new RuntimeException(e);
        }
    }

    private static String getUrlWithoutParameters(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null, // Ignore the query part of the input url
                uri.getFragment()).toString();
    }
}
