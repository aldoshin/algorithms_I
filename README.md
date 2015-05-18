Personal History for Algorithms, Part I course
============
All solutions make use of the library http://algs4.cs.princeton.edu/code/algs4-data.zip
#### In order to fulfill Coursera's honor code all source code was move to private repository in [Bitbucket](https://bitbucket.org/aldoshin/algorithms_i)

Union Find
-----------------
The solution for Percolation does not deal with backwash and uses Union-Find algorithm

Makes use of __Weighted quick-union__: Rather than arbitrarily connecting the second tree to the first for union() in the quick-union algorithm, we keep track of the size of each tree and always connect the smaller tree to the larger. Program WeightedQuickUnionUF.java http://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html implements this approach


Randomized Queue
----------------------
The Subset handles the case where the maximum size of RandomizedQueue object created is <= k by randomizing the array of string

Collinear Points
-------------------------------
Draw lines for sets of points that are collinear using Comparator and then sorting them by their slopes

Fast in collinear avoids subsets.

8 puzzle
-----------------------------
8 puzzle solved with A* algorithm. Using PriorityQueue and the manhattan criteria.

Kd-tree
---------------------------------
Solves the nearest-neighbor problem and collecting the set of points inside a Rectangle (range problem) using Kd-tree
