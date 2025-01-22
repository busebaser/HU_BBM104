import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Game {

    private double timeLeft = 10000.000;
    private double decreaseRate = 1.0;
    public static int haul;
    public static int collectedMoney;
    private boolean gameOn = true;

    // To prevent the player from moving to incorrect coordinates while falling due to gravity, a boolean value is created to ensure that the player only moves on certain rows.
    public boolean isMoveHorizontal = true;
    double fall_count = 0;
    private boolean isFlying = false;


    private Label fuel = new Label("fuel: " + String.format("%.3f", timeLeft).replace(",", "."));
    private Label haulLabel = new Label("haul: " + haul);
    private Label collectedMoneyLabel = new Label("money: " + collectedMoney);


    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public Label getFuel() {
        return fuel;
    }

    public Label getHaulLabel() {
        return haulLabel;
    }

    public Label getCollectedMoneyLabel() {
        return collectedMoneyLabel;
    }


    public void settingTime(DrillMachine dm,GamePane pane) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                long elapsedNanoSeconds = now - lastUpdate;
                double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
                lastUpdate = now;

                double effectiveDecreaseRate = decreaseRate;
                if (dm.isMoving()) {
                    // If the player is moving, the decrement rate is increased.
                    effectiveDecreaseRate *= 1000.0;
                }

                timeLeft -= (elapsedSeconds * effectiveDecreaseRate);

                // The resetting of the decimal part is checked.
                if (timeLeft <= 0) {
                    stop();
                    timeLeft = 0;
                } else if (timeLeft - (int) timeLeft <= 0.1) {
                    timeLeft -= (timeLeft - (int) timeLeft);
                }

                // The status of the fuel is updated in the Label.
                fuel.setText("fuel: " + String.format("%.3f", timeLeft).replace(",", "."));


                if (timeLeft <= 0) {
                    // When the fuel runs out, the timer stops and a green game over screen appears on the screen.
                    this.stop();
                    pane.getGreen_GameOver(collectedMoney);
                    setGameOn(false);

                }
            }
        };
        timer.start();

    }

    /**
     * Necessary methods are implemented for when the player presses the directional keys on the keyboard.
     * @param scene The scene where the game is played.
     * @param dmView The visual representation of the player, the drill machine.
     * @param dm The Drill Machine
     * @param gamePane The pane of the game.
     */

    public void keyArrangement(Scene scene, DrillMachineView dmView, DrillMachine dm, GamePane gamePane) {
        scene.setOnKeyPressed(event -> {
            int r = dm.getRow_drill();
            int c = dm.getColumn_drill();
            if(dm.getRow_drill()>=0) {  // The player's movements underground are controlled.
                if (event.getCode() == KeyCode.LEFT && isMoveHorizontal && isGameOn()) {
                    // If there is lava in the parcel where the player has arrived, the game ends and the game over screen appears.
                    if ((Parcel.getParcel(r, c - 1)).isLava()) {
                        moveLeft(dmView,dm);
                        gamePane.getRed_GameOver();


                    } else if ((Parcel.getParcel(r, c - 1)).isObstacle()) {
                        // The player cannot move to a location with an obstacle; only its appearance changes.
                        dmView.getDrillMachineView("LEFT");


                    } else if ((Parcel.getParcel(r, c - 1)).isValuable()) {
                        dmView.getDrillMachineView("LEFT");
                        // The MoneyHaulCollected boolean is used to control whether points are collected repeatedly when the player passes over the same parcel.
                        if (!Parcel.getParcel(r, c - 1).MoneyHaulCollected()) {
                            haul += Parcel.getParcel(r, c - 1).getValuable_weight();
                            haulLabel.setText("haul: " + haul);
                            collectedMoney += Parcel.getParcel(r, c - 1).getValuable_worth();
                            collectedMoneyLabel.setText("money: " + collectedMoney);
                            Parcel.getParcel(r, c - 1).setMoneyHaulCollected(true);
                        }

                        moveLeft(dmView,dm);
                        excavatingParsel(dmView,gamePane);
                        Parcel.getParcel(r, c - 1).setExcavated(true);

                    } else {
                        dmView.getDrillMachineView("LEFT");
                        Parcel.getParcel(r, c - 1).setExcavated(true);
                        moveLeft(dmView,dm);
                        excavatingParsel(dmView,gamePane);
                    }

                } else if (event.getCode() == KeyCode.RIGHT && isMoveHorizontal && isGameOn()) {
                    if (Parcel.getParcel(r, c + 1).isLava()) {
                        // If there is lava in the parcel where the player has arrived, the game ends and the game over screen appears.
                        moveRight(dmView,dm);
                        gamePane.getRed_GameOver();
                    } else if (Parcel.getParcel(r, c + 1).isObstacle()) {
                        // The player cannot move to a location with an obstacle; only its appearance changes.
                        dmView.getDrillMachineView("RIGHT");
                    } else if (Parcel.getParcel(r, c + 1).isValuable()) {
                        dmView.getDrillMachineView("RIGHT");
                        moveRight(dmView,dm);
                        excavatingParsel(dmView,gamePane);
                        Parcel.getParcel(r, c + 1).setExcavated(true);
                        if (!Parcel.getParcel(r, c + 1).MoneyHaulCollected()) {
                            // The MoneyHaulCollected boolean is used to control whether points are collected repeatedly when the player passes over the same parcel.
                            haul += Parcel.getParcel(r, c + 1).getValuable_weight();
                            haulLabel.setText("haul: " + haul);
                            collectedMoney += Parcel.getParcel(r, c - 1).getValuable_worth();
                            collectedMoneyLabel.setText("money: " + collectedMoney);
                            Parcel.getParcel(r, c + 1).setMoneyHaulCollected(true);
                        }

                    } else {
                        dmView.getDrillMachineView("RIGHT");
                        Parcel.getParcel(r, c + 1).setExcavated(true);
                        moveRight(dmView,dm);
                        excavatingParsel(dmView,gamePane);

                    }
                } else if (event.getCode() == KeyCode.DOWN && isGameOn()) {
                    if ((Parcel.getParcel(r + 1, c)).isLava()) {
                        // If there is lava in the parcel where the player has arrived, the game ends and the game over screen appears.
                        moveDown(dmView,dm);
                        dmView.getDrillMachineView("DOWN");
                        gamePane.getRed_GameOver();
                    } else if ((Parcel.getParcel(r + 1, c)).isObstacle()) {
                        // The player cannot move to a location with an obstacle; only its appearance changes.
                        dmView.getDrillMachineView("DOWN");
                    } else if (Parcel.getParcel(r + 1, c).isValuable()) {
                        dmView.getDrillMachineView("DOWN");
                        if (!Parcel.getParcel(r + 1, c).MoneyHaulCollected()) {
                            // The MoneyHaulCollected boolean is used to control whether points are collected repeatedly when the player passes over the same parcel.
                            haul += Parcel.getParcel(r + 1, c).getValuable_weight();
                            haulLabel.setText("haul: " + haul);
                            collectedMoney += Parcel.getParcel(r + 1, c).getValuable_worth();
                            collectedMoneyLabel.setText("money: " + collectedMoney);
                            Parcel.getParcel(r + 1, c).setMoneyHaulCollected(true);
                        }

                        moveDown(dmView,dm);
                        excavatingParsel(dmView,gamePane);
                        Parcel.getParcel(r + 1, c).setExcavated(true);
                    } else {
                        dmView.getDrillMachineView("DOWN");
                        moveDown(dmView,dm);
                        excavatingParsel(dmView,gamePane);
                        Parcel.getParcel(r + 1, c).setExcavated(true);
                    }
                } else if (event.getCode() == KeyCode.UP && isGameOn()) {
                    if (Parcel.getParcel(r - 1, c).isExcavated()) {
                        dmView.getDrillMachineView("UP");
                        moveUp(dmView,dm);
                    }
                }

            }else{ // If the player is above ground:
                if (event.getCode() == KeyCode.LEFT) {
                    dmView.getDrillMachineView("LEFT");
                    moveLeft(dmView,dm);
                }else if(event.getCode() == KeyCode.RIGHT){
                    dmView.getDrillMachineView("RIGHT");
                    moveRight(dmView,dm);
                }else if(event.getCode() == KeyCode.DOWN && isGameOn()){
                    dmView.getDrillMachineView("DOWN");
                    moveDown(dmView,dm);
                    excavatingParsel(dmView,gamePane);
                    Parcel.getParcel(dm.getRow_drill()+ 1, dm.getColumn_drill()).setExcavated(true);
                }

            }

        });

        scene.setOnKeyReleased(event -> {
            dm.setMoving(false);
            isFlying = true;
        });

        fallingAnimation(dmView, dm);
    }

    public void moveLeft(DrillMachineView dmView, DrillMachine dm) {
        if (dmView.getX() - 50 >= -50) { // It checks if the player has exited from the left edge of the screen.
            dmView.setX(dmView.getX() - 50);
            dm.setMoving(true);
            dm.setColumn_drill(dm.getColumn_drill() - 1);
        }
    }


    public void moveRight(DrillMachineView dmView, DrillMachine dm) {
        if (dmView.getX() + 50 <= 728) { // It checks if the player has exited from the right edge of the screen.
            dmView.setX(dmView.getX() + 50);
            dm.setMoving(true);
            dm.setColumn_drill(dm.getColumn_drill() + 1);
        }
    }


    public void moveUp(DrillMachineView dmView,DrillMachine dm) {
        dmView.setY(dmView.getY() - 50);
        dm.setMoving(true);
        dm.setRow_drill(dm.getRow_drill() - 1);

    }

    public void moveDown(DrillMachineView dmView,DrillMachine dm) {
        dmView.setY(dmView.getY() + 50);
        dm.setMoving(true);
        dm.setRow_drill(dm.getRow_drill() + 1);

    }

    /**
     * When the player progresses underground, brown images are added to the places they progress to represent the digging process.
     * @param drillMachineView The visual representation of the player.
     * @param gP The game's pane.
     */
    public void excavatingParsel(DrillMachineView drillMachineView, GamePane gP) {
        Color brown = Color.rgb(139, 69, 19);
        WritableImage writableImage = new WritableImage(50, 50);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                pixelWriter.setColor(x, y, brown);
            }
        }
        ImageView excavatedView = new ImageView(writableImage);
        excavatedView.setLayoutX(drillMachineView.getX() + 20);
        excavatedView.setLayoutY(drillMachineView.getY() + 10);
        gP.getChildren().add(gP.getChildren().size() - 1, excavatedView);
    }


    /**
     * With each fall of the Drill Machine, the Y variable is moved down by 25 units, and since all rows consist of 50-unit images, the fall_count is increased by 0.5 with each fall.
     * If the fall_count equals an integer (indicating it has passed that many rows), it can move sideways.
     * @param drillMachineView The visual representation of the player.
     * @param dm the Drill Machine.
     */
    public void fallingAnimation(DrillMachineView drillMachineView, DrillMachine dm) {
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (isFlying) {
                    long elapsedNanoSeconds = now - lastUpdate;
                    double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;

                    if (elapsedSeconds >= 0.5) { //It completes each fall in 0.5 seconds.
                        lastUpdate = now;
                        if (Parcel.getParcel(dm.getRow_drill() + 1, dm.getColumn_drill()).isExcavated() && dm.getRow_drill()>0) {
                            drillMachineView.setY(drillMachineView.getY() + 25);
                            isMoveHorizontal = false;
                            fall_count += 0.5;
                            if (Math.floor(fall_count) == fall_count) {
                                dm.setRow_drill(dm.getRow_drill() + 1);
                                isMoveHorizontal = true;
                            }
                        }
                    }
                }
            }
        }.start();
    }

}
