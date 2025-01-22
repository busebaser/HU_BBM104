import java.io.IOException;
import java.util.*;

public class Machine {


    /**
     * This method iterates through the list where each product and its attributes are listed side by side, cycling through as many times as there are product names,
     * and sets the attributes sequentially.
     *
     * @param path The path of the file containing product attributes.
     * @return An ArrayList containing the created Product objects.
     */
    public ArrayList<Product> creatingProducts(String path) {
        for (int i = 0; i < IO.getProductAttributes(path).size() / 5; i++) {
            Product product = new Product();
            product.setName(IO.getProductAttributes(path).get(5 * i));
            product.setPrice(Integer.parseInt(IO.getProductAttributes(path).get(5 * i + 1)));
            product.setProProtein(Double.parseDouble(IO.getProductAttributes(path).get(5 * i + 2)));
            product.setProCarbo(Double.parseDouble(IO.getProductAttributes(path).get(5 * i + 3)));
            product.setProFat(Double.parseDouble(IO.getProductAttributes(path).get(5 * i + 4)));

            product.setCalorie((int) (4 * product.getProProtein() + 4 * product.getProCarbo() + 9 * product.getProFat() + 0.5));

            Product.addProductToProductList(product);
        }
        return Product.getProductList();
    }

    /**
     * This method defines 24 new slots for the machine and sets each one to its initial empty state before any product arrives.
     * @return It returns a list containing 24 slots.
     */
    public Slot[] creatingSlots() {
        Slot[] slots = new Slot[24];
        for (int i = 0; i < 24; i++) {
            boolean empty = true;
            String slotProduct = "___";
            int amount = 0;
            int calorie = 0;
            slots[i] = new Slot(empty, slotProduct, amount, calorie);
        }
        return slots;
    }

    /**
     * This method checks whether all slots in the machine are full or not.
     * @param slots The array that represents the list containing the slots.
     * @return If there are still partially filled slots, it returns 0; if all slots are filled, it returns -1.
     */

    public int fill(Slot[] slots) {
        for (Slot slot : slots) {
            if (slot.is_empty()) { // aralarında herhangi bir dolmamış var ise
                return 0;
            }
        }
        return -1;
    }

    /**
     * This method designates the array of 24 slots that have been created as the slots currently being processed by the machine.
     */
    public Slot[] current_slots = creatingSlots();

    /**
     * This method starts filling the machine's slots with incoming products sequentially. If there is not enough space in the machine for that product, it prints a warning message.
     * @param slotProduct_name The product that has not yet been placed into the machine and is initiated for processing.
     * @return If all slots in the machine are not fully occupied, it returns 0; if all slots are completely filled, it returns -1.
     * @throws IOException
     */
    public int fillingSlots(String slotProduct_name) throws IOException {
        Product product = new Product();
        for (int i = 0; i < 24; i++) {
            if ((Objects.equals(current_slots[i].getSlotProduct(), slotProduct_name)) && current_slots[i].is_empty()) {
                current_slots[i].increaseAmount();
                if (current_slots[i].getAmount() == 10) {
                    current_slots[i].setEmpty(false);

                }
                product.setInMachine(true);
                break;

            } else if (Objects.equals(current_slots[i].getSlotProduct(), "___") && current_slots[i].getAmount() == 0) {
                current_slots[i].setSlotProduct(slotProduct_name);
                current_slots[i].increaseAmount();
                product.setInMachine(true);
                break;
            }
        }
        if ((!product.getInMachine() && fill(current_slots) == 0)) {
            IO.bw.write("INFO: There is no available place to put " + slotProduct_name + "\n");
        }
        else if((!product.getInMachine() && fill(current_slots) == -1)){
            IO.bw.write("INFO: There is no available place to put " + slotProduct_name + "\n");
            return -1;
        }
        return 0;
    }

