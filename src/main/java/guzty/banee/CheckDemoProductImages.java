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

        int i;
        String demoProducts = "demoProducts";
        String imageUrlsText = "imageUrls";

        List<String> imageUrls;
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection(demoProducts).get();

            QuerySnapshot querySnapshot = query.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {

                imageUrls = (List<String>) document.getData().get(imageUrlsText);
//                System.out.println("imageUrls = " + imageUrls);

                for (i = 0; i < imageUrls.size(); i++) {

                    File destination = new File(FilenameUtils.getName(getUrlWithoutParameters(imageUrls.get(i))));
                    try {
                        FileUtils.copyURLToFile(new URL(imageUrls.get(i)), destination);
                        System.out.println("destination = " + destination.getName());

                    } catch (IOException e) {

                        System.out.println("exception = " + e);

                        if (e.getLocalizedMessage().contains("401")) {

                            System.out.println("i = " + i + ", " + document.getId());
                            // System.out.println("imageUrls = " + imageUrls);

                            imageUrls.remove(i);
                            // System.out.println("imageUrls = " + imageUrls);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put(imageUrlsText, imageUrls);
                            // System.out.println("hashMap = " + hashMap);

                            document.getReference().update(hashMap);

                        } else {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException | ExecutionException | InterruptedException e) {

            throw new RuntimeException(e);
        }
    }

    public static String getUrlWithoutParameters(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null, // Ignore the query part of the input url
                uri.getFragment()).toString();
    }
}
