public class Minibus extends BusManager{
    /**
     * This class represents a minibus, which is a type of bus with a specific layout and characteristics.
     */
    private final String type = "minibus";
    private final String layout = "(2)";

    private String id;
    private String origin;
    private String destination;
    private int rows;
    private double price;
    private double revenue;

    /**
     * Constructor for Minibus class.
     * @param id The ID of the minibus.
     * @param origin The origin of the minibus.
     * @param destination The destination of the minibus.
     * @param rows The number of rows in the minibus.
     * @param price The price of a ticket for the minibus.
     */
    public Minibus(String id, String origin, String destination, int rows, double price){
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.rows = rows;
        this.price = price;
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
    public String getId(){
        return this.id;
    }
    public String getOrigin(){
        return this.origin;
    }
    public String getDestination(){
        return this.destination;
    }
    public int getRows(){
        return this.rows;
    }
    public double getPrice(){
        return this.price;
    }

    public void increaseRevenue(double add){  // Increases revenue by the specified amount.
        this.revenue += add;
    }
    public void decreaseRevenue(double decreaseValue){ // Decreases revenue by the specified amount.
        this.revenue -= decreaseValue;
    }

    /**
     * ince there are no refunds for minibuses, it returns 0.
     * @param seats Seats to be refunded.
     * @return 0.
     */
    @Override
    public double getTotalRefundAmount(String seats) {
        return 0;
    }


    /**
     * Creates an array by multiplying the number of rows of the bus by 2, as there are 2 seats per row, and represents all seats as empty using the '*' symbol.
     * @return An array of strings representing each seat.
     */
    public String[] creatingSeats() {
        String[] currentSeats = new String[getRows() *2];
        for(int i = 0; i < getRows() * 2; i++){
            currentSeats[i] = "*";
        }
        return currentSeats;
    }


    /**
     * Returns a string containing the seating arrangement with 2 seats per row and information about the voyage.
     * @return The String containing the seating arrangement and information about the voyage.
     */
    public String showSeatsLayout(String[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i+= 2){
            sb.append(arr[i]).append(" ").append(arr[i + 1]).append("\n");
        }
        return String.format("Voyage %s\n%s-%s\n",getId(),getOrigin(),getDestination()) + sb + String.format("Revenue: %s", byFormat(getRevenue())).replace(",", ".") + "\n";

    }

    /**
     * Generates a string containing information about the voyage initialization for the minibus.
     * @param id The ID of the minibus.
     * @param type The type of the minibus.
     * @param layout The layout of the minibus.
     * @param to The origin of the minibus.
     * @param dest The destination of the minibus.
     * @param rows The number of rows in the minibus.
     * @param price The price of a ticket for the minibus.
     * @return A string containing information about the voyage initialization.
     */
    public String initvoyage_toString(String id, String type, String layout, String to, String dest,int rows,double price) {
        return super.initvoyage_toString(id,type,layout,to,dest,rows,price) + " Note that minibus tickets are not refundable.\n";
    }


    /**
     * Since there are no refunds for minibuses, it returns null.
     * @param seats Seats to be refunded.
     * @return null.
     */
    public String refundTicket_toString(String seats){
        return null;
    }

    /**
     * Since there are no refunds for minibuses, it returns null.
     * @param seatsArr Current seating arrangement.
     * @param seats Seats to be refunded.
     * @return
     */
    public String[] getRefundedSeating(String[] seatsArr, String seats){
        return null;
    }

}
