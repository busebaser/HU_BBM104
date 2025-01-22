import javafx.scene.image.ImageView;

/**
 * Each part of the underground area of the game, divided into 14 rows and 16 columns, is characterized as a Parcel.
 * Properties related to whether the parcel contains an obstacle, lava, or valuable mineral are assigned.
 */

public class Parcel {
    private int row;
    private int column;
    private boolean isValuable = false;
    private boolean isLava = false;
    private boolean isObstacle = false;

    private boolean excavated = false; // A boolean indicating whether the player has dug the area.

    private int valuable_worth = 0;
    private int valuable_weight = 0;

    private boolean moneyHaulCollected = false;
    private ImageView parsel_ImageView;


    public boolean MoneyHaulCollected() {
        return moneyHaulCollected;
    }

    public void setMoneyHaulCollected(boolean mhc) {
        this.moneyHaulCollected = mhc;
    }

    public boolean isExcavated() {
        return excavated;
    }

    public void setExcavated(boolean excavated) {
        this.excavated = excavated;
    }

    public ImageView getParcel_ImageView() {
        return parsel_ImageView;
    }

    public void setParcel_ImageView(ImageView parcelI) {
        this.parsel_ImageView = parcelI;
    }

    public int getValuable_worth() {
        return valuable_worth;
    }

    public void setValuable_worth(int vw) {
        this.valuable_worth = vw;
    }

    public int getValuable_weight() {
        return valuable_weight;
    }

    public void setValuable_weight(int valuable_weight) {
        this.valuable_weight = valuable_weight;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int c) {
        this.column = c;
    }

    public boolean isValuable() {
        return isValuable;
    }

    public void setValuable(boolean valuable) {
        isValuable = valuable;
    }

    public boolean isLava() {
        return isLava;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }

    public void setLava(boolean lava) {
        isLava = lava;
    }

    static Parcel[] parcelList = parcelList();


    /** A list containing all 224 parcels of the entire area due to its size of 14x16.
     * @return
     */
    public static Parcel[] parcelList(){
        Parcel[] parselList = new Parcel[224];
        int index = 0;
        for(int i=0; i<=13; i++){
            for(int j=0; j<=15; j++){
                Parcel parsel = new Parcel();
                parsel.setRow(i);
                parsel.setColumn(j);
                parselList[index++] = parsel;

            }
        }
        return parselList;
    }

    /**
     * @return The parcel at the given row and column provided as parameters.
     */
    public static Parcel getParcel(int row, int column){
        for(Parcel parcel : parcelList){
            if(parcel.getRow() == row && parcel.getColumn() == column){
                return parcel;
            }
        }
        return null;
    }
}
