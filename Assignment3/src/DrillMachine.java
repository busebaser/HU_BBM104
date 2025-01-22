/**
 * This class represents a DrillMachine object and contains its position represented by row and column values, as well as boolean properties to control whether it is moving or not.
 */

public class DrillMachine{
    private int row_drill;
    private int column_drill;

    private boolean isMoving = false;

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getRow_drill() {
        return row_drill;
    }

    public void setRow_drill(int row_drill) {
        this.row_drill = row_drill;
    }

    public int getColumn_drill() {
        return column_drill;
    }

    public void setColumn_drill(int column_drill) {
        this.column_drill = column_drill;
    }
}
