import java.io.File;
import java.util.*;

public class BookingSystem {
    public static void main(String[] args) {
        // The check of the number of arguments.
        if(args.length != 2){
            System.out.println("\"ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \\\"java8 BookingSystem input.txt output.txt\\\". Program is going to terminate!\"");
            return;
        }

        // Get input and output file paths from command line arguments
        String inputFilePath = args[0];
        String outputFilePath = args[1];

        // Check the write permission to the input file.
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.out.println("ERROR: This program cannot read from the \"" + inputFilePath + "\", either this program does not have read permission to read that file or file does not exist. Program is going to terminate!");
            return;
        }

        // Check the write permission to the output file.
        File outputFile = new File(outputFilePath);
        if (!outputFile.exists() || !outputFile.isFile()) {
            System.out.println("ERROR: This program cannot write to the \"" + outputFilePath + "\", please check the permissions to write that directory. Program is going to terminate!");
            return;
        }

        // Initialize the system controller
        SystemController systemController = new SystemController();

        // An array consisting of strings that hold both the ID and the total number of seats for a bus.
        // For example, for a bus with an ID of 10 and a total seat count of 60, the string '10,60' would be found.
        ArrayList<String> busID_seatsArray = new ArrayList<>();
        BusManager[] buses = new BusManager[0];

