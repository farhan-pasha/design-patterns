package main.java.solid_principles;

/*
    -Should be able to substitute a sub-class(derived class) for a base class
    -if you violate it, will result in incorrect code through inheritance.
    -Design patten used: Factory
 */


 class Rectangle{
    protected int width, height;

     Rectangle() {

    }

    Rectangle(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea(){
        return height*width;
    }
}

 class Square extends Rectangle{

    public Square(){

    }

    public Square (int side){
        width = height = side;
    }

    //Suppose we enforce the square to be square as seen below:

     @Override
     public void setHeight(int height) {
         super.setHeight(height);
         super.setWidth(height);
     }

     @Override
     public void setWidth(int width) {
         super.setHeight(width);
         super.setWidth(width);
     }


}

public class LiskovSubstitutionPrinciple {

     public static void useIt(Rectangle r) {
         int height = r.getHeight();
         r.setWidth(10);
         System.out.println("Expected Area: "+ height*10 + " Actual Area: " +r.getArea());
     }


     public static void main (String[] args){

         Rectangle rectangle = new Rectangle();
         rectangle.setHeight(5);
         useIt(rectangle);//Expected Area: 50 Actual Area: 50

         Rectangle square = new Square(); //New square is also a rectangle by virtue of inheritance
         square.setHeight(5);
         useIt(square);//Expected Area: 50 Actual Area: 100.

         /*
            As seen above the overridden setter methods in square contributed in breaking the Liskov Substitution
            Principle, making the program to return undesirable outputs. Use Factory design pattern instead!
         */

         Rectangle factoryRectangle = RectangleFactory.newRectagle(5,5);
         useIt(factoryRectangle);//Expected Area: 50 Actual Area: 50

         Rectangle factorySquare = RectangleFactory.newSquare(5);
         useIt(factorySquare);//Expected Area: 50 Actual Area: 50


     }
}

//Factory Design Pattern
class RectangleFactory {
    public static Rectangle newRectagle(int width, int height){
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side){
        return new Rectangle(side, side);
    }
}
