import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * This class sets the ImageViews of the DrillMachine based on the right, left, up, and down directions.
 */

public class DrillMachineView extends ImageView {

    private static final String PATH = "/assets/";
    private static final String LEFT_IMAGE = "drill_01.png";
    private static final String RIGHT_IMAGE = "drill_55.png";

    private static final String DOWN_IMAGE = "drill_42.png";

    private static final String UP_IMAGE = "drill_23.png";

    public DrillMachineView(){
        super(new Image(Objects.requireNonNull(DrillMachineView.class.getResourceAsStream(PATH + LEFT_IMAGE))));
    }

    public void getDrillMachineView(String type){
        switch (type){
            case "LEFT":
                setImage(new Image(Objects.requireNonNull(DrillMachineView.class.getResourceAsStream(PATH + LEFT_IMAGE))));
                break;
            case "RIGHT":
                setImage(new Image(Objects.requireNonNull(DrillMachineView.class.getResourceAsStream(PATH + RIGHT_IMAGE))));
                break;
            case "DOWN":
                setImage(new Image(Objects.requireNonNull(DrillMachineView.class.getResourceAsStream(PATH + DOWN_IMAGE))));
                break;
            case "UP":
                setImage(new Image(Objects.requireNonNull(DrillMachineView.class.getResourceAsStream(PATH + UP_IMAGE))));
                break;
            }
        }
}