        // Process each line of input
        for(String[] line : IO.readFile(inputFilePath)){

            IO.writeToFile(outputFilePath,"COMMAND: " + printArrayAsString(line).trim() + "\n");

            // Check for errors in the input line
            if(Objects.equals(systemController.errorDetection(line, busID_seatsArray), "NoError")){
                if(Objects.equals(line[0], "INIT_VOYAGE")){
                    // Initialize a new voyage based on the provided parameters
                    // Create the corresponding bus type and add it to the array of buses
                    // Write the initialization details to the output file
                    if(line.length == 7){
                        Minibus minibus = new Minibus(line[2],line[3],line[4],Integer.parseInt(line[5]),Double.parseDouble(line[6]));
                        buses = BusManager.addBus(buses,minibus);
                        minibus.seats = minibus.creatingSeats();
                        busID_seatsArray.add(String.format("%s,%d",minibus.getId(),minibus.getRows()*2));
                        IO.writeToFile(outputFilePath,minibus.initvoyage_toString(line[2],minibus.getType(),minibus.getLayout(),line[3],line[4], minibus.getRows(), minibus.getPrice()));
                    }
                    if(line.length == 8){
                        StandardBus standartBus = new StandardBus(line[2],line[3],line[4],Integer.parseInt(line[5]),Double.parseDouble(line[6]),Integer.parseInt(line[7]));
                        buses = BusManager.addBus(buses,standartBus);
                        standartBus.seats = standartBus.creatingSeats();
                        busID_seatsArray.add(String.format("%s,%d",standartBus.getId(),standartBus.getRows()*4));
                        IO.writeToFile(outputFilePath,standartBus.initvoyage_toString(line[2],standartBus.getType(),standartBus.getLayout(),line[3],line[4], standartBus.getRows(), standartBus.getPrice()));
                    }
                    if(line.length == 9){
                        PremiumBus premiumBus = new PremiumBus(line[2],line[3],line[4],Integer.parseInt(line[5]),Double.parseDouble(line[6]),Integer.parseInt(line[7]),Integer.parseInt(line[8]));
                        buses = BusManager.addBus(buses,premiumBus);
                        premiumBus.seats = premiumBus.creatingSeats();
                        busID_seatsArray.add(String.format("%s,%d",premiumBus.getId(),premiumBus.getRows()*3));
                        IO.writeToFile(outputFilePath, premiumBus.initvoyage_toString());
                    }
                }
                if(Objects.equals(line[0], "Z_REPORT")){
                    if(buses.length == 0){
                        IO.writeToFile(outputFilePath,"Z Report:\n" +
                                "----------------\n" +
                                "No Voyages Available!\n" +
                                "----------------\n");
                    }else{
                        IO.writeToFile(outputFilePath,"Z Report:\n" + "----------------\n");
                        Arrays.sort(buses, (o1, o2) -> Integer.compare(Integer.parseInt(o1.getId()),Integer.parseInt(o2.getId())));   // It sorts the Bus objects in ascending order based on their IDs.

                        for(BusManager bus : buses){
                            IO.writeToFile(outputFilePath,bus.showSeatsLayout(bus.seats));
                            IO.writeToFile(outputFilePath,"----------------\n");

                        }
                    }
                }
                if(Objects.equals(line[0], "CANCEL_VOYAGE")){
                    BusManager busToRemove = BusManager.findBusById(buses,line[1]);
                    IO.writeToFile(outputFilePath,String.format("Voyage %s was successfully cancelled!\nVoyage details can be found below:\n",line[1]));
                    assert busToRemove != null;
                    busToRemove.decreaseRevenue(busToRemove.getCancellationDecrease(busToRemove.seats));
                    IO.writeToFile(outputFilePath,busToRemove.showSeatsLayout(busToRemove.seats));
                    buses = BusManager.removeBus(buses,busToRemove);

                    Iterator<String> iterator = busID_seatsArray.iterator();
                    while (iterator.hasNext()) {
                        String busID_seatsCount = iterator.next();
                        String[] parts = busID_seatsCount.split(",");
                        String busID = (parts[0]);
                        if (Objects.equals(busID, line[1])) { // If the ID before the comma is equal to the given ID, this string is removed from the list.
                            iterator.remove();
                        }
                    }
                }
                if(Objects.equals(line[0], "PRINT_VOYAGE")){
                    BusManager busToSee = BusManager.findBusById(buses,line[1]);
                    IO.writeToFile(outputFilePath, busToSee.showSeatsLayout(busToSee.seats));
                }

                if(Objects.equals(line[0], "SELL_TICKET")){
                    boolean empty = true;
                    BusManager busToSellTicket = BusManager.findBusById(buses,line[1]);
                    int[] intArray = systemController.seatNumberOrganizer(line[2]);
                    for (int j : intArray) { // It checks whether the seats are empty or not.
                        if(j != 0){
                            if (!Objects.equals(busToSellTicket.seats[j - 1], "*")) {
                                empty = false;
                            }
                        }
                    }
                    if(!empty){
                        IO.writeToFile(outputFilePath,"ERROR: One or more seats already sold!\n");
                    }else{
                        assert busToSellTicket != null;
                        busToSellTicket.seats = busToSellTicket.getSoldSeating(busToSellTicket.seats, line[2]);
                        busToSellTicket.increaseRevenue(busToSellTicket.getTotalSalesRevenue(line[2]));

                        IO.writeToFile(outputFilePath,busToSellTicket.sellTicket_toString(busToSellTicket.getId(), line[2], busToSellTicket.getOrigin(), busToSellTicket.getDestination(), busToSellTicket.getPrice()));
                    }
                }

                if(Objects.equals(line[0], "REFUND_TICKET")){
                    boolean full = true;
                    BusManager busToRefundTicket = BusManager.findBusById(buses,line[1]);
                    int[] intArray = systemController.seatNumberOrganizer(line[2]);
                    for(int i : intArray){ // It checks whether the seats are full or not.
                        if(i != 0){
                            if(!Objects.equals(busToRefundTicket.seats[i - 1], "X")){
                                full = false;
                            }
                        }
                    }
                    if(!full){
                        IO.writeToFile(outputFilePath,"ERROR: One or more seats are already empty!\n");
                    }else{
                        if(busToRefundTicket.refundTicket_toString(line[2]) == null){
                            IO.writeToFile(outputFilePath,"ERROR: Minibus tickets are not refundable!\n");
                        }else{
                            busToRefundTicket.seats = busToRefundTicket.getRefundedSeating(busToRefundTicket.seats,line[2]);
                            busToRefundTicket.decreaseRevenue(busToRefundTicket.getTotalRefundAmount(line[2]));
                            IO.writeToFile(outputFilePath,busToRefundTicket.refundTicket_toString(line[2]));
                        }
                    }
                }

            }else{
                // Write the error message to the output file
                IO.writeToFile(outputFilePath, systemController.errorDetection(line,busID_seatsArray));

            }

        }
        // If the last line does not contain the Z report code, it prints the Z report.
        if(!IO.isLastZReport(inputFilePath)){
            if(buses.length == 0){
                IO.writeToFile(outputFilePath,"Z Report:\n" +
                        "----------------\n" +
                        "No Voyages Available!\n" +
                        "----------------\n");
            }else{
                IO.writeToFile(outputFilePath,"Z Report:\n" + "----------------\n");
                Arrays.sort(buses, (o1, o2) -> Integer.compare(Integer.parseInt(o1.getId()),Integer.parseInt(o2.getId())));   // It sorts the Bus objects in ascending order based on their IDs.

                for(BusManager bus : buses){
                    IO.writeToFile(outputFilePath,bus.showSeatsLayout(bus.seats));
                    IO.writeToFile(outputFilePath,"----------------\n");

                }
            }
        }

        // Remove the last newline character from the output file
        IO.removeLastNewLine(outputFilePath);


    }

    // Utility method to print an array as a string
    public static String printArrayAsString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s).append("\t");
        }
        return sb.toString();

    }

}
