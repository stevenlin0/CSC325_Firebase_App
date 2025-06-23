package ui;

import auth.AuthService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class AuthDialog {
    public static void show(boolean isSignUp, Runnable onSuccess) {
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(isSignUp? "Register" : "Sign In");

        TextField mail = new TextField();
        PasswordField pw = new PasswordField();
        Label err = new Label(); err.setStyle("-fx-text-fill:red");
        Button ok = new Button(isSignUp? "Create" : "Log In");

        ok.setOnAction(e -> {
            try {
                if (isSignUp) AuthService.signUp(mail.getText(), pw.getText());
                else          AuthService.signIn(mail.getText(), pw.getText());
                dlg.close(); onSuccess.run();
            } catch (Exception ex){ err.setText(ex.getMessage()); }
        });

        VBox v = new VBox(8, new Label("E‑mail"), mail,
                new Label("Password"), pw,
                ok, err);
        v.setPadding(new Insets(15));
        dlg.setScene(new Scene(v)); dlg.showAndWait();
    }
}