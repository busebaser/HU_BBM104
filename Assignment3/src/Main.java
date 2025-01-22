import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class Main extends Application {


    public void start(Stage primaryStage) throws FileNotFoundException {

        Game game = new Game();
        DrillMachineView drillMachineView = new DrillMachineView();
        DrillMachine dm = new DrillMachine();
        GamePane pane = new GamePane(game.getFuel(),game.getHaulLabel(),game.getCollectedMoneyLabel());
        Scene scene = new Scene(pane, 793,778); // The optimal size of the panes within the game area is calculated through experimentation and assigned to these values.
        primaryStage.setTitle("HU-Load");


        // The most suitable x and y values were determined for a position where the player can start above ground and the view can be centered.
        drillMachineView.setX(278);
        drillMachineView.setY(26);
        dm.setRow_drill(-1);
        dm.setColumn_drill(6);
        pane.getChildren().add(drillMachineView);

        game.settingTime(dm,pane);
        game.keyArrangement(scene,drillMachineView,dm,pane);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public static void main(String[] args){
        launch(args);
    }
}
