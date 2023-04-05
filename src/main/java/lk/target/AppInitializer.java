package lk.target;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {



        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);



        //Both ways are correct

//        FXMLLoader fxmlLoader  =  new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
//        stage.setScene(new Scene(fxmlLoader.load()));

        stage.getIcons().add(new Image("/image/target.png"));
        stage.setResizable(true);
        stage.setTitle("Target");
        stage.centerOnScreen();
        stage.setFullScreen(false);
        stage.show();
    }
}
