package guzty.banee;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.io.FilenameUtils;

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
import java.util.concurrent.TimeUnit;

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
            StorageClient storageClient = StorageClient.getInstance();

            DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

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

                                        LocalDateTime localDateTime = LocalDateTime.now();
                                        String blobString = dateTimeFormatterDate.format(localDateTime) + "/" + dateTimeFormatterTime.format(localDateTime) + FilenameUtils.getExtension(fileEntry4.getPath());

//                                        BlobId blobId = BlobId.of("guzty-c2dc5.appspot.com", blobString);
                                        try {
//                                            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
//                                                    .setMetadata(Map.ofEntries(Map.entry("customMetadata", "{'picked-file-path': " + fileEntry4.getPath().replaceFirst("/home/guzty_tech/upload_guzty_product_templates/assets/", "") + "}")))
//                                                    .setContentType(Magic.getMagicMatch(fileEntry4, false).getMimeType())
//                                                    .build();
//                                            Blob blob = storage.createFrom(blobInfo, fileEntry4.toPath());

                                            try (InputStream image = new FileInputStream(fileEntry4.getPath())) {

                                                Blob blob = storageClient.bucket("guzty-c2dc5.appspot.com").create(blobString, image, Magic.getMagicMatch(fileEntry4, false).getMimeType(), Bucket.BlobWriteOption.doesNotExist());
                                                System.out.println("image link = " + blob.signUrl(7300, TimeUnit.DAYS, Storage.SignUrlOption.withContentType()));
                                            }

                                        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
                                            throw new RuntimeException(e);
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
