# Assignment 2 — Algorithmic Analysis, Correctness and Performance Trade-offs

## 1. Overview

This project implements and analyzes three fundamental data structures in Java: a Dynamic Array, a Linked List, and a Min-Heap.

The purpose of the assignment is to study the correctness and performance of these data structures using both theoretical and experimental analysis. The project compares different operations using asymptotic notation, loop invariants, and benchmark experiments.

The implemented data structures support the following operations:

- **Dynamic Array:** `add(x)`, `add(index, x)`, `remove(index)`, `get(index)`, `contains(x)`
- **Linked List:** `add(x)`, `add(index, x)`, `remove(index)`, `get(index)`, `contains(x)`
- **Min-Heap:** `insert(x)`, `peekMin()`, `extractMin()`

The implementations are tested with empty structures, single and multiple elements, duplicate values, boundary cases, invalid indices where applicable, and large inputs.

## 2. Complexity Analysis

### Dynamic Array

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
|---|---|---|---|---|
| `add(x)` | Ω(1) | Θ(1) amortized | O(n) | O(n) during resize |
| `add(index, x)` | Ω(1) | Θ(n) | O(n) | O(n) during resize |
| `remove(index)` | Ω(1) | Θ(n) | O(n) | O(1) |
| `get(index)` | Θ(1) | Θ(1) | Θ(1) | O(1) |
| `contains(x)` | Ω(1) | Θ(n) | O(n) | O(1) |

`add(x)` normally places the new element directly at the end of the array. When the internal array is full, a larger array must be created and the existing elements must be copied. Therefore, a single insertion may take O(n), while repeated end insertions have Θ(1) amortized time.

`add(index, x)` may require shifting elements to the right. Insertion at the end requires no shifting, while insertion near the beginning may move almost all elements.

`remove(index)` shifts all elements after the removed position one place to the left. Removing the last element requires no shifting, while removing near the beginning requires Θ(n) movements.

`get(index)` uses direct array indexing, so its running time does not depend on the number of stored elements.

`contains(x)` checks elements sequentially. The value may be found immediately in the best case, while the average and worst cases require examining a number of elements proportional to n.

### Linked List

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
|---|---|---|---|---|
| `add(x)` | Ω(1) | Θ(n) | O(n) | O(1) |
| `add(index, x)` | Ω(1) | Θ(n) | O(n) | O(1) |
| `remove(index)` | Ω(1) | Θ(n) | O(n) | O(1) |
| `get(index)` | Ω(1) | Θ(n) | O(n) | O(1) |
| `contains(x)` | Ω(1) | Θ(n) | O(n) | O(1) |

In this implementation, `add(x)` traverses the list from the head to the last node before attaching the new node. Therefore, adding to an empty list is constant time, while adding to a non-empty list may require traversing up to n nodes.

`add(index, x)` inserts directly at the head when the index is 0. For other positions, the list must be traversed until the node before the requested index is reached.

`remove(index)` removes the head directly when the index is 0. Removing from another position requires traversal to the previous node.

`get(index)` starts at the head and follows references until it reaches the requested position. Unlike a Dynamic Array, a Linked List does not provide direct random access.

`contains(x)` searches sequentially through the nodes until the value is found or the end of the list is reached.

### Min-Heap

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
|---|---|---|---|---|
| `insert(x)` | Ω(1) | Θ(log n) | O(log n) | O(n) during resize |
| `peekMin()` | Θ(1) | Θ(1) | Θ(1) | O(1) |
| `extractMin()` | Ω(1) | Θ(log n) | O(log n) | O(1) |

`insert(x)` first places the new value at the end of the heap. It then moves the value upward while it is smaller than its parent. If the heap property is already satisfied, no swaps are required. In the worst case, the element moves from the bottom of the heap to the root.

`peekMin()` directly returns the root of the Min-Heap, which is stored at index 0. Therefore, it takes constant time.

`extractMin()` removes the root and replaces it with the last element. The replacement may then move downward through the heap until the heap property is restored. The height of a binary heap is Θ(log n), so the average and worst-case running times grow logarithmically.

## 3. Correctness

### Loop Invariant 1 — Dynamic Array Insertion

Operation: `add(index, value)`

The insertion algorithm shifts elements one position to the right, starting from the end of the array and moving toward the insertion index.

**Loop Invariant:**  
Before each iteration of the loop, all elements originally located after the current position have already been shifted one position to the right without changing their order.

**Initialization:**  
Before the first iteration, no elements have been shifted yet. The loop starts at the current size of the array, so the invariant is true.

**Maintenance:**  
During each iteration, the algorithm copies `data[i - 1]` to `data[i]`. This shifts one element exactly one position to the right. The relative order of the already shifted elements is preserved. Therefore, the invariant remains true for the next iteration.

**Termination:**  
The loop terminates when `i` is no longer greater than `index`. At this point, every element from the insertion index to the previous last element has been shifted one position to the right.

**Correctness:**  
After the loop terminates, `data[index]` is free. The new value is placed at this position and the size is increased. All previous elements remain in their original relative order, so the insertion operation is correct.


### Loop Invariant 2 — Min-Heap Insertion

Operation: `insert(value)`

The insertion algorithm first places the new value at the end of the heap and then repeatedly compares it with its parent.

**Loop Invariant:**  
Before each iteration of the loop, the heap property is satisfied everywhere except possibly between the current node and its parent.

**Initialization:**  
Before the loop begins, the original heap already satisfies the Min-Heap property. The new value is inserted at the end, so the only possible violation is between the new node and its parent. Therefore, the invariant is true.

**Maintenance:**  
If the parent is less than or equal to the current value, the heap property is satisfied and the loop stops. Otherwise, the current value is smaller than its parent, so the two values are swapped. After the swap, the violation can only exist between the new current position and its new parent. Therefore, the invariant is maintained.

**Termination:**  
The loop terminates when the current node reaches the root or when its parent is less than or equal to it.

**Correctness:**  
At termination, there is no violation between the current node and its parent. Since all other parent-child relationships already satisfy the heap property, the entire structure is a valid Min-Heap after insertion.

## 4. Experimental Setup

The performance of the implemented data structures is evaluated using four fixed workloads.

### Input Sizes

The following values of `n` are used:

- 100
- 1,000
- 10,000
- 100,000

Here, `n` represents the number of elements initially stored in the data structure, while `m` represents the number of operations performed during a workload.

### Workloads

**Workload 1 — Random Access**

Dynamic Array and Linked List are tested using 10,000 randomly generated indices. The `get(index)` operation is performed for every generated index.

**Workload 2 — Search**

Dynamic Array and Linked List are tested using 1,000 search values. The `contains(value)` operation is performed for every value.

**Workload 3 — Insertion and Removal**

Dynamic Array and Linked List are tested using 1,000 insertions and removals. The operations are performed both at index `0` and at the middle position `n / 2`.

**Workload 4 — Priority Processing**

A Min-Heap is tested by inserting `n` random integers and then extracting all `n` elements using `extractMin()`.

### Benchmarking Method

Each experiment is executed 5 times, and the average execution time is reported.

Execution time is measured using:

`System.nanoTime()`

The experiments use a fixed random seed:

`Random(67)`

Input data is generated before the timed section. Input generation and printing are not included in the measured execution time.

In addition to execution time, the experiments record the required operation-specific metrics, including element accesses, comparisons, and movements.

## 5. Results

## 6. Discussion

## 7. Design Recommendations

## 8. Conclusion