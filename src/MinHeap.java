public class MinHeap {
    private int[] data;
    private int size;
    private long comparisonCount;

    public MinHeap() {
        data = new int[10];
        size = 0;
        comparisonCount = 0;
    }

    public int size() {
        return size;
    }

    public void insert(int value) {
        ensureCapacity();

        data[size] = value;
        int current = size;
        size++;

        while (current > 0) {
            int parent = (current - 1) / 2;

            comparisonCount++;

            if (data[parent] <= data[current]) {
                break;
            }

            swap(parent, current);
            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        int min = data[0];

        data[0] = data[size - 1];
        size--;

        int current = 0;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;

            if (left < size) {
                comparisonCount++;

                if (data[left] < data[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                comparisonCount++;

                if (data[right] < data[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == current) {
                break;
            }

            swap(current, smallest);
            current = smallest;
        }

        return min;
    }

    public long getComparisonCount() {
        return comparisonCount;
    }

    public void resetComparisonCount() {
        comparisonCount = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }
    }

    private void swap(int first, int second) {
        int temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }
}