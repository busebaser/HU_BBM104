/**
 * Subclass containing the properties and specific methods of a standard bus.
 */
public class StandardBus extends BusManager{
    private final String type = "standard";
    private final String layout = "(2+2)";
    private String id;
    private String origin;
    private String destination;
    private int rows;
    private  double price;
    private int refund_cut;

    private double revenue;

    /**
     * Constructor for StandardBus class.
     * @param id          ID of the bus.
     * @param to          Origin of the bus.
     * @param dest        Destination of the bus.
     * @param rows        Number of rows of seats.
     * @param price       Price of a ticket.
     * @param refund_cut  Percentage of refund cut.
     */
    public StandardBus(String id, String to, String dest, int rows, double price, int refund_cut){
        this.id = id;
        this.origin = to;
        this.destination = dest;
        this.rows = rows;
        this.price = price;
        this.refund_cut = refund_cut;
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
    @Override
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


    public void increaseRevenue(double add){  // Increases revenue by the specified amount.
        this.revenue += add;
    }
    public void decreaseRevenue(double decreaseValue){ // Decreases revenue by the specified amount.
        this.revenue -= decreaseValue;
    }


    /**
     * Creates an array by multiplying the number of rows of the bus by 4, as there are 4 seats per row, and represents all seats as empty using the '*' symbol.
     * @return An array of strings representing each seat.
     */
    @Override
    public String[] creatingSeats() {
        String[] currentSeats = new String[getRows() * 4];
        for(int i = 0; i < getRows() * 4; i++){
            currentSeats[i] = "*";
        }
        return currentSeats;
    }

    /**
     * Returns a string containing the seating arrangement with 4 seats per row and information about the voyage.
     * @return The String containing the seating arrangement and information about the voyage.
     */
    public String showSeatsLayout(String[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i+= 4){
            sb.append(arr[i]).append(" ").append(arr[i + 1]).append(" | ").append(arr[i+2]).append(" ").append(arr[i+3]).append("\n");
        }
        return String.format("Voyage %s\n%s-%s\n",getId(),getOrigin(),getDestination()) + sb + String.format("Revenue: %s", byFormat(getRevenue())).replace(",",".") + "\n";

    }

    /**
     * Generates a string representation of the initialized voyage.
     * @param id    Voyage ID.
     * @param type  Type of the voyage.
     * @param layout    Layout of the seats.
     * @param to    Origin of the voyage.
     * @param dest  Destination of the voyage.
     * @param rows  Number of rows.
     * @param price Price of a ticket.
     * @return Formatted string representing the initialized voyage.
     */
    public String initvoyage_toString(String id, String type, String layout, String to, String dest, int rows, double price) {
        return super.initvoyage_toString(id,type,layout,to,dest,rows,price) + String.format(" Note that refunds will be %d",getRefund_cut()).replace(",", ".") + "% less than the paid amount.\n";
    }


    /**
     * Calculates the total refund amount for the seats to be refunded.
     * After splitting the string representing the seat numbers to find the count, it multiplies this count by the refund price of a ticket.
     * @param seats Seats to be refunded.
     * @return The total refund amount for the seats to be refunded
     */
    @Override
    public double getTotalRefundAmount(String seats) {
        int seatsCount = seats.split("_").length;
        return seatsCount * refundPrice();
    }


    /**
     * Calculates the price of a refunded ticket by using the refunded cut.
     * @return Price of a refunded ticket.
     */
    public double refundPrice(){
        return getPrice() - (getPrice() * getRefund_cut() / 100);
    }

    /**
     * Returns a String array representing the seating arrangement of the bus after the seats to be refunded are returned to their empty state.
     * It converts the seats to '*' to indicate that they are empty.
     * @param seatsArr Current seating arrangement.
     * @param seats Seats to be refunded.
     * @return Updated seating arrangement after refund.
     */
    public String[] getRefundedSeating(String[] seatsArr, String seats){
        String[] strArray = seats.split("_");
        int[] intArray = new int[strArray.length];
        for (int i =0; i < strArray.length; i++){
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        for(int seatNumber : intArray) {
            seatsArr[seatNumber - 1] = "*";
        }
        return seatsArr;
    }

    /**
     * Returns a string containing information about the ticket refund.
     * @param seats Seats to be refunded.
     * @return A string containing information about the ticket refund
     */
    public String refundTicket_toString(String seats){
        return String.format("Seat %s of the Voyage %s from %s to %s was successfully refunded for %s TL.\n",seats.replace("_","-"),getId(),getOrigin(),getDestination(), byFormat(getTotalRefundAmount(seats))).replace(",",".");
    }
}
