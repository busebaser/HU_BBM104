public class Slot {
    /**
     * This class represents a slot in the vending machine.
     * It contains information about whether the slot is empty, the product stored in the slot,
     * the amount of the product, the calorie content of the product, the price of the product,
     * and the carbohydrate, protein, and fat content of the product.
     */
    private boolean empty;
    private String slotProduct;
    private int amount;
    private int calorie;

    private int price;

    private double carb;
    private double protein;
    private double fat;

    /**
     * Constructs a slot with the given parameters.
     *
     * @param empty       True if the slot is empty, false otherwise.
     * @param slotProduct The product stored in the slot.
     * @param amount      The amount of the product in the slot.
     * @param calorie     The calorie content of the product in the slot.
     */
    public Slot(boolean empty, String slotProduct,int amount,int calorie){
        this.empty = empty;
        this.slotProduct = slotProduct;
        this.amount = amount;
        this.calorie = calorie;
    }
    /**
     * Returns whether the slot is empty or not.
     * @return True if the slot is empty, false otherwise.
     */
    public boolean is_empty() {
        return empty;
    }
    /**
     * Sets whether the slot is empty or not.
     * @param empty True if the slot is empty, false otherwise.
     */
    public void setEmpty(boolean empty){
        this.empty = empty;
    }
    /**
     * Returns the product stored in the slot.
     * @return The product stored in the slot.
     */
    public String getSlotProduct(){
        return slotProduct;
    }
    /**
     * Sets the product stored in the slot.
     * @param slotProduct The product to be stored in the slot.
     */
    public void setSlotProduct(String slotProduct){
        this.slotProduct = slotProduct;
    }
    /**
     * Returns the amount of the product in the slot.
     * @return The amount of the product in the slot.
     */
    public int getAmount(){
        return amount;
    }
    /**
     * Sets the amount of the product in the slot.
     * @param amount The amount of the product in the slot.
     */
    public void setAmount(int amount){
        this.amount = amount;
    }
    /**
     * Increases the amount of the product in the slot by 1.
     */
    public void increaseAmount(){
        this.amount += 1;
    }
    /**
     * Decreases the amount of the product in the slot by 1.
     */
    public void decreaseAmount() {this.amount -= 1;}
    /**
     * Sets the calorie content of the product in the slot.
     * @param calorie The calorie content of the product in the slot.
     */

    public void setSlotCalorie(int calorie){
        this.calorie = calorie;
    }
    /**
     * Returns the calorie content of the product in the slot.
     * @return The calorie content of the product in the slot.
     */
    public int getSlotCalorie(){
        return calorie;
    }
    /**
     * Sets the price of the product in the slot.
     * @param price The price of the product in the slot.
     */

    public void setPrice(int price){ this.price = price;}
    /**
     * Returns the price of the product in the slot.
     * @return The price of the product in the slot.
     */
    public int getPrice(){ return price;}
    /**
     * Sets the carbohydrate content of the product in the slot.
     * @param carb The carbohydrate content of the product in the slot.
     */
    public void setCarb(double carb){
        this.carb = carb;
    }
    /**
     * Returns the carbohydrate content of the product in the slot.
     * @return The carbohydrate content of the product in the slot.
     */
    public double getCarb(){
        return carb;
    }
    /**
     * Sets the protein content of the product in the slot.
     * @param protein The protein content of the product in the slot.
     */
    public void setProtein(double protein){
        this.protein = protein;
    }
    /**
     * Returns the protein content of the product in the slot.
     * @return The protein content of the product in the slot.
     */
    public double getProtein(){
        return protein;
    }
    /**
     * Sets the fat content of the product in the slot.
     * @param fat The fat content of the product in the slot.
     */

    public void setFat(double fat) {
        this.fat = fat;
    }
    /**
     * Returns the fat content of the product in the slot.
     * @return The fat content of the product in the slot.
     */
    public double getFat() {
        return fat;
    }
}
