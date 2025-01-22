import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.Random;

/**
 * The background of the game, including the pane, is prepared in this class.
 */
public class GamePane extends BorderPane{

    BackGroundItems bgi = new BackGroundItems();


    public GamePane(Label fuel, Label haul, Label collectedMoney){

        HBox sky = new HBox();
        sky.setStyle("-fx-background-color: navy;");


        fuel.setStyle("-fx-text-fill: white;");
        fuel.setFont(new Font("Arial",22));

        haul.setStyle("-fx-text-fill: white;");
        haul.setFont(new Font("Arial",22));

        collectedMoney.setStyle("-fx-text-fill: white;");
        collectedMoney.setFont(new Font("Arial",22));


        VBox strings = new VBox();
        strings.setPadding(new Insets(5));
        strings.getChildren().addAll(fuel, haul, collectedMoney);
        sky.getChildren().add(strings);



        StackPane underground = new StackPane();
        VBox blueBackGround = new VBox();
        blueBackGround.setStyle("-fx-background-color: navy;");
        blueBackGround.setPrefSize(800,800);

        underground.getChildren().addAll(blueBackGround,settingUnderground());

        this.setTop(sky);
        this.setCenter(underground);


    }



    public VBox settingUnderground(){
        VBox underground = new VBox();
        underground.setSpacing(-0.5); // To avoid gaps between images in the game, a gap of -0.5 is assigned between them.

        for(int rowIndex= 0; rowIndex<=13; rowIndex++){
            HBox row = new HBox();

            for(int column=0; column <= 15; column++) {
                int[] coord = {rowIndex, column};
                for (int[] obstaclesCoord : UnderGroundCoordinates.obstaclesCoordinates) {
                    if (Arrays.equals(coord, obstaclesCoord)) {
                        // Obstacle property is assigned to the parcel at the given coordinates.
                        Parcel.getParcel(rowIndex, column).setObstacle(true);
                        // A random image from the obstacle images is assigned as the image view of the parcel at the random index.
                        Parcel.getParcel(rowIndex,column).setParcel_ImageView(new ImageView((bgi.obstaclesImage().get(selectRandomNumber(0, 2)))));
                        // The obstacle image is added to the pane at the specified coordinates.
                        row.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());

                    }
                }

                for (int[] valuablesCoord : UnderGroundCoordinates.valuablesCoordinates) {
                    if (Arrays.equals(coord, valuablesCoord)) {
                        // Valuable mineral is assigned to the parcel at the given coordinates.
                        Parcel.getParcel(rowIndex, column).setValuable(true);
                        // A random mineral is selected from among 10 minerals.
                        MineralItems mineral = getByIndex(selectRandomNumber(0, 9));
                        // The image, value, and weight of the selected mineral are assigned to the parcel.
                        Parcel.getParcel(rowIndex, column).setValuable_worth(mineral.getWorth());
                        Parcel.getParcel(rowIndex, column).setValuable_weight(mineral.getWeight());
                        Parcel.getParcel(rowIndex,column).setParcel_ImageView(new ImageView(mineral.getImage()));

                        //To place the mineral image on top of a soil image, a StackPane is used to stack the two images on top of each other.
                        StackPane stackPane = new StackPane();
                        if (rowIndex == 0) {
                            stackPane.getChildren().add(new ImageView(bgi.topGroundImages().get(selectRandomNumber(0, 1))));
                            stackPane.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());
                        } else {
                            stackPane.getChildren().add(new ImageView(bgi.soilImages().get(selectRandomNumber(0, 4))));
                            stackPane.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());
                        }
                        row.getChildren().add(stackPane);
                    }
                }

                for (int[] lavasCoord : UnderGroundCoordinates.lavasCoordinates) {
                    if (Arrays.equals(coord, lavasCoord)) {
                        // Lava property and the image is assigned to the parcel at the given coordinates.
                        Parcel.getParcel(rowIndex, column).setLava(true);
                        Parcel.getParcel(rowIndex,column).setParcel_ImageView(new ImageView(bgi.lavaImages().get(selectRandomNumber(0,2))));
                        row.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());

                    }
                }

                // While assigning images for the top row of the underground, if the remaining parcels are not lava, obstacle, or valuable minerals, soil images are assigned.
                if (!Parcel.getParcel(rowIndex, column).isObstacle() && !Parcel.getParcel(rowIndex, column).isValuable() && !Parcel.getParcel(rowIndex, column).isLava()) {
                    if (rowIndex == 0) {
                        Parcel.getParcel(rowIndex,column).setParcel_ImageView(new ImageView(bgi.topGroundImages().get(selectRandomNumber(0, 1))));
                        row.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());
                    } else {
                        Parcel.getParcel(rowIndex,column).setParcel_ImageView(new ImageView(bgi.soilImages().get(selectRandomNumber(0, 4))));
                        row.getChildren().add(Parcel.getParcel(rowIndex,column).getParcel_ImageView());
                    }

                }



            }
            row.setSpacing(-0.5);
            underground.getChildren().add(row);



        }

        return underground;
    }


    /**
     * @return A random number within the range of two given numbers provided as parameters
     */
    public static int selectRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * @param index The index property of the mineral.
     * @return The mineral at the specified index number from the Mineral enum.
     */
    public static MineralItems getByIndex(int index) {
        for (MineralItems item : MineralItems.values()) {
            if (item.getIndex() == index) {
                return item;
            }
        }
        return null;
    }

    /**
     * The game pane is replaced with a red game-over screen displayed when the game is over,due to the player falling into lava.
     */
    public void getRed_GameOver(){
        this.getChildren().clear();

        Rectangle red = new Rectangle(793,778,Color.DARKRED);
        Label gameOver = new Label("GAME OVER");

        StackPane pane = new StackPane();
        gameOver.setStyle("-fx-text-fill: white;");
        gameOver.setFont(new Font("Arial",60));

        pane.getChildren().addAll(red,gameOver);

        setCenter(pane);
    }

    /**
     * The game pane is replaced with a green background and a 'Game Over' text displaying the total score earned by the player when the player's fuel runs out.
     * @param money The total amount of money the player has collected from minerals in the game.
     */
    public void getGreen_GameOver(int money){
        this.getChildren().clear();

        this.setStyle("-fx-background-color: #035003;");

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial",60));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX((793 - gameOverText.getLayoutBounds().getWidth()) / 2);
        gameOverText.setY(300);

        Text moneyText = new Text("Collected Money: " + money);
        moneyText.setFont(Font.font("Arial",60));
        moneyText.setFill(Color.WHITE);
        moneyText.setX((793 - moneyText.getLayoutBounds().getWidth()) / 2);
        moneyText.setY(380);

        this.getChildren().addAll(gameOverText, moneyText);


    }

}