    /**
     * This method sets the calorie, carbohydrate, protein, fat, and price attributes of each product placed in the machine's slots from the list of product attributes.
     * @param slot The array containing the slots of the machine
     * @param productList The list containing the products.
     */
    public void attributesMatch(Slot[] slot, ArrayList<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < slot.length; j++) {
                if (Objects.equals(productList.get(i).getName(), slot[j].getSlotProduct())) {
                    slot[j].setSlotCalorie(productList.get(i).getCalorie());
                    slot[j].setPrice(productList.get(i).getPrice());
                    slot[j].setCarb(productList.get(i).getProCarbo());
                    slot[j].setProtein(productList.get(i).getProProtein());
                    slot[j].setFat(productList.get(i).getProFat());
                }
            }
        }
    }

    /**
     * This method iterates through the requested number of products. First, it checks whether the money deposited by the user is acceptable by the machine. Then, it distinguishes between whether the user chooses to make a selection from any slot or based on their desired nutritional value.
     * If the user selects a slot, it checks if there is a product in that slot and if the money deposited is sufficient for the selected product. If the user selects a nutritional value, it checks if the first product within the +5 -5 range of that nutritional value exists.
     * If the conditions are not met, warning messages are displayed, and the money is refunded. If the conditions are met, the product is given to the user, and the product is deducted from the slot.
     * At the end of the function, if there are no products left in a slot, the values of that slot are reset.
     * Since it is uncertain whether the type of the desired nutrient value specified by the user is an integer or a double, the round method from the Math class is used to obtain an approximate value in calculations.
     * @param purchases The array containing the requested products in sequence.
     * @param content The string array containing the price, attributes, or position of the requested products.
     * @throws IOException
     */

    public void shopping(Purchase[] purchases,String[] content) throws IOException {
        for (int i = 0; i < purchases.length; i++) {
            IO.bw.write("INPUT: " + content[i] + "\n");
            if (!purchases[i].isSuitable()) {
                IO.bw.write("INFO: Number cannot be accepted. Please try again with another number.\n");
                IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
            } else {
                if (Objects.equals(purchases[i].getType(), "NUMBER")) {
                    if(purchases[i].getValue() > 24 || purchases[i].getValue() < 0){
                        IO.bw.write("INFO: Number cannot be accepted. Please try again with another number.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                    else{
                        if (current_slots[purchases[i].getValue()].getAmount()== 0) {
                            IO.bw.write("INFO: This slot is empty, your money will be returned.\n");
                            IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                        } else {
                            if (purchases[i].getMoney() >= current_slots[purchases[i].getValue()].getPrice()) {
                                IO.bw.write("PURCHASE: You have bought one " + current_slots[purchases[i].getValue()].getSlotProduct() + "\n");
                                IO.bw.write("RETURN: Returning your change: " + (purchases[i].getMoney() - current_slots[purchases[i].getValue()].getPrice()) + " TL\n");
                                current_slots[purchases[i].getValue()].decreaseAmount();
                            }else {
                                IO.bw.write("INFO: Insufficient money, try again with more money.\n");
                                IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                            }
                        }
                    }
                } else if (Objects.equals(purchases[i].getType(), "CARB")) {
                    if(suitableCarb(Math.round(purchases[i].getValue()))== -1){
                        IO.bw.write("INFO: Product not found, your money will be returned.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                    else if (purchases[i].getMoney() >= current_slots[suitableCarb(Math.round(purchases[i].getValue()))].getPrice()) {
                        IO.bw.write("PURCHASE: You have bought one " + current_slots[suitableCarb(Math.round(purchases[i].getValue()))].getSlotProduct() + "\n");
                        IO.bw.write("RETURN: Returning your change: " + (purchases[i].getMoney() - current_slots[suitableCarb(Math.round(purchases[i].getValue()))].getPrice()) + " TL\n");
                        current_slots[suitableCarb(Math.round(purchases[i].getValue()))].decreaseAmount();

                    } else {
                        IO.bw.write("INFO: Insufficient money, try again with more money.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                } else if(Objects.equals(purchases[i].getType(), "PROTEIN")){
                    if(suitableProtein(Math.round(purchases[i].getValue()))== -1){
                        IO.bw.write("INFO: Product not found, your money will be returned.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                    else if(purchases[i].getMoney() >= current_slots[suitableProtein(Math.round(purchases[i].getValue()))].getPrice()){
                        IO.bw.write("PURCHASE: You have bought one " + current_slots[suitableProtein(Math.round(purchases[i].getValue()))].getSlotProduct() + "\n");
                        IO.bw.write("RETURN: Returning your change: " + (purchases[i].getMoney() - current_slots[suitableProtein(Math.round(purchases[i].getValue()))].getPrice()) + " TL\n");
                        current_slots[suitableProtein(Math.round(purchases[i].getValue()))].decreaseAmount();

                    }else{
                        IO.bw.write("INFO: Insufficient money, try again with more money.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                }else if(Objects.equals(purchases[i].getType(), "FAT")) {
                    if(suitableFat(purchases[i].getValue())== -1){
                        IO.bw.write("INFO: Product not found, your money will be returned.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");

                    }else if(purchases[i].getMoney() >= current_slots[suitableFat(Math.round(purchases[i].getValue()))].getPrice()){
                        IO.bw.write("PURCHASE: You have bought one " + current_slots[suitableFat(Math.round(purchases[i].getValue()))].getSlotProduct() + "\n");
                        IO.bw.write("RETURN: Returning your change: " + (purchases[i].getMoney() - current_slots[suitableFat(Math.round(purchases[i].getValue()))].getPrice()) + " TL\n");
                        current_slots[suitableFat(Math.round(purchases[i].getValue()))].decreaseAmount();

                    }else{
                        IO.bw.write("INFO: Insufficient money, try again with more money.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + "\n");
                    }
                }else{
                    if(suitableCalorie(purchases[i].getValue())== -1){
                        IO.bw.write("INFO: Product not found, your money will be returned.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + "\n");
                    }
                    else if(purchases[i].getMoney() >= current_slots[suitableCalorie(purchases[i].getValue())].getPrice()){
                        IO.bw.write("PURCHASE: You have bought one " + current_slots[suitableCalorie(purchases[i].getValue())].getSlotProduct() + "\n");
                        IO.bw.write("RETURN: Returning your change: " + (purchases[i].getMoney() - current_slots[suitableCalorie(purchases[i].getValue())].getPrice()) + " TL\n");
                        current_slots[suitableCalorie(purchases[i].getValue())].decreaseAmount();

                    }else{
                        IO.bw.write("INFO: Insufficient money, try again with more money.\n");
                        IO.bw.write("RETURN: Returning your change: " + purchases[i].getMoney() + " TL\n");
                    }
                }
            }
        }
        for(int i = 0; i<24; i++){
            if(current_slots[i].getAmount() == 0){
                current_slots[i].setSlotCalorie(0);
                current_slots[i].setSlotProduct("___");
            }
        }
    }

    /**
     * It finds the slot position of the first product available in the slot within the range of +5 -5 of the desired nutritional value specified by the user.
     * @param sayı The desired carbohydrate nutritional value specified by the user.
     * @return If the product is available in the slot, it returns the index of that slot; otherwise, it returns -1.
     */
    public int suitableCarb(int sayı){
        for(int i = 0; i<24; i++){
            if(current_slots[i].getCarb() <= sayı + 5 && current_slots[i].getCarb() >= sayı-5 && current_slots[i].getAmount()>0){
                return i;
            }
        }
        return -1;
    }

    /**
     * It finds the slot position of the first product available in the slot within the range of +5 -5 of the desired nutritional value specified by the user.
     * @param sayı The desired protein nutritional value specified by the user.
     * @return If the product is available in the slot, it returns the index of that slot; otherwise, it returns -1.
     */
    public int suitableProtein(int sayı){
        for(int i = 0; i<24; i++){
            if(current_slots[i].getProtein() <= sayı + 5 && current_slots[i].getProtein() >= sayı-5 && current_slots[i].getAmount()>0){
                return i;
                }
        }
        return -1;
    }
    /**
     * It finds the slot position of the first product available in the slot within the range of +5 -5 of the desired nutritional value specified by the user.
     * @param sayı The desired fat nutritional value specified by the user.
     * @return If the product is available in the slot, it returns the index of that slot; otherwise, it returns -1.
     */
    public int suitableFat(int sayı) {
        for (int i = 0; i < 24; i++) {
            if (current_slots[i].getFat() <= sayı + 5 && current_slots[i].getFat() >= sayı - 5 && current_slots[i].getAmount()>0) {
                return i;
            }
        }
        return -1;
    }
    /**
     * It finds the slot position of the first product available in the slot within the range of +5 -5 of the desired nutritional value specified by the user.
     * @param sayı The desired calorie value specified by the user.
     * @return If the product is available in the slot, it returns the index of that slot; otherwise, it returns -1.
     */
    public int suitableCalorie(int sayı){
        for(int i = 0; i<24; i++){
            if(current_slots[i].getSlotCalorie() <= sayı + 5 && current_slots[i].getSlotCalorie() >= sayı-5 && current_slots[i].getAmount()>0){
                return i;
            }
        }
        return -1;

    }
}
