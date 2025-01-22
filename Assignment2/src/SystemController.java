import java.util.ArrayList;
import java.util.Objects;

/**
 * This class checks the entered inputs and returns error messages if the inputs are incorrect.
 * In short, it contains methods to ensure the proper functioning of the bus system.
 */
public class SystemController {
    // Array containing valid commands
    public String[] commands = {"INIT_VOYAGE","SELL_TICKET","REFUND_TICKET","PRINT_VOYAGE","CANCEL_VOYAGE","Z_REPORT"};
    // Array containing valid options
    public String[] options = {"Standard","Premium","Minibus"};

    /**
     * Method to check if a given command is valid
     * @param first The command
     * @return If the entered command exists within the list of available commands, it returns true; otherwise, it returns false.
     */
    public boolean commandControl(String first) { // Method to check if a given command is valid
        for (String i : commands) {
            if (Objects.equals(i, first)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if a given option is valid
     * @param second the option
     * @return If the entered option exists within the list of available options, it returns true; otherwise, it returns false.
     */
    public boolean optionsControl(String second){
        for(String i : options){
            if(Objects.equals(i,second)){
                return true;
            }
        }
        return false;
    }


    /**
     * Method to check if a given ID already exists in the ArrayList
     * It splits the string containing 'id,seatCount' for each bus by comma, then retrieves the ID of that bus by checking the first index.
     * @param idArr An array containing strings with the IDs and seat counts of buses.
     * @param id The ID to be checked
     * @return If the ID is in the list, it returns true; otherwise, it returns false.
     */
    public boolean idControl(ArrayList<String> idArr, String id){
        for(int i= 0; i < idArr.size(); i++){
            if(Objects.equals(idArr.get(i).split(",")[0], id)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the given seat numbers are greater than the seat numbers in that bus.
     * @param id The ID of the bus to be checked.
     * @param idArr The array list stored as a string with the ID to access the number of seats on the bus.
     * @param seatNumber The numbers to be checked.
     * @return If the number does not exist on the bus, it returns false; otherwise, it returns true.
     */
    public boolean seatControl(String id, ArrayList<String> idArr, String seatNumber){
        int[] seatsNumbers = seatNumberOrganizer(seatNumber);

        for(int i=0; i < idArr.size(); i++){
            if(Objects.equals(idArr.get(i).split(",")[0], id)){
                for(int j=0; j < seatsNumbers.length; j++){
                    if(Integer.parseInt(idArr.get(i).split(",")[1]) < seatsNumbers[j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * A method that checks whether the given seat numbers are positive.
     * @param arr An array containing the seat numbers.
     * @return If the seat numbers are greater than 0, it returns true; otherwise, false.
     */
    public boolean seatNumberControl(String arr){
        int[] seatsNumber = seatNumberOrganizer(arr);

        for(int i=0; i < seatsNumber.length;i++){
            if(seatsNumber[i] < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * It has been created to facilitate the conversion of seat numbers from String to integer in each method, as the seat numbers are provided as a String from the input file.
     * @param seats The string array containing seat numbers.
     * @return An integer array containing seat numbers.
     */
    public int[] seatNumberOrganizer(String seats){
        int[] intArray = new int[Math.floorDiv(seats.length(),2)+1];
        if(seats.length() == 1){
            intArray[0] = Integer.parseInt(seats);
        }else{
            String[] strArray = seats.split("_");
            for (int i =0; i < strArray.length; i++){
                intArray[i] = Integer.parseInt(strArray[i]);
            }

        }
        return intArray;
    }


    /**
     * It performs validation of the given input line.
     * @param line The input line
     * @param idArr The string list containing both the IDs and seat counts of buses.
     * @return If there is an error, it returns these errors as a string; if there are no errors, it returns the string 'NoError'.
     */
    public String errorDetection(String[] line,ArrayList<String> idArr){

        if(commandControl(line[0])){
            // Check specific conditions for each command
            if(Objects.equals(line[0], "INIT_VOYAGE")){
                // Check if the option is valid
                if(optionsControl(line[1])){
                    // Check validity of other parameters
                    if(Objects.equals(line[1], "Minibus") && line.length != 7){
                        return "ERROR: Missing or excess data cannot be entered when initializing a Minibus Bus voyage.";
                    }
                    if(Objects.equals(line[1], "Standard") && line.length != 8){
                        return "ERROR: Missing or excess data cannot be entered when initializing a Standard Bus voyage.";
                    }
                    if(Objects.equals(line[1], "Premium") && line.length != 9){
                        return "ERROR: Missing or excess data cannot be entered when initializing a Premium Bus voyage.";
                    }
                    if(Integer.parseInt(line[2]) < 0){
                        return String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n",line[2]);
                    }
                    // Check for duplicate IDs
                    if(idControl(idArr,line[2])) {
                        return String.format("ERROR: There is already a voyage with ID of %s!\n", line[2]);
                    }
                    // Check if the number of seat rows are positive or not.
                    if(Integer.parseInt(line[5]) < 0){
                        return String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!\n",line[5]);
                    }
                    // Check if the price is a positive number or not.
                    if(Double.parseDouble(line[6]) < 0){
                        return String.format("ERROR: %s is not a positive number, price must be a positive number!\n",line[6]);
                    }
                    // Check if the refund cut is in range of [0,100] or not.
                    if (line.length > 7) {
                        if(Integer.parseInt(line[7]) < 0 || Integer.parseInt(line[7]) > 100){
                            return String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!\n",line[7]);
                        }
                    }
                    // Check if the premium fee is a positive integer or not.
                    if(line.length > 8 && Objects.equals(line[1],"Premium")){
                        if(Integer.parseInt(line[8]) < 0){
                            return String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!\n",line[8]);
                        }
                    }

                }else{
                    return "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n";
                }
            }
            if(Objects.equals(line[0], "SELL_TICKET")){
                // Check the sell_ticket command line's length is 3 or not.
                if(line.length != 3){
                    return "ERROR: Erroneous usage of \"SELL_TICKET\" command!\n";
                }
            }
            // Check the refund_ticket command line's length is 3 or not.
            if(Objects.equals(line[0], "REFUND_TICKET")){
                if(line.length != 3){
                    return "ERROR: Erroneous usage of \"REFUND_TICKET\" command!\n";
                }

            }
            if(Objects.equals(line[0], "REFUND_TICKET") || Objects.equals(line[0], "SELL_TICKET")){
                // Check if the id is valid.
                if(!idControl(idArr,line[1])){
                    return String.format("ERROR: There is no voyage with ID of %s!\n",line[1]);
                }
                // Check if the seat number is valid.
                if(!seatControl(line[1],idArr,line[2])){
                    return "ERROR: There is no such a seat!\n";
                }
                // Check if the seat number is a positive integer.
                if(!seatNumberControl(line[2])){
                    return String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!\n",line[2].substring(0,2));
                }
            }

            if(Objects.equals(line[0], "CANCEL_VOYAGE")){
                // Check the command of cancel_voyage line's length is 2.
                if(line.length != 2){
                    return "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!\n";
                }
            }

            if(Objects.equals(line[0], "PRINT_VOYAGE")){
                // Check the command of cancel_voyage line's length is 2.
                if(line.length != 2){
                    return "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!\n";
                }
            }

            if(Objects.equals(line[0], "CANCEL_VOYAGE") || Objects.equals(line[0], "PRINT_VOYAGE")){
                // Check if the id of a voyage is a positive integer.
                if(Integer.parseInt(line[1]) <= 0 ){
                    return String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n",line[1]);
                }
                // Check if the id is valid.
                if(!idControl(idArr,line[1])){
                    return String.format("ERROR: There is no voyage with ID of %S!\n",line[1]);
                }

            }

            if(Objects.equals(line[0], "Z_REPORT")){
                // Check if the z_report command line's length is 1.
                if(line.length != 1){
                    return "ERROR: Erroneous usage of \"Z_REPORT\" command!\n";
                }
            }

        }else{
            return "ERROR: There is no command namely " + line[0] + "!\n";
        }

        return "NoError";

    }


}
