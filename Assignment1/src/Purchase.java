public class Purchase {
    /**
     * This class represents a purchase made by the user.
     * It contains information about whether the purchase is suitable, the amount of money spent,
     * the type of purchase (either slot selection or nutritional value selection),
     * and the value of the purchase (either slot index or nutritional value).
     */
    private boolean suitable;
    private int money;
    private String type;
    private int value;

    /**
     * Constructs a purchase with the given parameters.
     *
     * @param suitable True if the purchase is suitable, false otherwise.
     * @param money    The amount of money spent on the purchase.
     * @param type     The type of purchase (slot selection or nutritional value selection).
     * @param value    The value of the purchase (slot index or nutritional value).
     */
    public Purchase(boolean suitable, int money, String type, int value){
        this.suitable = suitable;
        this.money = money;
        this.type = type;
        this.value = value;
    }
    /**
     * Sets whether the purchase is suitable or not.
     * @param suitable True if the purchase is suitable, false otherwise.
     */
    public void setSuitable(boolean suitable) {
        this.suitable = suitable;
    }
    /**
     * Returns whether the purchase is suitable or not.
     * @return True if the purchase is suitable, false otherwise.
     */
    public boolean isSuitable(){
        return suitable;
    }
    /**
     * Sets the amount of money spent on the purchase.
     * @param money The amount of money spent on the purchase.
     */
    public void setMoney(int money){
        this.money = money;
    }
    /**
     * Returns the amount of money spent on the purchase.
     * @return The amount of money spent on the purchase.
     */
    public int getMoney(){
        return money;
    }
    /**
     * Sets the type of purchase (slot selection or nutritional value selection).
     * @param type The type of purchase.
     */

    public void setType(String type){
        this.type = type;
    }
    /**
     * Returns the type of purchase (slot selection or nutritional value selection).
     * @return The type of purchase.
     */
    public String getType(){
        return type;
    }
    /**
     * Sets the value of the purchase (slot index or nutritional value).
     * @param value The value of the purchase.
     */

    public void setValue(int value) {
        this.value = value;
    }
    /**
     * Returns the value of the purchase (slot index or nutritional value).
     * @return The value of the purchase.
     */
    public int getValue(){
        return value;
    }
}

