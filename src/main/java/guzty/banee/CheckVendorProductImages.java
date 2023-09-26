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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckVendorProductImages {
    public static void main(String[] args) {

        Firestore db;

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection("vendors").get();
            QuerySnapshot querySnapshot;
            try {

                querySnapshot = query.get();

            } catch (InterruptedException | ExecutionException e) {

                throw new RuntimeException(e);
            }
            List<QueryDocumentSnapshot> vendorsDocument = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot vendorDocument : vendorsDocument) {

                Iterable<CollectionReference> collections = vendorDocument.getReference().listCollections();
                for (CollectionReference collRef : collections) {

                    System.out.println("Found sub collection with id: " + collRef.getId());
                }
            }
        } catch (IOException e) {

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
