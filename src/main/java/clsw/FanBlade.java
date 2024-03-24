/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clsw;

/**
 *
 * @author emeka
 */
public class FanBlade {
    //coordinates x2, y2 of the end point of the blade from the centre at the rotor
    public int x2,y2;
    //the rest angle of the blade from the 0 degree going anti-clockwise
    public int a;
    
    public FanBlade(int x2, int y2, int a){
        this.x2 = x2;
        this.y2= y2;
        this.a = a;
    }
}
