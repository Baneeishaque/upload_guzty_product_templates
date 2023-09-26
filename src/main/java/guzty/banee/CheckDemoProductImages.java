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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckDemoProductImages {
    public static void main(String[] args) {

        int i = 0;
        QueryDocumentSnapshot documentSnapshot = null;
        Firestore db = null;
        String demoProducts = "demoProducts";
        String imageUrlsText = "imageUrls";
        List<String> imageUrls = null;

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection(demoProducts).get();
            QuerySnapshot querySnapshot;
            try {

                querySnapshot = query.get();

            } catch (InterruptedException | ExecutionException e) {

                throw new RuntimeException(e);
            }
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {

                documentSnapshot = document;

                imageUrls = (List<String>) document.getData().get(imageUrlsText);
//                System.out.println("imageUrls = " + imageUrls);

                for (i = 0; i < imageUrls.size(); i++) {

                    File destination = new File(FilenameUtils.getName(getUrlWithoutParameters(imageUrls.get(i))));
                    FileUtils.copyURLToFile(new URL(imageUrls.get(i)), destination);
                    System.out.println("destination = " + destination.getName());
                }
            }
        } catch (IOException | URISyntaxException e) {

            System.out.println("exception = " + e);
            System.out.println("i = " + i);
            System.out.println("imageUrls = " + imageUrls);

            if (e.getLocalizedMessage().contains("401")) {

                assert documentSnapshot != null;

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(imageUrlsText, imageUrls.remove(imageUrls.get(i)));
                System.out.println("hashMap = " + hashMap);

//                db.collection(demoProducts).document(Objects.requireNonNull(documentSnapshot.get("reference")).toString()).update(hashMap);
            }
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
