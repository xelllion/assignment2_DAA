import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RANDOM_ACCESS_OPERATIONS = 10000;
    private static final int SEARCH_OPERATIONS = 1000;
    private static final int INSERT_REMOVE_OPERATIONS = 1000;
    private static final int REPETITIONS = 5;
    private static final int SEED = 67;

    public static void main(String[] args) {
        runRandomAccessWorkload();
        runSearchWorkload();
        runInsertionRemovalWorkload();
        runPriorityProcessingWorkload();
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

    private static void runInsertionRemovalWorkload() {
        System.out.println("Workload 3 - Insertion and Removal");
        System.out.println();

        for (int n : SIZES) {
            int[] values = generateValues(n);

            double arrayInsertBeginningTime = 0;
            double listInsertBeginningTime = 0;
            long arrayInsertBeginningMovements = 0;
            long listInsertBeginningAccesses = 0;

            double arrayRemoveBeginningTime = 0;
            double listRemoveBeginningTime = 0;
            long arrayRemoveBeginningMovements = 0;
            long listRemoveBeginningAccesses = 0;

            double arrayInsertMiddleTime = 0;
            double listInsertMiddleTime = 0;
            long arrayInsertMiddleMovements = 0;
            long listInsertMiddleAccesses = 0;

            double arrayRemoveMiddleTime = 0;
            double listRemoveMiddleTime = 0;
            long arrayRemoveMiddleMovements = 0;
            long listRemoveMiddleAccesses = 0;

            for (int repetition = 0; repetition < REPETITIONS; repetition++) {
                DynamicArray array = createDynamicArray(values);
                LinkedList list = createLinkedList(values);

                array.resetMovementCount();
                list.resetAccessCount();

                long start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    array.add(0, i);
                }

                long end = System.nanoTime();
                arrayInsertBeginningTime += end - start;
                arrayInsertBeginningMovements += array.getMovementCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    list.add(0, i);
                }

                end = System.nanoTime();
                listInsertBeginningTime += end - start;
                listInsertBeginningAccesses += list.getAccessCount();


                array = createDynamicArrayForRemoval(values);
                list = createLinkedListForRemoval(values);

                array.resetMovementCount();
                list.resetAccessCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    array.remove(0);
                }

                end = System.nanoTime();
                arrayRemoveBeginningTime += end - start;
                arrayRemoveBeginningMovements += array.getMovementCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    list.remove(0);
                }

                end = System.nanoTime();
                listRemoveBeginningTime += end - start;
                listRemoveBeginningAccesses += list.getAccessCount();


                array = createDynamicArray(values);
                list = createLinkedList(values);

                array.resetMovementCount();
                list.resetAccessCount();

                int middle = n / 2;

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    array.add(middle, i);
                }

                end = System.nanoTime();
                arrayInsertMiddleTime += end - start;
                arrayInsertMiddleMovements += array.getMovementCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    list.add(middle, i);
                }

                end = System.nanoTime();
                listInsertMiddleTime += end - start;
                listInsertMiddleAccesses += list.getAccessCount();


                array = createDynamicArrayForRemoval(values);
                list = createLinkedListForRemoval(values);

                array.resetMovementCount();
                list.resetAccessCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    array.remove(middle);
                }

                end = System.nanoTime();
                arrayRemoveMiddleTime += end - start;
                arrayRemoveMiddleMovements += array.getMovementCount();

                start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    list.remove(middle);
                }

                end = System.nanoTime();
                listRemoveMiddleTime += end - start;
                listRemoveMiddleAccesses += list.getAccessCount();
            }

            System.out.println("n = " + n);

            System.out.println("Insertion at beginning:");
            System.out.printf("Dynamic Array: %.0f ns, movements: %d%n",
                    arrayInsertBeginningTime / REPETITIONS,
                    arrayInsertBeginningMovements / REPETITIONS);
            System.out.printf("Linked List: %.0f ns, accesses: %d%n",
                    listInsertBeginningTime / REPETITIONS,
                    listInsertBeginningAccesses / REPETITIONS);

            System.out.println("Removal at beginning:");
            System.out.printf("Dynamic Array: %.0f ns, movements: %d%n",
                    arrayRemoveBeginningTime / REPETITIONS,
                    arrayRemoveBeginningMovements / REPETITIONS);
            System.out.printf("Linked List: %.0f ns, accesses: %d%n",
                    listRemoveBeginningTime / REPETITIONS,
                    listRemoveBeginningAccesses / REPETITIONS);

            System.out.println("Insertion in middle:");
            System.out.printf("Dynamic Array: %.0f ns, movements: %d%n",
                    arrayInsertMiddleTime / REPETITIONS,
                    arrayInsertMiddleMovements / REPETITIONS);
            System.out.printf("Linked List: %.0f ns, accesses: %d%n",
                    listInsertMiddleTime / REPETITIONS,
                    listInsertMiddleAccesses / REPETITIONS);

            System.out.println("Removal in middle:");
            System.out.printf("Dynamic Array: %.0f ns, movements: %d%n",
                    arrayRemoveMiddleTime / REPETITIONS,
                    arrayRemoveMiddleMovements / REPETITIONS);
            System.out.printf("Linked List: %.0f ns, accesses: %d%n",
                    listRemoveMiddleTime / REPETITIONS,
                    listRemoveMiddleAccesses / REPETITIONS);

            System.out.println();
        }
    }

    private static void runPriorityProcessingWorkload() {
        System.out.println("Workload 4 - Priority Processing");
        System.out.println();

        for (int n : SIZES) {
            int[] values = generateValues(n);

            double insertionTotalTime = 0;
            double extractionTotalTime = 0;
            long insertionComparisons = 0;
            long extractionComparisons = 0;
            boolean sorted = true;

            for (int repetition = 0; repetition < REPETITIONS; repetition++) {
                MinHeap heap = new MinHeap();

                heap.resetComparisonCount();

                long start = System.nanoTime();

                for (int value : values) {
                    heap.insert(value);
                }

                long end = System.nanoTime();

                insertionTotalTime += end - start;
                insertionComparisons += heap.getComparisonCount();

                heap.resetComparisonCount();

                start = System.nanoTime();

                int previous = heap.extractMin();

                while (heap.size() > 0) {
                    int current = heap.extractMin();

                    if (previous > current) {
                        sorted = false;
                    }

                    previous = current;
                }

                end = System.nanoTime();

                extractionTotalTime += end - start;
                extractionComparisons += heap.getComparisonCount();
            }

            System.out.println("n = " + n);

            System.out.printf("Insertion: %.0f ns%n",
                    insertionTotalTime / REPETITIONS);
            System.out.println("Insertion comparisons: "
                    + insertionComparisons / REPETITIONS);

            System.out.printf("Extraction: %.0f ns%n",
                    extractionTotalTime / REPETITIONS);
            System.out.println("Extraction comparisons: "
                    + extractionComparisons / REPETITIONS);

            System.out.println("Non-decreasing order: " + sorted);
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

    private static DynamicArray createDynamicArrayForRemoval(int[] values) {
        DynamicArray array = createDynamicArray(values);
        int requiredSize = INSERT_REMOVE_OPERATIONS + values.length / 2;

        while (array.size() < requiredSize) {
            array.add(0);
        }

        return array;
    }

    private static LinkedList createLinkedListForRemoval(int[] values) {
        LinkedList list = createLinkedList(values);
        int requiredSize = INSERT_REMOVE_OPERATIONS + values.length / 2;

        while (list.size() < requiredSize) {
            list.add(0, 0);
        }

        return list;
    }
}