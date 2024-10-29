# Discussion
## Part 1 - Benchmarking

#### Hotel California had the following Benchmark data:

JmhRuntimeTest.arrayMap: 0.227         
JmhRuntimeTest.avlTreeMap: 0.161          
JmhRuntimeTest.bstMap: 0.156         
JmhRuntimeTest.treapMap: 0.179

#### The Federalist Papers had the following Benchmark data:

JmhRuntimeTest.arrayMap: 0.235       
JmhRuntimeTest.avlTreeMap: 0.157          
JmhRuntimeTest.bstMap: 0.156         
JmhRuntimeTest.treapMap: 0.189

#### Moby Dick had the following Benchmark data:

JmhRuntimeTest.arrayMap: 0.240         
JmhRuntimeTest.avlTreeMap: 0.153          
JmhRuntimeTest.bstMap: 0.161         
JmhRuntimeTest.treapMap: 0.177
#### Pride and Prejudice had the following Benchmark data:

JmhRuntimeTest.arrayMap: 0.235         
JmhRuntimeTest.avlTreeMap: 0.159          
JmhRuntimeTest.bstMap: 0.159         
JmhRuntimeTest.treapMap: 0.187

#### Analysis and Observations

Of all the data structures, ArrayMap consistently performs the worst due to its O(n) time complexity for search and insert operations with scores greater than 0.2. In contrast, AVL Tree, Binary Search Tree (BST), and Treap perform within the range of 0.15 to 0.19, reflecting their typical O(log n) runtimes, though BST and Treap can degrade to O(n) in worst cases.

BST outperforms AVL trees in three of the four text files, which may be explained by the added comparisons present in AVL trees. By construction, AVL Trees need to maintain strict balance. Though guaranteeing logarithmic height, this constant balancing creates overhead from rotations during insertions and deletions. This overhead, especially with frequent rotations due to varied vocabulary, can outweigh the benefits of a balanced tree in smaller datasets. In Moby Dick, which has significantly more words, AVL's balancing proves marginally advantageous, explaining its better performance compared to BST.

Moreover, a binary search tree will only approach its worst case linear time if insertions happen in an ordered fashion. BSTs can perform well with diverse word distributions, as they avoid skewed structures, and the lack of balancing overhead gives them an edge over AVL Trees, as well as treaps, in smaller datasets. The diversity of words and their frequency prevents long chains of nodes that could cause imbalances. This explains why BST is performing slightly better or relatively close to AVL tree in the text files. The same reasoning explains why BST outperforms Treap: Treap adds complexity through random priorities and rotations, which can introduce inefficiency.

Finally, AVL Trees consistently outperform Treaps because AVL's O(log n) operations are guaranteed, while Treap's average O(log n) performance can degrade to O(n), particularly noticeable in shorter benchmark iterations.

## Part 2 - Selection Problem

### Strategy 1:
Strategy 1 is correct. The max heap removal will always return the best element, so calling the removal k amount of times will give the kth best element as once an element is removed, the priority queue will swap values and ensure that the next "best" element is at the root and ready to be accessed.

Time Complexity: O(n log n)

A single insertion in strategy 1 is done in O(log n) time, since this operation must happen n times we say that insertion of all elements happens in O(n log n) time. For the first query,
returning the kth best element happens in O(k log n) time as removal in priority queues are O(log n) and this must happen k amount of times. The removal run time does not matter as k will always be less than or equal to n, thus making the time complexity O (n log n).

Space Complexity: O(n)

The input space is O(n) since n elements need to be stored in the max heap. The auxillary space is O(1) as no additional data structures are used. Thus, the space complexity is O(n) + O(1) = O(n).

### Strategy 2:

Strategy 2 is correct. By using a min heap to store the largest k elements, it effectively ensures that the smallest of these k elements (which is the kth largest overall) is always accessible at the root of the heap. As it processes the remaining elements in the array, it only retains those that are larger than the smallest in the heap, maintaining the correct subset of largest values.

Time Complexity: O(n log n)

Inserting the initial k elements into a binary min-heap takes O(k log k) because inserting each element into a heap takes O(log k), and this operation is repeated for each of the k elements. For each of the remaining n - k elements, both the removal and insertion process will take O(log k) time. This gives a big-Oh of O((n - k) log k) time. Summing these two complexities and expressing in big-Oh form we get O(n log k). Because k in the worst case is equal to n, we get that the time complexity is O(n log n).

Space Complexity: O(n)

The input space is O(n) since n elements need to be stored in the max heap. The auxiliary space for the min heap is O(1) because comparing and swapping only require temporary variables used. The amount of space these temporary variables take up is not contingent on the size of the input because the variables used at any one time is constant.