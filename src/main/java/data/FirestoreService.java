package data;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class FirestoreService {
    private static final Firestore db;
    static {
        try (InputStream in = FirestoreService.class.getResourceAsStream("/firebase-key.json")) {
            FirebaseOptions opt = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(in)).build();
            FirebaseApp.initializeApp(opt);
            db = FirestoreClient.getFirestore();
        } catch (Exception ex){ throw new ExceptionInInitializerError(ex); }
    }
    public static List<Student> allStudents() throws ExecutionException, InterruptedException {
        List<Student> list = new ArrayList<>();
        for (DocumentSnapshot d : db.collection("students").get().get().getDocuments())
            list.add(d.toObject(Student.class));
        return list;
    }
}

