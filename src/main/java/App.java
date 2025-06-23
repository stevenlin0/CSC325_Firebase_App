import javafx.application.Application;
import javafx.stage.Stage;
import ui.MainView;
import ui.SplashScreen;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        SplashScreen.show(new Stage(), () -> new MainView().show(stage));
    }
    public static void main(String[] args) { launch(); }
}
