package ui;


import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class SplashScreen {
    public static void show(Stage splash, Runnable next) {
        ImageView iv = new ImageView(new Image(SplashScreen.class.getResource("/splash.png").toExternalForm()));
        iv.setPreserveRatio(true); iv.setFitWidth(700);

        splash.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane(iv);
        splash.setScene(new Scene(root));
        //splash.setScene(new Scene(iv));
        splash.show();

        FadeTransition ft = new FadeTransition(Duration.seconds(2.4), iv);
        ft.setFromValue(0); ft.setToValue(1); ft.setOnFinished(e -> { splash.close(); next.run(); });
        ft.play();
    }
}
