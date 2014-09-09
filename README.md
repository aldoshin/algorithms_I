Personal History for Algorithms, Part I course
============

##Union Find
-----------------
The solution for Percolation does not deal with backwash and uses Union-Find algorithm

Makes use of __Weighted quick-union__: Rather than arbitrarily connecting the second tree to the first for union() in the quick-union algorithm, we keep track of the size of each tree and always connect the smaller tree to the larger. Program WeightedQuickUnionUF.java http://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html implements this approach


The Subset handles the case where the maximum size of RandomizedQueue object created is <= k by randomizing the array of string

Fast in collinear avoids subsets.

8 puzzle solved with A* algorithm
