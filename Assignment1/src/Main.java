import java.io.IOException;
/**
 * The main class responsible for executing the vending machine simulation.
 */

public class Main {
    /**
     * The main method of the program.
     * @param args Command-line arguments: [0] for product input file, [1] for purchase input file, [2] for output file.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        String prodInputFile = args[0];
        String purcInputFile = args[1];
        String outputFile = args[2];

        // Initialize output file
        new IO(outputFile);

        // Create instance of the Machine class
        Machine machine = new Machine();

        // Iterating through the total number of products, it fills the machine with the products.
        // If all slots of the machine are filled, it provides a notification indicating that the machine is full and stops filling the machine.
        String[] slotProduct_name_list = IO.readandGetProduct(prodInputFile);
        for (int i = 0; i < slotProduct_name_list.length; i++){
            if(machine.fillingSlots(slotProduct_name_list[i]) == -1){
                IO.bw.write("INFO: The machine is full!\n");
                break;
            }
        }
        // Match attributes
        machine.attributesMatch(machine.current_slots,machine.creatingProducts(prodInputFile));


        // A string array is defined where the properties of the slots in the machine are arranged sequentially.
        String[] firstTable = new String[machine.current_slots.length*3];
        for (int i = 0; i < machine.current_slots.length; i++) {
            firstTable[i] = machine.current_slots[i].getSlotProduct();
            firstTable[machine.current_slots.length+i] = String.valueOf(machine.current_slots[i].getAmount());
            firstTable[machine.current_slots.length*2+i] = String.valueOf(machine.current_slots[i].getSlotCalorie());
        }

        //The state of the machine before the user starts shopping is printed.
        IO.writeToTable(firstTable);

        // The user makes a purchase from the machine.
        machine.shopping(IO.readCreatePurchase(purcInputFile,IO.countLines(purcInputFile)),IO.readAllFile(purcInputFile));


        // A string array is defined where the properties of the slots in the machine are arranged sequentially.
        String[] finalTable = new String[machine.current_slots.length*3];
        for (int i = 0; i < machine.current_slots.length; i++) {
            finalTable[i] = machine.current_slots[i].getSlotProduct();
            finalTable[machine.current_slots.length+i] = String.valueOf(machine.current_slots[i].getAmount());
            finalTable[machine.current_slots.length*2+i] = String.valueOf(machine.current_slots[i].getSlotCalorie());
        }

        //The state of the machine after the user starts shopping is printed.
        IO.writeToTable(finalTable);

        //  Closes the file.
        IO.closeFile();

    }
}
