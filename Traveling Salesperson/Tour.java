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
    // Instance variables
    //Number of points in the Tour
    private int count;
    //Holds the first point in the Tour
    private ListNode front;
    //Holds the last point in the Tour
    private ListNode rear;
    
    // Constructor of the Tour
    public Tour()
    {
        //Sets the size of the tour and the first and last point 
        //before any point is added
        count = 0;
        front = null;
        rear = null;
    }
        
    //Return the number of points (nodes) in the list   
    public int size()
    {
        return count;
    }

    // Append Point p to the end of the list
    public void add(Point p)
    {
        //creates ListNode n to hold Point p
        ListNode n = new ListNode(p);
        //Checks if the list is empty by checking rear
        if(rear == null){
            //Sets front to n to set it as the first point if the list is empty
            front = n;
        } else{
            //If the list isn't empty, this n appends it to the end of the list
            rear.setNext(n);
        }
        //Sets rear to n to make sure that it is the last point in the list
        rear = n;
        //Increases the size of the list
        count++;
    } 
    
    /* This inserts a ListNode between two ListNodes
     * This works by replacing f's next variable with r instead of re
     * Then it sets r's next variable to re to make sure that re is still accessible
     * Obviously, it also increments size to show that a point has been added
     */
    public void addBetween(ListNode r, ListNode f, ListNode re)
    {
        f.setNext(r);
        r.setNext(re);
        count++;
    }
    
    /*
     * This method removes a ListNode from between two ListNodes
     * This is performed by just setting f's next to re to get 
     * rid of the ListNode/s in between them
     * Obviously, it also deincrements the size to show that a point has been removed
     */
    public void remove(ListNode f, ListNode re)
    {
        f.setNext(re);
        count--;
    }
    
    // print every node in the list 
    public void print()
    {   
        //This is a copy of the front variable to instantiate through the list
        for(ListNode printer = front; printer != null; printer = printer.getNext()){
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
            g.fillOval( (int) (printer.getData().getX()), (int) (printer.getData()).getY(), 4, 4);
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
            return 0;
        
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
         if(front == null)
         {
             add(p);
             return;
         }
         ListNode c = front;
         ListNode track = c;
         double nearest = front.getData().distance(p);
         while(c.getNext() != null)
         {
             c = c.getNext();
             if(nearest > c.getData().distance(p))
             {
                 track = c;
                 nearest = c.getData().distance(p);
             }
             
         }
         if(track.getNext() == null)
         {
             add(p);
             return;
         }
         ListNode temp = track.getNext();
         ListNode lNode = new ListNode(p);
         track.setNext(lNode);
         lNode.setNext(temp);
         count++;
        
    }
    public void insertSmallest(Point p)
    { 
        if(front == null || front.getNext() == null)
        {
         add(p); 
         return;
        }
        
        ListNode pNode = new ListNode(p);
        double nearD = 0;
        ListNode trackF = front;
        ListNode trackR = front.getNext();
        ListNode c = front;
        
        while(c != null){
            ListNode l = c.getNext();
            addBetween(pNode, c, l);
            //double nd = distance();
            double nd = 0;
            if(l != null){
                nd = c.getData().distance(p) + l.getData().distance(p) - c.getData().distance(l.getData());
            }else {
                nd = front.getData().distance(p) + rear.getData().distance(p) - front.getData().distance(rear.getData());
            }
            
            
            if(nearD == 0)
                {
                    nearD = nd;
                }
            if(nd < nearD)
                {
                    nearD = nd;
                    trackF = c;
                    trackR = l;
                    remove(c, l);
                }
            else
                {
                    remove(c, l);
                }
             c = c.getNext();
            }
           
        
        if(trackR == null)
        {
            add(p);
            return;
        }
        addBetween(pNode, trackF, trackR);
        
        }
    
    // This is a private inner class, which is a separate class within a class.
    private class ListNode
    {
        private Point data;
        private ListNode next;
        /*Constructs a ListNode that holds the point it will represent in the list
         * It also holds a connection to another ListNode that represents 
         * the 
         */
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
        
        public Point getData(){
            return data;
        }
        
        public ListNode getNext(){
            return next;
        }
        public void setData(Point p)
        {
            data = p;
        }
    }
    
    
  

}