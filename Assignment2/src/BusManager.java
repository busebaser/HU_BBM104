import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Abstract class to manage buses.
 */
public abstract class BusManager {
    // Properties
    private String id;
    private static double price;
    private String origin;
    private String destination;
    private int rows;
    private  double revenue;

    public String[] seats;

    //Getters for properties
    public int getRows() {
        return rows;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }
    public double getRevenue() {
        return revenue;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public abstract void increaseRevenue(double add);  // Increases revenue by the specified amount.
    public abstract void decreaseRevenue(double decreaseValue); // Decreases revenue by the specified amount.

    /**
     * Creates the seating arrangement.
     * @return Array representing the seating arrangement.
     */
    public abstract String[] creatingSeats();

    /**
     * Shows the layout of seats.
     * @param arr Array representing the seating arrangement.
     * @return Layout of seats.
     */
    public abstract String showSeatsLayout(String[] arr);


    /**
     * Returns the text that should be displayed on the screen when a ticket is refunded as a String.
     * @param seats Seats to be refunded.
     * @return String representation of refunded ticket.
     */

    public abstract String refundTicket_toString(String seats);

    /**
     * After a ticket refund is made, it returns the seating arrangement of the bus.
     * @param seatsArr Current seating arrangement.
     * @param seats Seats to be refunded.
     * @return Updated seating arrangement after refund.
     */
    public abstract String[] getRefundedSeating(String[] seatsArr, String seats);


    /**
     * Calculates the total refund amount for the given seats.
     * @param seats Seats to be refunded.
     * @return Total refund amount.
     */
    public abstract double getTotalRefundAmount(String seats);

    /**
     * Initializes a voyage and returns a formatted string.
     * @param id Voyage ID.
     * @param type Bus type.
     * @param layout Layout of seats.
     * @param to Destination.
     * @param dest Destination.
     * @param rows Number of rows.
     * @param price Ticket price.
     * @return Formatted string representing the initialized voyage.
     */
    public String initvoyage_toString(String id, String type, String layout, String to, String dest, int rows, double price){
        int lout;
        if(layout.length() == 3){
            lout = Character.getNumericValue(layout.charAt(1));
        }
        else{
            lout =  Character.getNumericValue(layout.charAt(1)) + Character.getNumericValue(layout.charAt(3));
        }

        return String.format("Voyage %s was initialized as a %s %s voyage from %s to %s with %s TL priced %d regular seats.",id,type,layout,to,dest, byFormat(price),lout*rows).replace(",",".");
    }


    /**
     * It calculates the amount of money that needs to be refunded without any deduction for the occupied seats in order to decrease the total revenue of the bus after cancellation.
     * @param seats The seats of the bus to be cancelled.
     * @return Decrease in revenue.
     */
    public double getCancellationDecrease(String[] seats){
        double count = 0;
        for(String i : seats){
            if(Objects.equals(i, "X")){
                count++;
            }
        }
        return count * getPrice();
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
        String[] seatNumbers = seats.split("_");
        double total  = seatNumbers.length * price;

        return String.format("Seat %s of the Voyage %s from %s to %s was successfully sold for %s TL.\n",seats.replace("_","-"),id,to,dest, byFormat(total)).replace(",",".");
    }

    /**
     * Returns the String array containing the seats after a ticket/tickets sale is made. Occupied seats are represented by 'X'.
     * @param seatsArr Current seating arrangement.
     * @param seats Seats sold.
     * @return Updated seating arrangement after selling tickets.
     */
    public String[] getSoldSeating(String[] seatsArr, String seats){
        int seatNumber;
        if(seats.length() ==1){
            seatNumber = Integer.parseInt(seats);
            seatsArr[seatNumber-1] = "X"; // Since seat numbers start from 1, subtract 1 to convert to index.

        }else{
            String[] strArray = seats.split("_");
            int[] intArray = new int[strArray.length];
            for (int i =0; i < intArray.length; i++){
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            for (int j : intArray) {
                seatsArr[j - 1] = "X";
            }
        }
        return seatsArr;
    }

    /**
     * Calculates the total revenue after ticket sales.
     * @param seats Seats string representing the sold tickets.
     * @return Total revenue after ticket sales.
     */
    public double getTotalSalesRevenue(String seats){
        String[] strArray = seats.split("_");
        return getPrice() * strArray.length;
    }

    /**
     * Finds a bus by its ID.
     * @param buses Array of buses to search within.
     * @param id ID of the bus to find.
     * @return The bus object with the specified ID, or null if not found.
     */
    public static BusManager findBusById(BusManager[] buses, String id){
        for(BusManager bus : buses){
            if(Objects.equals(bus.getId(), id)){
                return bus;
            }
        }
        return null;
    }

    /**
     * Adds a bus to an array of buses.
     * @param array Array of buses to add to.
     * @param busToAdd The bus object to add.
     * @return New array of buses with the added bus.
     */
    public static BusManager[] addBus(BusManager[] array, BusManager busToAdd) {
        BusManager[] newArray = new BusManager[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[newArray.length - 1] = busToAdd;
        return newArray;
    }

    /**
     * Removes a bus from an array of buses.
     * @param array Array of buses to remove from.
     * @param busToRemove The bus object to remove.
     * @return New array of buses with the removed bus.
     */
    public static BusManager[] removeBus(BusManager[] array, BusManager busToRemove) {
        int indexToRemove = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(busToRemove)) {
                indexToRemove = i;
                break;
            }
        }
        BusManager[] newArray = new BusManager[array.length - 1];

        for (int i = 0; i < indexToRemove; i++) {
            newArray[i] = array[i];
        }

        for (int i = indexToRemove + 1; i < array.length; i++) {
            newArray[i - 1] = array[i];
        }
        return newArray;
    }

    /**
     * Formats a number to a string with two decimal places.
     * @param num Number to format.
     * @return Formatted string.
     */
    public static String byFormat(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }
}
