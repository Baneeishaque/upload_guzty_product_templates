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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static guzty.banee.CheckDemoProductImages.getUrlWithoutParameters;

public class CheckVendorProductImages {
    public static void main(String[] args) {

        Firestore db;
        String imageUrlsText = "imageUrls";

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

            querySnapshot = query.get();
            List<QueryDocumentSnapshot> vendorsDocument = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot vendorDocument : vendorsDocument) {

                Iterable<CollectionReference> collections = vendorDocument.getReference().listCollections();
                for (CollectionReference subCollection : collections) {

                    String subCollectionName = subCollection.getId();
                    System.out.println("Found sub collection with id: " + subCollectionName);

                    if (subCollectionName.equals("products")) {

                        ApiFuture<QuerySnapshot> query2 = subCollection.get();
                        try {

                            QuerySnapshot querySnapshot2 = query2.get();
                            List<QueryDocumentSnapshot> productDocuments = querySnapshot2.getDocuments();

                            for (QueryDocumentSnapshot productDocument : productDocuments) {

                                List<String> imageUrls = (List<String>) productDocument.getData().get(imageUrlsText);
                                System.out.println("imageUrls = " + imageUrls);

                                for (int i = 0; i < imageUrls.size(); i++) {

                                    File destination = new File(FilenameUtils.getName(getUrlWithoutParameters(imageUrls.get(i))));
                                    try {
                                        FileUtils.copyURLToFile(new URL(imageUrls.get(i)), destination);
                                        System.out.println("destination = " + destination.getName());

                                    } catch (IOException e) {

                                        System.out.println("exception = " + e);

                                        if (e.getLocalizedMessage().contains("401")) {

                                            System.out.println("i = " + i);
                                            System.out.println("imageUrls = " + imageUrls);

                                            imageUrls.remove(i);
                                            System.out.println("imageUrls = " + imageUrls);

                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put(imageUrlsText, imageUrls);
                                            System.out.println("hashMap = " + hashMap);

//                                            productDocument.getReference().update(hashMap);

                                        } else {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        } catch (InterruptedException | ExecutionException | URISyntaxException e) {

                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
