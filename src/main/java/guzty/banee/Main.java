package guzty.banee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
//            Storage storage = StorageOptions.newBuilder().
//                    setCredentials(credentials).build().getService();
//            StorageClient storageClient = StorageClient.getInstance();
            Storage storage = StorageOptions.getDefaultInstance().getService();

            DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            ApiFuture<QuerySnapshot> query = db.collection("productCategory").where(Filter.equalTo("deleted", false)).get();
            QuerySnapshot querySnapshot;
            try {
                querySnapshot = query.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            List<String> images = new ArrayList<>();
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

                                    ObjectMapper mapper = new ObjectMapper();
                                    ProductJsonModal productJsonModal = mapper.readValue(FileUtils.readFileToString(fileEntry3, StandardCharsets.UTF_8), ProductJsonModal.class);
                                    System.out.println("productJsonModal = " + productJsonModal);

//                                    System.exit(0);

//                                    images.clear();

                                } else {

                                    for (final File fileEntry4 : Objects.requireNonNull(fileEntry3.listFiles())) {

                                        String fileName = fileEntry4.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets/", "");
                                        System.out.println("Upload : " + fileName);

//                                        LocalDateTime localDateTime = LocalDateTime.now();
//                                        String blobString = dateTimeFormatterDate.format(localDateTime) + "/" + dateTimeFormatterTime.format(localDateTime) + "." + FilenameUtils.getExtension(fileEntry4.getPath());
//
//                                        BlobId blobId = BlobId.of("guzty-c2dc5.appspot.com", blobString);
//                                        try {
//                                            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
//                                                    .setMetadata(Map.ofEntries(Map.entry("picked-file-path", fileName)))
//                                                    .setContentType(Magic.getMagicMatch(fileEntry4, false).getMimeType())
//                                                    .build();
//                                            Blob blob = storage.createFrom(blobInfo, fileEntry4.toPath(), Storage.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));
////                                            System.out.println("image = " + blob.getMediaLink());
//                                            images.add(blob.getMediaLink());
//
////                                            try (InputStream image = new FileInputStream(fileEntry4.getPath())) {
////
////                                                Blob blob = storageClient.bucket("guzty-c2dc5.appspot.com").create(blobString, image, Magic.getMagicMatch(fileEntry4, false).getMimeType(), Bucket.BlobWriteOption.doesNotExist());
////                                            }
//
//                                        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
//                                            throw new RuntimeException(e);
//                                        }
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
