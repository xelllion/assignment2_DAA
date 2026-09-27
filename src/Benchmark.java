import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RANDOM_ACCESS_OPERATIONS = 10000;
    private static final int SEARCH_OPERATIONS = 1000;
    private static final int REPETITIONS = 5;
    private static final int SEED = 67;

    public static void main(String[] args) {
        runRandomAccessWorkload();
        runSearchWorkload();
    }

    private static void runRandomAccessWorkload() {
        System.out.println("Workload 1 - Random Access");
        System.out.println();

        for (int n : SIZES) {
            int[] values = generateValues(n);
            int[] indices = generateIndices(n, RANDOM_ACCESS_OPERATIONS);

            double dynamicArrayTotalTime = 0;
            double linkedListTotalTime = 0;
            long dynamicArrayAccesses = 0;
            long linkedListAccesses = 0;

            for (int repetition = 0; repetition < REPETITIONS; repetition++) {
                DynamicArray array = createDynamicArray(values);
                LinkedList list = createLinkedList(values);

                array.resetAccessCount();
                list.resetAccessCount();

                long start = System.nanoTime();

                for (int index : indices) {
                    array.get(index);
                }

                long end = System.nanoTime();
                dynamicArrayTotalTime += end - start;

                start = System.nanoTime();

                for (int index : indices) {
                    list.get(index);
                }

                end = System.nanoTime();
                linkedListTotalTime += end - start;
                dynamicArrayAccesses += array.getAccessCount();
                linkedListAccesses += list.getAccessCount();
            }

            double dynamicArrayAverage = dynamicArrayTotalTime / REPETITIONS;
            double linkedListAverage = linkedListTotalTime / REPETITIONS;

            System.out.println("n = " + n);
            System.out.printf("Dynamic Array: %.0f ns%n", dynamicArrayAverage);
            System.out.println("Dynamic Array accesses: " + dynamicArrayAccesses / REPETITIONS);

            System.out.printf("Linked List: %.0f ns%n", linkedListAverage);
            System.out.println("Linked List accesses: " + linkedListAccesses / REPETITIONS);
            System.out.println();
        }
    }

    private static void runSearchWorkload() {
        System.out.println("Workload 2 - Search");
        System.out.println();

        for (int n : SIZES) {
            int[] values = generateValues(n);
            int[] searchValues = generateSearchValues(SEARCH_OPERATIONS);

            double dynamicArrayTotalTime = 0;
            double linkedListTotalTime = 0;
            long dynamicArrayComparisons = 0;
            long linkedListComparisons = 0;

            for (int repetition = 0; repetition < REPETITIONS; repetition++) {
                DynamicArray array = createDynamicArray(values);
                LinkedList list = createLinkedList(values);

                array.resetComparisonCount();
                list.resetComparisonCount();

                long start = System.nanoTime();

                for (int value : searchValues) {
                    array.contains(value);
                }

                long end = System.nanoTime();
                dynamicArrayTotalTime += end - start;

                start = System.nanoTime();

                for (int value : searchValues) {
                    list.contains(value);
                }

                end = System.nanoTime();
                linkedListTotalTime += end - start;

                dynamicArrayComparisons += array.getComparisonCount();
                linkedListComparisons += list.getComparisonCount();
            }

            double dynamicArrayAverage = dynamicArrayTotalTime / REPETITIONS;
            double linkedListAverage = linkedListTotalTime / REPETITIONS;

            System.out.println("n = " + n);
            System.out.printf("Dynamic Array: %.0f ns%n", dynamicArrayAverage);
            System.out.println("Dynamic Array comparisons: " + dynamicArrayComparisons / REPETITIONS);

            System.out.printf("Linked List: %.0f ns%n", linkedListAverage);
            System.out.println("Linked List comparisons: " + linkedListComparisons / REPETITIONS);
            System.out.println();
        }
    }

    private static int[] generateValues(int n) {
        Random random = new Random(SEED);
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt();
        }

        return values;
    }

    private static int[] generateIndices(int n, int count) {
        Random random = new Random(SEED);
        int[] indices = new int[count];

        for (int i = 0; i < count; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private static int[] generateSearchValues(int count) {
        Random random = new Random(SEED + 1);
        int[] values = new int[count];

        for (int i = 0; i < count; i++) {
            values[i] = random.nextInt();
        }

        return values;
    }

    private static DynamicArray createDynamicArray(int[] values) {
        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        return array;
    }

    private static LinkedList createLinkedList(int[] values) {
        LinkedList list = new LinkedList();

        for (int value : values) {
            list.add(0, value);
        }

        return list;
    }
}