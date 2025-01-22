import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * In this class, the underground is divided into 14 rows and 16 columns, with each unit considered as a parcel.
 * X and Y coordinates are set according to the row and column where each parcel is located.
 * Methods in this class access possible locations where obstacles, valuable minerals, and lava can be found.
 **/
public class UnderGroundCoordinates {


    public static List<int[]> possibleCoordinates = possibleCoordinates();
    public static List<int[]> obstaclesCoordinates = settingNewObstacles(obstaclesCoordinates());
    public static List<int[]> lavasCoordinates = lavaCoordinates();
    public static List<int[]> valuablesCoordinates = valuablesCoordinates();




    //The coordinates of parcels that are outside the areas where obstacles will be located on the right, left, and bottom of the screen are kept in a list as possible coordinates.
    public static List<int[]> possibleCoordinates(){
        List<int[]> possibleCoordinates = new ArrayList<>();


        for(int row=1; row <=12; row++){
            for(int column=1; column <= 14; column++) {
                int[] coordinate = {row, column};
                possibleCoordinates.add(coordinate);
            }

        }
        return possibleCoordinates;
    }


    /**
     * @return A list containing the coordinates of obstacles located in the far right and far left columns of the game screen, as well as those located at the bottom.
     **/
    public static List<int[]> obstaclesCoordinates(){
        List<int[]> obstaclesCoordinates = new ArrayList<>();

        for(int row = 1; row <= 12; row++){
            int[] cord = {row,15};
            int[] cord2 = {row,0};
            obstaclesCoordinates.add(cord);
            obstaclesCoordinates.add(cord2);
        }

        for(int column = 0; column <= 15; column++){
            int[] cord = {13,column};
            obstaclesCoordinates.add(cord);
        }

        return

                obstaclesCoordinates;
    }

    /**
     * It determines the locations of valuable minerals within the underground area by randomly selecting indices from the possibleCoordinates list.
     * This ensures that valuable minerals are randomly distributed with random coordinates in each game.
     * @return A list containing the coordinates of the locations where valuable minerals will be found.
     */
    public static List<int[]> valuablesCoordinates(){
        List<int[]> valuablesCoordinates = new ArrayList<>();
        // It randomly selects how many valuable minerals there will be within the underground area.
        int randomCountNumber = selectRandomNumber(5,10);

        for(int i = 0; i < randomCountNumber; i++){
            int randomIndex = selectRandomNumber(0, possibleCoordinates.size()-1);
            int[] coord = possibleCoordinates.get(randomIndex);
            valuablesCoordinates.add(coord);
            // It removes the selected coordinates from the possibleCoordinates list to ensure that the same coordinates are not assigned multiple values.
            possibleCoordinates.remove(randomIndex);

        }
        return valuablesCoordinates;
    }




    /**
     * It determines the locations of lavas within the underground area by randomly selecting indices from the possibleCoordinates list.
     * This ensures that lavas are randomly distributed with random coordinates in each game.
     * @return A list containing the coordinates of the locations where lavas will be found.
     */
    public static List<int[]> lavaCoordinates(){
        List<int[]> lavaCoordinates = new ArrayList<>();
        // It randomly selects how many lavas there will be within the underground area.
        int randomCountNumber = selectRandomNumber(5,10);

        for(int i = 0; i < randomCountNumber; i++){
            int randomIndex = selectRandomNumber(0, possibleCoordinates.size()-1);
            int[] coord = possibleCoordinates.get(randomIndex);
            lavaCoordinates.add(coord);
            possibleCoordinates.remove(randomIndex);
        }

        return lavaCoordinates;
    }


    /**
     *
     * @param obstaclesCoordinates A list containing the coordinates of obstacles.
     * @return A list containing the coordinates of obstacles that can be found at random locations within the game area, excluding the predetermined obstacle locations at the beginning of the game area.
     */
    public static List<int[]> settingNewObstacles(List<int[]> obstaclesCoordinates){
        int randomCountNumber = selectRandomNumber(1,3);

        for(int i= 0; i<randomCountNumber;i++){
            int randomIndex = selectRandomNumber(0, possibleCoordinates.size()-1);
            int[] coord = possibleCoordinates.get(randomIndex);
            if (!Arrays.equals(coord,new int[]{0, 0})){
                obstaclesCoordinates.add(coord);
                possibleCoordinates.remove(randomIndex);

            }

        }

        return obstaclesCoordinates;
    }


    /**
     *
     * @return A random number within the range of two given numbers provided as parameters
     */
    public static int selectRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
