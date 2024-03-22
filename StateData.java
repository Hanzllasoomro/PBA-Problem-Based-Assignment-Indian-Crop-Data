import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StateData {
    String state;
    LinkedList<CropData> cropDataList;
    Stack<CropCount> cropCountStack;
    LinkedList<YearData> yearDataList;
    StateData(String state){
        this.state = state;
        this.cropDataList = new LinkedList<>();
        this.cropCountStack = new Stack<>();
        this.yearDataList = new LinkedList<>();
    }
    void sortStack(){
        Stack<CropCount> temp = new Stack<>();
        while(!cropCountStack.isEmpty()){
            CropCount  cropCount = cropCountStack.pop();
            while (!temp.isEmpty() && temp.peek().count > cropCount.count)
                cropCountStack.push(temp.pop());
            temp.push(cropCount);
        }
        cropCountStack = temp;
    }
}
