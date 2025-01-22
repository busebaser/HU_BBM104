import java.util.ArrayList;

public class Product {
    /**
     * This class represents a product with its attributes such as name, price, calorie, carbohydrate, fat, and protein content.
     * It also keeps track of whether the product is currently in the machine.
     */
    private boolean inMachine;
    private String name;
    private int price;
    private int calorie;
    private double proCarbo;
    private double proFat;
    private double proProtein;
    private static ArrayList<Product> productList = new ArrayList<>();

    /**
     * Adds a product to the product list.
     * @param product The product to add to the list.
     */
    public static void addProductToProductList(Product product){
        productList.add(product);
    }

    /**
     * Retrieves the product list.
     * @return The list of products.
     */
    public static ArrayList<Product> getProductList(){
        return productList;
    }

    /**
     * Sets whether the product is currently in the machine.
     * @param inMachine true if the product is in the machine, false otherwise.
     */
    public void setInMachine(boolean inMachine){
        this.inMachine = inMachine;
    }

    /**
     * Retrieves whether the product is currently in the machine.
     * @return true if the product is in the machine, false otherwise.
     */
    public boolean getInMachine(){
        return inMachine;
    }

    /**
     * Sets the name of the product.
     * @param name The name of the product.
     */

    public void setName(String name){
        this.name = name;
    }

    /**
     * Retrieves the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the price of the product.
     * @param price The price of the product.
     */
    public void setPrice(int price){
        this.price = price;
    }
    /**
     * Retrieves the price of the product.
     * @return The price of the product.
     */
    public int getPrice() {
        return price;
    }
    /**
     * Sets the calorie content of the product.
     * @param calorie The calorie content of the product.
     */
    public void setCalorie(int calorie){
        this.calorie = calorie;
    }
    /**
     * Retrieves the calorie content of the product.
     * @return The calorie content of the product.
     */
    public int getCalorie(){
        return calorie;
    }
    /**
     * Sets the carbohydrate content of the product.
     * @param proCarbo The carbohydrate content of the product.
     */
    public void setProCarbo(double proCarbo){
        this.proCarbo = proCarbo;
    }
    /**
     * Sets the fat content of the product.
     * @param proFat The fat content of the product.
     */
    public void setProFat(double proFat){
        this.proFat = proFat;
    }
    /**
     * Sets the protein content of the product.
     * @param proProtein The protein content of the product.
     */
    public void setProProtein(double proProtein){
        this.proProtein = proProtein;
    }
    /**
     * Retrieves the carbohydrate content of the product.
     * @return The carbohydrate content of the product.
     */
    public double getProCarbo(){
        return proCarbo;
    }
    /**
     * Retrieves the fat content of the product.
     * @return The fat content of the product.
     */
    public double getProFat(){
        return proFat;
    }
    /**
     * Retrieves the protein content of the product.
     * @return The protein content of the product.
     */
    public double getProProtein(){
        return proProtein;
    }

}
