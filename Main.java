import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Stack;

public class Main {
    private static final String filePath = "E:\\Sem3\\DSA\\Assignments\\PBA\\src\\crop_production.csv";
    static String requiredState = "Andhra Pradesh";
    static int targetYear = 2010;
    static Queue queue = new Queue();

    public static void main(String[] args) throws Exception {


        LinkedList<StateData> stateDataLinkedList = readCsv();
        //getCropCount(stateDataLinkedList);
        //queue.traverse();

        // Problem 1:
        System.out.println("The most popular crop in particular years is:");
        getPopularCropPerYear(stateDataLinkedList);

        /// Problem 2:
        System.out.println("states with popular crop");
        getStatePopularCrops(stateDataLinkedList);

        //Probelem 3:
        System.out.println("The recent and oldest crop of ");
        getOldestAndRecentCrops(stateDataLinkedList, requiredState);

    }

    static LinkedList<StateData> readCsv() throws Exception {
        LinkedList<StateData> stateDataLinkedList = new LinkedList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String delimiter = ",";
        bufferedReader.readLine();

        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(delimiter);
            String state = data[0].trim();
            String district = data[1].trim();
            int cropYear = Integer.parseInt(data[2].trim());
            String cropName = data[4].trim();

            StateData stateData = getStateData(stateDataLinkedList, state);
            CropData cropData = new CropData(state, district, cropYear, cropName);
            stateData.cropDataList.add(cropData);
            initializingCropCountStack(stateData.cropCountStack, cropName);
            if (state.equalsIgnoreCase(requiredState) && cropYear == targetYear)
                queue.add(stateDataLinkedList ,requiredState);

        }
        return stateDataLinkedList;
    }

    private static LinkedList<YearData> getPopularCropPerYear(LinkedList<StateData> stateDataLinkedList) {
        YearData yearData ;
        LinkedList<YearData> yearDataLinkedList = new LinkedList<>();
        for (StateData stateData : stateDataLinkedList)
            for (CropData cropData : stateData.cropDataList) {
                yearData = getYearData(yearDataLinkedList, cropData.cropYear);
                yearData.cropData.add(cropData);
                initYearDataStack(yearData.cropCounts, cropData.cropName);
            }
        for (YearData yearData1 : yearDataLinkedList){
            yearData1.sortStack();
            System.out.println("\u001B[1m" + yearData1.year + "\u001B[0m");
            CropCount cropCount = yearData1.cropCounts.getLast();
            System.out.println(cropCount.crop + " : " + cropCount.count);
            System.out.println();

        }
    return yearDataLinkedList;
    }
    private static void getOldestAndRecentCrops(LinkedList<StateData> stateDataLinkedList, String requiredState) {
        for (StateData stateData : stateDataLinkedList) {
            if(stateData.state.equalsIgnoreCase(requiredState)) {
                System.out.println("\u001B[1m" + stateData.state + "\u001B[0m");
                int recentYear = stateData.cropDataList.getLast().cropYear;
                int oldestYear = stateData.cropDataList.getFirst().cropYear;
                System.out.println("\u001B[1m Recent Crop : " + recentYear + "\u001B[0m");
                for(CropData cropData : stateData.cropDataList) {
                    if(cropData.cropYear == recentYear)
                        System.out.println(cropData.cropName);
                }                System.out.println();
                System.out.println("\u001B[1m Oldest Crop : " + oldestYear + "\u001B[0m");
                for(CropData cropData : stateData.cropDataList) {
                    if(cropData.cropYear == oldestYear)
                        System.out.println(cropData.cropName);
                }                System.out.println();
            }
        }
    }

    private static StateData getStateData(LinkedList<StateData> stateDataLinkedList, String state) {
        for (StateData sd : stateDataLinkedList)
            if (sd.state.equalsIgnoreCase(state))
                return sd;
        var newStateData = new StateData(state);
        stateDataLinkedList.add(newStateData);
        return newStateData;
    }
    private static YearData getYearData(LinkedList<YearData> yearDataList, int year) {
        for (YearData yearData : yearDataList)
            if (yearData.year == year)
                return yearData;
        var newYearData = new YearData(year);
        yearDataList.add(newYearData);
        return newYearData;
    }

    private static void initializingCropCountStack(Stack<CropCount> cropCountStack, String cropName) {
        Stack<CropCount> temporaryStack = new Stack<>();
        boolean cropFound = false;
        while (!cropCountStack.isEmpty()) {
            CropCount cropCount = cropCountStack.pop();
            if (cropCount.crop.equalsIgnoreCase(cropName)) {
                cropCount.count++;
                cropFound = true;
            }
            temporaryStack.push(cropCount);
        }
        if (!cropFound)
            temporaryStack.push(new CropCount(cropName, 1));
        while (!temporaryStack.isEmpty())
            cropCountStack.push(temporaryStack.pop());
    }
    private static void initYearDataStack(Stack<CropCount> yearDataStack , String cropName) {
        Stack<CropCount> temporaryStack = new Stack<>();
        boolean cropFound = false;
        while (!yearDataStack.isEmpty()) {
            CropCount cropCount = yearDataStack.pop();
            if (cropCount.crop.equalsIgnoreCase(cropName)) {
                cropCount.count++;
                cropFound = true;
            }
            temporaryStack.push(cropCount);
        }
        if (!cropFound)
            temporaryStack.push(new CropCount(cropName, 1));
        while (!temporaryStack.isEmpty())
            yearDataStack.push(temporaryStack.pop());
    }

    private static void getCropCount(LinkedList<StateData> stateDataLinkedList) {
        for (StateData stateData : stateDataLinkedList) {
            stateData.sortStack();
            System.out.println("\u001B[1m" + stateData.state + "\u001B[0m");
            while (!stateData.cropCountStack.isEmpty()) {
                CropCount cropCount = stateData.cropCountStack.pop();
                System.out.println(cropCount.crop + " : " + cropCount.count);
            }
            System.out.println();
        }
    }

    private static void getStatePopularCrops(LinkedList<StateData> stateDataLinkedList) {
        for (StateData stateData : stateDataLinkedList){
                stateData.sortStack();
                    System.out.println("\u001B[1m" + stateData.state + "\u001B[0m");
                    CropCount cropCount = stateData.cropCountStack.getLast();
                    System.out.println(cropCount.crop + " : " + cropCount.count);
                    CropCount cropCount2 = stateData.cropCountStack.get(stateData.cropCountStack.size() - 2);
                    System.out.println(cropCount2.crop + " : " + cropCount2.count);
                    System.out.println();

            }
    }
}
