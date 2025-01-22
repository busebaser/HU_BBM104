import javafx.scene.image.Image;
import java.util.*;

/*
This class contains methods to add images of the game into separate lists based on their categories and returns these lists.
 */
public class BackGroundItems {

    static final String PATH = "/assets/";

    public List<Image> obstaclesImage() {
        List<Image> obsImages = new ArrayList<>();
        obsImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "obstacle_01.png"))));
        obsImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "obstacle_02.png"))));
        obsImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "obstacle_03.png"))));

        return obsImages;
    }

    public List<Image> topGroundImages(){
        List<Image> groundImages = new ArrayList<>();
        groundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "top_01.png"))));
        groundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "top_02.png"))));

        return groundImages;
    }

    public List<Image> soilImages() {
        List<Image> undergroundImages = new ArrayList<>();
        undergroundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "soil_01.png"))));
        undergroundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "soil_02.png"))));
        undergroundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "soil_03.png"))));
        undergroundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "soil_04.png"))));
        undergroundImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "soil_05.png"))));

        return undergroundImages;
    }

    public List<Image> lavaImages(){
        List<Image> lavaImages = new ArrayList<>();
        lavaImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "lava_01.png"))));
        lavaImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "lava_02.png"))));
        lavaImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "lava_03.png"))));

        return lavaImages;
    }


}
