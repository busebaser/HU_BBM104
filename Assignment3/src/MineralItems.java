import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Each mineral to be found in the game area is represented as an enum with its value, weight, image, and index number.
 */

public enum MineralItems {

    IRONIUM(30,10, path() + "valuable_ironium.png",0),
    BRONZIUM(60,10,path() + "valuable_bronzium.png",1),
    SILVERIUM(100,10,path() + "valuable_silverium.png",2),
    GOLDIUM(250,20,path() + "valuable_goldium.png",3),
    PLATINUM(750,30,path() + "valuable_platinum.png",4),
    EINSTEINIUM(2000,40,path() + "valuable_einsteinium.png",5),
    EMERALD(5000,60,path() + "valuable_emerald.png",6),
    RUBY(20000,80,path() + "valuable_ruby.png",7),
    DIAMOND(10000,100,path() + "valuable_diamond.png",8),
    AMAZONITE(500000,120,path() + "valuable_amazonite.png",9);

    private final int worth;
    private final int weight;
    private final Image image;
    private final int index;


    MineralItems(int value, int weight, String path, int index) {
        this.worth = value;
        this.weight =weight;
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        this.index = index;

    }

    public int getWorth(){
        return worth;
    }
    public int getWeight(){
        return weight;
    }
    public Image getImage(){
        return image;
    }
    public int getIndex() {return index;}


    public static final String path() {
        return "/assets/";

    }
}
