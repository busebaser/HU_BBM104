import java.io.*;
import java.util.*;

/**
 * This class reads data from input files, processes them to be used in other classes or methods, and writes the necessary data to the output file.
 */
public class IO {

    /**
     * Represents a static BufferedWriter object used for writing data.
     */
    public static BufferedWriter bw;
    /**
     * Represents a static instance of the Machine class.
     */
    static Machine machine = new Machine();

    /**
     * This method reads the names of the products from the text file, considering whether the product names consist of single or two words, and adds them to the list of product names.
     * @param path The input file from which the names of the products will be obtained.
     * @return Returns the list of products.
     */
    public static String[] readandGetProduct(String path) {
        List<String> product_list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 5) {
                    product_list.add(parts[0]);
                } else if (parts.length > 5) {
                    product_list.add(parts[0] + " " + parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] products = new String[product_list.size()];
        products = product_list.toArray(products);
        return products;
    }

    /**
     * Constructs an IO object with the specified output file.
     * Initializes a BufferedWriter to write data to the output file.
     * @param outputfile The name of the output file to which data will be written.
     */
    public IO(String outputfile) {
        try {
            BufferedWriter bw = new BufferedWriter((new FileWriter(outputfile)));
            IO.bw = bw;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the table containing the properties of the slots inside the machine (the product it contains, the quantity, and the calorie value).
     * @param array The array containing the properties of the slots along with the slots themselves.
     */
    public static void writeToTable(String[] array) {
        try {
            bw.write("-----Gym Meal Machine-----\n");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    int index = i * 4 + j;

                    bw.write(array[index] + "(" +  array[index + machine.current_slots.length*2] + ", " + array[index + machine.current_slots.length] + ")___");
                }
                bw.write("\n");
            }
            bw.write("----------\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the BufferedWriter associated with the output file.
     * It flushes any buffered data to the output file and then closes the file.
     * @throws IOException If an I/O error occurs while closing the file.
     */
    public static void closeFile() throws IOException {
        bw.flush();
        bw.close();
    }

    /**
     * This method reads each line from the input file specified by the path, and based on whether the product name consists of a single or two words, it implements a different indexing system.
     * It then adds the properties of the product provided in the input file to a list of products, where each product's name and properties are placed side by side in sequence.
     * @param path The path of the input file containing the properties of the products.
     * @return The list where all products are sequentially arranged along with their properties.
     */
    public static ArrayList<String> getProductAttributes(String path) {
        ArrayList<String> productList = new ArrayList<>();
        ArrayList<String> addedNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split("\\s+");
                String name, price, protein, carbo, fat;
                if (datas.length == 5) {
                    name = datas[0];
                    price = datas[1];
                    protein = datas[2];
                    carbo = datas[3];
                    fat = datas[4];
                } else if (datas.length == 6) {
                    name = datas[0] + " " + datas[1];
                    price = datas[2];
                    protein = datas[3];
                    carbo = datas[4];
                    fat = datas[5];

                } else {
                    continue;
                }
                if (!addedNames.contains(name)) {
                    productList.add(name);
                    productList.add(price);
                    productList.add(protein);
                    productList.add(carbo);
                    productList.add(fat);

                    addedNames.add(name);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    /**
     * Counts the number of lines in the file specified by the given path.
     * @param path The path of the file to count the lines.
     * @return The number of lines in the file.
     */
    public static int countLines(String path){
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            while(br.readLine() != null){
                count++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Calculates the sum of all elements in the given array.
     * This method is created to calculate the total value of the money that the user has inserted into the machine.
     * @param array The list of integers to calculate the sum.
     * @return The sum of all elements in the array.
     */
    public static int sum(List<Integer> array){
        int sum = 0;
        for(int i = 0; i< array.size(); i++){
            sum+= array.get(i);
        }
        return sum;
    }

    /**
     * This method first creates a Purchase object for each expenditure the user will make (equal to the number of lines in the input file) from the Purchase class and stores them in the 'purchases' array.
     * Then, it sets the properties of the expenditures that the user will make sequentially, based on the data obtained from the input file it reads.
     * @param path The path of the input file where the expenditures of the user are found line by line.
     * @param count The total number of lines in the input file, and the total number of expenditures made by the user.
     * @return Returns the array containing all expenditures.
     */
    public static Purchase[] readCreatePurchase(String path, int count) {
        Purchase[] purchases = new Purchase[count];
        for (int s = 0; s < count; s++) {
            boolean purchaseSuit = true;
            String purchaseType = "___";
            int purchaseMoney = 0;
            int purchaseValue = 0;
            purchases[s] = new Purchase(purchaseSuit,purchaseMoney,purchaseType, purchaseValue);
        }
        List<Integer> possibleNumbers = Arrays.asList(1, 5, 10, 20, 50, 100, 200);
        List<String> array = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\t");
                for (String part : parts) {
                    String[] subParts = part.trim().split(" ");
                    for (String subPart : subParts) {
                        array.add(subPart);
                    }
                }
                for (int m = 1; m < array.size() - 2; m++) {
                    numberList.add(Integer.parseInt(array.get(m)));
                }
                stringList.add(array.get(array.size()-2));

                for (int num : numberList) {
                    purchases[i].setSuitable(possibleNumbers.contains(num));
                }

                purchases[i].setMoney(sum(numberList));
                purchases[i].setType(stringList.get(0));
                purchases[i].setValue(Integer.parseInt(array.get(array.size() - 1)));

                numberList.clear();
                stringList.clear();
                array.clear();
                i++;
            }

            } catch(IOException e){
                e.printStackTrace();
            }
            return purchases;
        }

    /**
     * This method, for the purpose of writing the expenditure that the user wants to make to the output file,
     * takes the data from the input file containing the expenditures and adds them to a list where they are all sequentially present.
     * @param path The path of the input file containing the expenditures.
     * @return Returns the list of strings containing the expenditures.
     */
    public static String[] readAllFile(String path) {
        ArrayList<String> organizedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null){
                String[] words = line.trim().split("\\s+");
                if(words.length>2){
                    String last = words[words.length-1];
                    String wordBeforeLast = words[words.length-2];
                    StringBuilder middles = new StringBuilder();
                    for(int i =1; i< words.length-2 ; i++){
                        middles.append(words[i]).append(" ");
                    }
                    organizedLines.add("CASH    " + middles.toString().trim() + "\t" + wordBeforeLast + " " +  last) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return organizedLines.toArray(new String[0]);
    }
}



