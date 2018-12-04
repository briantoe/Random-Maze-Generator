/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

/**
 *
 * @author Brian T
 */
public class Cell {

    private Wall wall;
    private int coordinate; // cell number ranging from 0 to (row * col) - 1 of the maze

    Cell(int coordinate) {
        wall = Wall.NEITHER; // neither wall is deleted
        this.coordinate = coordinate;
    }

    Cell(Cell cell) {
        wall = cell.getWall();
        this.coordinate = cell.coordinate;
    }

    void setWall(Wall status) {
        // if local variable wall is already assigned a horizontal or vertical enum AND if value of status is not equal to value of wall
        
        if ((wall == Wall.HORIZONTAL || wall == Wall.VERTICAL) && (status != wall)) {
            wall = Wall.BOTH; // both walls are deleted
        } else {
            wall = status;
        }
    }

    Wall getWall() {
        return wall;
    }

    int getCoordinate() {
        return coordinate;
    }
}
