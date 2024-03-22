import java.util.*;

public class YearData {
    public int year;
    public LinkedList<CropData> cropData;
    public Stack<CropCount> cropCounts;
    public YearData(int year){
        this.year = year;
        cropData = new LinkedList<>();
        cropCounts = new Stack<>();
    }
    public void sortStack(){
        Stack<CropCount> temp = new Stack<>();
        while(!cropCounts.isEmpty()){
            CropCount cropCount = cropCounts.pop();
            while(!temp.isEmpty() && temp.peek().count > cropCount.count)
                cropCounts.push(temp.pop());
            temp.push(cropCount);
        }
        cropCounts = temp;
    }
}
