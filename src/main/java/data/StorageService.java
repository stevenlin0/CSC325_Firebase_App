package data;

import com.google.cloud.storage.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class StorageService {
    private static final Storage store = StorageOptions.getDefaultInstance().getService();
    private static final String BUCKET = "week6csc325.firebasestorage.app";  // <<— change me

    public static String upload(Path localFile, String name) throws Exception {
        Blob b = store.create(BlobInfo.newBuilder(BUCKET, "pics/"+name).build(),
                Files.readAllBytes(localFile));
        return "https://storage.googleapis.com/"+BUCKET+"/pics/"+name;
    }
}

