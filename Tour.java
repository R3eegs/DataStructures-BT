import java.util.*;
import java.awt.Graphics;

/**
 * This class is a specialized Linked List of Points that represents a
 * Tour of locations attempting to solve the Traveling Salesperson Problem
 * 
 * @author
 * @version
 */

public class Tour implements TourInterface
{
    // instance variables
    //Number of points in the Tour
    private int count;
    //Holds the first point in the Tour
    private ListNode front;
    //Holds the last point in the Tour
    private ListNode rear;
    
    // constructor
    public Tour()
    {
        count = 0;
        front = null;
        rear = null;
    }
        
    //return the number of points (nodes) in the list   
    public int size()
    {
        return count;
    }

    // append Point p to the end of the list
    public void add(Point p)
    {
        ListNode n = new ListNode(p);
        if(rear == null){
            front = n;
        } else{
            rear.setNext(n);
        }
        rear = n;
        count++;
    } 
    
    // print every node in the list 
    public void print()
    {   
        ListNode printer = front;
        for(int i = 0; i < count; count++){
            if(!(printer==null)){
                System.out.println(printer.getData().toString());
                printer = printer.getNext();
            }
        }
    }
    
    // draw the tour using the given graphics context
    public void draw(Graphics g)
    {
        if(front == null)
            return;
        ListNode printer = front;
        while(printer != null){
            g.fillOval( (int) (printer.getData().getX())-2, (int) (printer.getData()).getY()-2, 4, 4);
            printer = printer.getNext();
        }
        ListNode line = front;
        while(!(line == rear)){
            g.drawLine((int) (line.getData().getX()), (int) (line.getData().getY()),(int) (line.getNext().getData().getX()),(int) (line.getNext().getData().getY()));
            line = line.getNext();
        }
        g.drawLine((int) (front.getData().getX()), (int) (front.getData().getY()),(int) (rear.getData().getX()), (int) (rear.getData().getY()));
    }
    
    //calculate the distance of the Tour, but summing up the distance between adjacent points
    //NOTE p.distance(p2) gives the distance where p and p2 are of type Point
    public double distance()
    {
        if(front == null)
            return -1.0;
        double tD = 0;
        ListNode l = front;
        while(l.getNext() != null){
            tD += l.getData().distance(l.getNext().getData());
            l = l.getNext();
        }
        tD += l.getData().distance(front.getData());
        return tD;
    }

    // add Point p to the list according to the NearestNeighbor heuristic
    public void insertNearest(Point p)
    {   
        if(front == null){
            add(p);
            return;
        }
        
        ListNode c;
        ListNode tracker = front;
        double nearest = (front.getData().distance(p));
        for(c = front; c != null; c = c.getNext()){
            double d = (c.getData().distance(p));
            if(nearest >  d){
                tracker = c;
                nearest =  d;
            }          
        }
        if( tracker.getNext() == null){
            add(p);
            return;
        }
        c = new ListNode(p);
        c.setNext(tracker.getNext());
        tracker.setNext(c);
        count++;
    }
    
        
    // add Point p to the list according to the InsertSmallest heuristic
    public void insertSmallest(Point p)
    { 
        if(front == null){
            add(p);
            return;
        } 
        ListNode tracker = rear;
        
        ListNode insert = new ListNode(p);
        double min = insert.getData().distance(tracker.getData())+insert.getData().distance(front.getData());
        for(ListNode c = front; c.getNext() != null; c = c.getNext()){
            double tempD = insert.getData().distance(c.getData())+insert.getData().distance(c.getNext().getData());
            if( tempD < min){
                tracker = c;
                min = tempD;
            }
        }
        if( tracker.getNext() == null){
            add(p);
            return;
        }
        insert.setNext(tracker.getNext());
        tracker.setNext(insert);
        count++;
    }
    
    
    // This is a private inner class, which is a separate class within a class.
    private class ListNode
    {
        private Point data;
        private ListNode next;
        public ListNode(Point p, ListNode n)
        {
            this.data = p;
            this.next = n;
        }
        
        public ListNode(Point p)
        {
            this(p, null);
        }
        
        public void setNext(ListNode n){
            next = n;
        }
        
        public void setData(Point p){
            data = p;
        }
        
        public Point getData(){
            return data;
        }
        
        public ListNode getNext(){
            return next;
        }
    }
    
    
  

}