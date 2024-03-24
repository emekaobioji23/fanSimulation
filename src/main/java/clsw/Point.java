/*
 * this class represents a Point on a circumference of a circle
 * 
 */
package clsw;

/**
 *
 * @author emeka
 */
public class Point implements Comparable{
    int x2,y2;
    Point(int x2,int y2){
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int compareTo(Object o) {
        if(x2 == ((Point)o).x2 && y2==((Point)o).y2){
            return 1;
        }else{
            return 0;
        }
    }
    
}
