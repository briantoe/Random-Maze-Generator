package maze;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brian T
 */
public enum Wall {
    NEITHER, VERTICAL, HORIZONTAL, BOTH;
    // imagine wall like this _|
}
// 0 = neither wall is deleted
// 1 = vertical right wall is deleted
// 2 = horizontal bottom wall is deleted
// 3 = both walls are deleted

