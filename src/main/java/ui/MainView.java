package ui;


import auth.AuthService;
import data.FirestoreService;
import data.StorageService;
import data.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.nio.file.Path;

public final class MainView {

    /* ---------------- controls ---------------- */
    private final Label status = new Label("Not signed in");
    private final TableView<Student> table = new TableView<>();
    private final ImageView profilePic = new ImageView();

    public void show(Stage stage) {
        stage.setTitle("CSC325 Firebase JavaFX");

        // menu ‑‑‑
        MenuBar mb = buildMenuBar();

        // table ‑‑‑
        buildTable();

        // profile/photo pane ‑‑‑
        VBox profileArea = buildProfilePane();

        BorderPane root = new BorderPane();
        root.setTop(mb);
        root.setCenter(table);
        root.setRight(profileArea);
        root.setBottom(status);
        BorderPane.setMargin(status, new Insets(4));

        Scene scene = new Scene(root, 1024, 640);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setScene(scene); stage.show();
    }

    /* ----- menu ----- */
    private MenuBar buildMenuBar() {
        Menu account = new Menu("Account");
        MenuItem signIn = new MenuItem("Sign In");
        MenuItem register = new MenuItem("Register");
        account.getItems().addAll(signIn, register);

        Menu data = new Menu("Data");
        MenuItem refresh = new MenuItem("Reload Students");
        data.getItems().add(refresh);

        MenuBar mb = new MenuBar(account, data);

        register.setOnAction(e -> AuthDialog.show(true, () -> status.setText("✓ Account created")));
        signIn.setOnAction(e -> AuthDialog.show(false, () -> status.setText("✓ Signed in")));

        refresh.setOnAction(e -> {
            try { table.getItems().setAll(FirestoreService.allStudents()); }
            catch (Exception ex) { status.setText("Load failed: "+ex.getMessage()); }
        });

        return mb;
    }

    /* ----- table ----- */
    private void buildTable() {
        table.getColumns().addAll(
                col("ID", "id", 50),
                col("First", "first",100),
                col("Last", "last",100),
                col("Dept", "dept",80),
                col("Major", "major",120),
                col("Email", "email",200)
        );
        table.setPlaceholder(new Label("No data"));
    }
    private <T> TableColumn<Student,T> col(String title,String prop,int w){
        TableColumn<Student,T> c=new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        c.setPrefWidth(w); return c;
    }

    /* ----- profile / upload photo ----- */
    private VBox buildProfilePane() {
        profilePic.setFitWidth(120); profilePic.setFitHeight(120);

        Button upload = new Button("Upload photo");
        upload.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            Path file = fc.showOpenDialog(profilePic.getScene().getWindow()).toPath();
            new Thread(() -> {
                try {
                    String url = StorageService.upload(file, file.getFileName().toString());
                    javafx.application.Platform.runLater(() -> profilePic.setImage(new javafx.scene.image.Image(url)));
                } catch (Exception ex) {
                    javafx.application.Platform.runLater(() -> status.setText("Upload failed: "+ex.getMessage()));
                }
            }).start();
        });

        VBox box = new VBox(10, profilePic, upload);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }
}
