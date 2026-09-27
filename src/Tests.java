public class Tests {
    public static void main(String[] args) {
        testDynamicArray();
        testLinkedList();
    }

    private static void testDynamicArray() {
        DynamicArray array = new DynamicArray();

        check(array.size() == 0, "Empty array");

        array.add(10);
        check(array.size() == 1, "Add one element");
        check(array.get(0) == 10, "Get one element");

        array.add(20);
        array.add(30);
        check(array.size() == 3, "Add multiple elements");
        check(array.get(1) == 20, "Get middle element");

        array.add(1, 15);
        check(array.get(0) == 10, "Indexed insertion keeps previous element");
        check(array.get(1) == 15, "Indexed insertion");
        check(array.get(2) == 20, "Indexed insertion shifts elements");

        array.add(20);
        check(array.contains(20), "Contains duplicate value");
        check(!array.contains(100), "Value not found");

        int removed = array.remove(1);
        check(removed == 15, "Remove returns correct value");
        check(array.get(1) == 20, "Remove shifts elements");
        check(array.size() == 4, "Size after removal");

        array.add(0, 5);
        check(array.get(0) == 5, "Insert at beginning");

        array.add(array.size(), 40);
        check(array.get(array.size() - 1) == 40, "Insert at end");

        boolean invalidGet = false;
        try {
            array.get(-1);
        } catch (IndexOutOfBoundsException e) {
            invalidGet = true;
        }
        check(invalidGet, "Invalid negative index");

        boolean invalidRemove = false;
        try {
            array.remove(array.size());
        } catch (IndexOutOfBoundsException e) {
            invalidRemove = true;
        }
        check(invalidRemove, "Invalid removal index");

        DynamicArray largeArray = new DynamicArray();
        for (int i = 0; i < 100000; i++) {
            largeArray.add(i);
        }

        check(largeArray.size() == 100000, "Large input size");
        check(largeArray.get(99999) == 99999, "Large input value");

        System.out.println("All DynamicArray tests passed.");
    }

    private static void testLinkedList() {
        LinkedList list = new LinkedList();

        check(list.size() == 0, "Empty linked list");

        list.add(10);
        check(list.size() == 1, "LinkedList add one element");
        check(list.get(0) == 10, "LinkedList get one element");

        list.add(20);
        list.add(30);
        check(list.size() == 3, "LinkedList add multiple elements");
        check(list.get(1) == 20, "LinkedList get middle element");

        list.add(1, 15);
        check(list.get(0) == 10, "LinkedList indexed insertion keeps previous element");
        check(list.get(1) == 15, "LinkedList indexed insertion");
        check(list.get(2) == 20, "LinkedList indexed insertion shifts position");

        list.add(20);
        check(list.contains(20), "LinkedList contains duplicate value");
        check(!list.contains(100), "LinkedList value not found");

        int removed = list.remove(1);
        check(removed == 15, "LinkedList remove returns correct value");
        check(list.get(1) == 20, "LinkedList remove updates links");
        check(list.size() == 4, "LinkedList size after removal");

        list.add(0, 5);
        check(list.get(0) == 5, "LinkedList insert at beginning");

        list.add(list.size(), 40);
        check(list.get(list.size() - 1) == 40, "LinkedList insert at end");

        boolean invalidGet = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            invalidGet = true;
        }
        check(invalidGet, "LinkedList invalid negative index");

        boolean invalidRemove = false;
        try {
            list.remove(list.size());
        } catch (IndexOutOfBoundsException e) {
            invalidRemove = true;
        }
        check(invalidRemove, "LinkedList invalid removal index");

        LinkedList largeList = new LinkedList();
        for (int i = 0; i < 100000; i++) {
            largeList.add(0, i);
        }

        check(largeList.size() == 100000, "LinkedList large input size");
        check(largeList.get(0) == 99999, "LinkedList large input value");

        System.out.println("All LinkedList tests passed.");
    }

    private static void check(boolean condition, String testName) {
        if (!condition) {
            throw new AssertionError("Test failed: " + testName);
        }
    }
}