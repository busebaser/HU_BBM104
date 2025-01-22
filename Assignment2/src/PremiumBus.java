import java.util.Objects;
/**
 * Subclass representing a premium bus with its properties and specific methods.
 */
public class PremiumBus extends BusManager{
    private final String type = "premium";
    private final String layout = "(1+2)";
    private String id;
    private String origin;
    private String destination;
    private int rows;
    private double price;
    private int refund_cut;
    private int premium_fee;
    private double revenue;

    /**
     * Constructor for PremiumBus class.
     */
    public PremiumBus(String id, String to, String dest, int rows, double price, int refund_cut, int premium_fee){
        this.id = id;
        this.origin = to;
        this.destination = dest;
        this.rows = rows;
        this.price = price;
        this.refund_cut = refund_cut;
        this.premium_fee = premium_fee;
    }

    @Override
    public double getRevenue() {
        return revenue;
    }
    public String getType() {
        return type;
    }
    public String getLayout() {
        return layout;
    }
    public String getId() {
        return id;
    }
    public String getOrigin() {
        return origin;
    }
    public String getDestination() {
        return destination;
    }
    public int getRows() {
        return rows;
    }
    public double getPrice() {
        return price;
    }
    public int getRefund_cut() {
        return refund_cut;
    }
    public double getPremium_fee() {
        return premium_fee;
    }

    public void increaseRevenue(double add){
        this.revenue += add;
    }
    public void decreaseRevenue(double decreaseValue){
        this.revenue -= decreaseValue;
    }


    /**
     * Creates an array by multiplying the number of rows of the bus by 3, as there are 3 seats per row, and represents all seats as empty using the '*' symbol.
     * @return An array of strings representing each seat.
     */
    public String[] creatingSeats() {
        String[] currentSeats = new String[getRows() * 3];
        for(int i = 0; i < currentSeats.length; i++){
            currentSeats[i] = "*";
        }
        return currentSeats;
    }

    /**
     * Returns a string containing the seating arrangement with 4 seats per row and information about the voyage.
     * Due to its premium status, it indicates that there is one seat on the left side and two seats on the right side per row, resulting in three seats per row in total."
     * @return The String containing the seating arrangement and information about the voyage.
     */
    public String showSeatsLayout(String[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getRows() *3 - 2; i+= 3){
            sb.append(arr[i]).append(" | ").append(arr[i+1]).append(" ").append(arr[i+2]).append("\n");
        }
        return String.format("Voyage %s\n%s-%s\n",getId(),getOrigin(),getDestination()) + sb + String.format("Revenue: %s", byFormat(getRevenue())).replace(",", ".") + "\n";

    }

    /**
     * It has been created to facilitate the conversion of seat numbers from String to integer in each method, as the seat numbers are provided as a String from the input file.
     * @param seats The string array containing seat numbers.
     * @return An integer array containing seat numbers.
     */
    public int[] seatNumbersOrganizer(String seats){
        String[] strArray = seats.split("_");
        int[] intArray = new int[strArray.length];
        for(int i=0; i < strArray.length; i++){
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    /**
     * This method calculates the ticket price based on whether the seat is premium or standard.
     * For premium seats, whose numbers are one more than a multiple of 3, it multiplies the price by the premium fee percentage, while for standard seats, it directly assigns the given price.
     * @param seatNumber Seat number.
     * @return The price of the seat.
     */

    public double premiumOrNormalPriceCalculation(int seatNumber){ // bi koltuğun numarasına göre fiyatını hesaplıyor.
        double updatedprice;
        if(seatNumber % 3 == 1){
            updatedprice = (getPremium_fee() + 100) * getPrice() / 100;
            return updatedprice;
        }else{
            return getPrice();
        }
    }


    /**
     * Initializes a voyage and returns a formatted string.
     * @return Formatted string representing the initialized voyage.
     */
    public String initvoyage_toString(){
        return String.format("Voyage %s was initialized as a %s %s voyage from %s to %s with %s TL priced %d regular seats and %s TL priced %d premium seats. Note that refunds will be %d",getId(),getType(),getLayout(),getOrigin(),getDestination(), byFormat(getPrice()),getRows()*2, byFormat(getPrice() * (getPremium_fee()+100) / 100),getRows(),getRefund_cut()).replace(",",".") + "% less than the paid amount.\n";
    }

    /**
     * Returns the text containing information about the sale to be displayed on the screen after a ticket sale is made.
     * @param id Voyage ID.
     * @param seats Seats sold.
     * @param to Destination.
     * @param dest Destination.
     * @param price Ticket price.
     * @return The text containing information about the sale.
     */
    public String sellTicket_toString(String id, String seats,String to, String dest,double price){
        return String.format("Seat %s of the Voyage %s from %s to %s was successfully sold for %s TL.\n",seats.replace("_","-"),id,to,dest, byFormat(getTotalSalesRevenue(seats))).replace(",",".");

    }


    /**
     * Calculates the total sales revenue based on the provided seat numbers.
     * This method utilizes the seatNumbersOrganizer method to determine the price based on whether the seat is premium or standard.
     * @param seats A string containing seat numbers separated by underscores.
     * @return The total sales revenue calculated based on the provided seat numbers.
     */
    public double getTotalSalesRevenue(String seats){
        double total = 0;
        int[] intArray = seatNumbersOrganizer(seats);

        for(int i : intArray){
            total += premiumOrNormalPriceCalculation(i);
        }
        return total;
    }

    /**
     * Generates a string describing the refund process for the specified seats.
     * @param seats A string containing seat numbers separated by underscores.
     * @return A string describing the refund process for the specified seats.
     */
    public String refundTicket_toString(String seats){
        return String.format("Seat %s of the Voyage %s from %s to %s was successfully refunded for %s TL.\n",seats.replace("_","-"),getId(),getOrigin(),getDestination(), byFormat(getTotalRefundAmount(seats))).replace(",",".");

    }

    /**
     * Updates the seating arrangement array to mark(*) refunded seats as empty.
     * @param seatsArr The array representing the seating arrangement.
     * @param seats A string containing seat numbers separated by underscores.
     * @return The updated seating arrangement array after marking refunded seats as empty.
     */
    public String[] getRefundedSeating(String[] seatsArr, String seats){
        int[] intArray = seatNumbersOrganizer(seats);
        for(int seatNumber : intArray) {
            seatsArr[seatNumber - 1] = "*";
        }
        return seatsArr;
    }


    /**
     * Calculates the total refund amount for the specified seats.
     * @param seats A string containing seat numbers separated by underscores.
     * @return The total refund amount calculated for the specified seats.
     */
    @Override
    public double getTotalRefundAmount(String seats) {
        double total = 0;
        int[] intArray = seatNumbersOrganizer(seats);

        for(int seatnumber : intArray){
            total += premiumOrNormalPriceCalculation(seatnumber) * (100-getRefund_cut()) / 100;
        }
        return total;
    }


    /**
     * It calculates the amount of money that needs to be refunded without any deduction for the occupied seats in order to decrease the total revenue of the bus after cancellation.
     * This calculation takes into account whether the seats are premium or standard.
     * @param seats The seats of the bus to be cancelled.
     * @return Decrease in revenue.
     */
    @Override
    public double getCancellationDecrease(String[] seats) {
        double total = 0;
        for(int i =0; i < seats.length; i++){
            if(Objects.equals(seats[i], "X")){
                total += premiumOrNormalPriceCalculation(i+1);
            }
        }
        return total;
    }


}
