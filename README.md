# Random-Maze-Generator
Generates a random maze based on user dimensional input

# Sample input of 12
![](https://i.imgur.com/g3VJrtg.png)

This uses a Disjoint Set to generate the maze, by randomly selecting two adjacent cells in a grid, and joining the set together if they are not within the same set.

This continues until every cell is in one set, signifying that each cell can be reached through some path.
By joining all of the cells together, the last cell (exit) is reachable from the first cell (entrance).
