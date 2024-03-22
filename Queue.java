import java.util.Stack;

//public class Queue{
//    Node head = new Node(null);
//    int size ;
//    public void enqueue(CropData cropData) {
//        if (head.cropData == null) {
//            head = new Node(cropData);
//            head.prev = head;
//            head.next = head;
//            size = 1;
//            return;
//        }
//        head.prev.next = new Node(cropData, head.prev, head);
//        head.prev = head.prev.next;
//        size++;
//    }
//    public void isEmpty() {
//        if (size == 0)
//            throw new IllegalStateException("Queue is empty");
//    }
//    public void traverse() {
//        isEmpty();
//        Node temp = head.next;
//        while (temp.cropData != head.cropData) {
//            System.out.println(temp.cropData.cropName);
//            temp = temp.next;
//        }
//    }
//
//    class Node{
//        Node next;
//        Node prev;
//        CropData cropData;
//        Node(CropData cropData){
//            this.cropData = cropData;
//        }
//        Node(CropData cropData , Node prev , Node next){
//            this.cropData = cropData;
//            this.next = next;
//            this.prev = prev;
//        }
//    }
//}

















































import java.util.LinkedList;

public class Queue {
    private Node head = new Node(null);
    private int size;

    public void add(LinkedList<StateData> stateDataLinkedList , String requiredState) {
        for(StateData stateData : stateDataLinkedList)
            if(stateData.state.equalsIgnoreCase(requiredState)){
                Node newNode = new Node(requiredState);
                Node lastNode = head.prev;
                lastNode.next = newNode;
                newNode.prev = lastNode;
                newNode.next = head;
                head.prev = newNode;
                size++;
        }
    }

    public void placeCropData(StateData states) {
        Node p;
        CropCount Count;
        for (CropData cropData : states.cropDataList) {
            p = containsYear(cropData.cropYear);
            boolean cropExists = false;
            for (CropData c : p.cropData)
                if (c.cropName.equalsIgnoreCase(cropData.cropName)){
                    cropExists = true;
                    break;
                }
            if (!cropExists) {
                p.cropData.add(cropData);
                Count = ContainsCrop(p.cropCount, cropData.cropName);
                Count.count++;
            }
        }
    }

    public void recentCrop() {
        if (isEmpty()) {
            System.out.println("Insert the State's data first!");
            return;
        }
        Node p = head.next;
        int recentYear = (int) p.cropYear;
        while (p != head) {
            if ((int) p.cropYear > recentYear) {
                recentYear = (int) p.cropYear;
            }
            p = p.next;
        }
        p = head.next;
        LinkedList<LinkedList<CropData>> recentCropData = new LinkedList<>();
        while (p != head) {
            if ((int) p.cropYear == recentYear) {
                recentCropData.add(p.cropData);
                //System.out.println("The recent crop is \u001B[1m" + p.cropData.getLast().cropName + " \u001B[0m is in the \u001B[1m" + p.cropData.getLast().cropYear + "\u001B[0m year ");
            }
            p = p.next;
        }
        System.out.println("The recent Crops is in the " + recentYear + " year, they are:");
        for (LinkedList<CropData> cropData : recentCropData)
            for (CropData crop : cropData)
                System.out.print(crop.cropName + " , ");
        System.out.println();
    }

    public void oldestCrop() {
        if (isEmpty()) {
            System.out.println("Insert the State's data first!");
            return;
        }
        Node p = head.next;
        int OldestYear = (int) p.cropYear;
        System.out.println(p.cropYear);
        while (p != head) {
            if ((int) p.cropYear < OldestYear)
                OldestYear = (int) p.cropYear;
            p = p.next;
        }
        p = head.next;
        LinkedList<LinkedList<CropData>> oldestCropData = new LinkedList<>();
        System.out.println(OldestYear);
        while (p != head) {
            if ((int) p.cropYear == OldestYear)
                oldestCropData.add(p.cropData);
                //System.out.println("The Oldest Crop is \u001B[1m " + p.cropData.getFirst().cropName + "\u001B[0m is in the \u001B[1m" + p.cropData.getLast().cropYear + "\u001B[0m year");
            p = p.next;
        }
        System.out.println("The oldest Crops is in the " + OldestYear + " year, they are:");
        for (LinkedList<CropData> cropData : oldestCropData)
            for (CropData crop : cropData)
                System.out.print(crop.cropName + " , ");
        System.out.println();
    }

    public void popularCrop(int y) {
        if (this.isEmpty()) {
            System.out.println("Insert the data first!");
            return;
        }
        Node p = this.head.next;
        while (p != head) {
            if ((int) p.cropYear == y) {
                System.out.println(CalculatePopular(p).crop + " is the popular crop of year " + p.cropYear + ",  There are " + CalculatePopular(p).count + " of these crops.");
                return;
            }
            p = p.next;
        }
        System.out.println("There's no data of year " + y + " for the current State");
    }

    private CropCount CalculatePopular(Node p) {
        CropCount pop = p.cropCount.peek();
        for (CropCount c : p.cropCount) {
            if (c.count > pop.count) {
                pop = c;
            }
        }
        return pop;

    }

    private Node containsYear(int y) {
        if (isEmpty()) {
            head.prev.next = new Node(y, head, head.prev);
            head.prev = head.prev.next;
            size++;
            return head.next;
        }
        Node p = head.next;
        while (p != head) {
            if ((int) p.cropYear == y) {
                return p;
            }
            p = p.next;
        }
        p.prev.next = new Node(y, p, p.prev);
        p.prev = p.prev.next;
        size++;
        return p.prev.next;
    }

    public CropCount ContainsCrop(LinkedList<CropCount> cc, String Cname) {
        for (CropCount count : cc)
            if (count.crop.equalsIgnoreCase(Cname))
                return count;
        CropCount count = new CropCount(Cname, 1);
        cc.add(count);
        return count;
    }

    public Object remove() {
        if (this.size == 0) {
            throw new IllegalStateException();
        }
        Object temp = this.head.next.cropYear;
        this.head.next = head.next.next;
        head.next.prev = head;
        this.size--;
        return temp;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public int size() {
        return this.size;
    }

    public void printFifo(int targetYear) {
        for (Node p = this.head.next; p != head; p = p.next) {
            if ((int) p.cropYear == targetYear) {
                System.out.println("-------------------" + p.cropYear + "'s Crop Data------------------------------");
                for (CropData c : p.cropData) {
                    System.out.println(c);
                }

                System.out.println("-------------------" + p.cropYear + "'s Crop Count------------------------------");
                for (CropCount c : p.cropCount) {
                    System.out.println(c);
                }
            }
        }
    }

    private static class Node {
        Object cropYear;
        LinkedList<CropData> cropData = new LinkedList<>();
        LinkedList<CropCount> cropCount = new LinkedList<>();
        Node next = this;
        Node prev = this;

        Node(Object cropYear) {
            cropYear = cropYear;
        }

        Node(Object cropYear, Node next, Node prev) {
            this.cropYear = cropYear;
            this.next = next;
            this.prev = prev;
        }

    }
}
