/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guistack;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import stack.Stack;

/**
 *
 * @author Python Hacker
 */
public class StackTest extends Thread implements Observer {

    Stack stack;
    Scanner input = new Scanner(System.in);
    
    public StackTest(Stack stack){
        this.stack = stack;
    }
    
    @Override
    public void run(){
        try {
            System.out.println("************* :::: Menu :::: **************** ");
            System.out.println(" 1- Push Operation\n 2- Pop Operation\n 3- Top Operation");
            System.out.println(" 4- Empty Operation\n 5- IsEmpty Check\n 6- IsFull Check");
            System.out.print("Please Make a choice: ");
            int choice = input.nextInt();
            
            switch(choice){
                case 1:
                    System.out.print("Enter item to be pushed ");
                    this.stack.push(input.nextInt());
                    break;
                case 2:
                    System.out.println("Item poped is " + this.stack.pop());
                    break;
                case 3:
                    System.out.println("Top Item is " + this.stack.top());
                    break;
                case 4:
                    this.stack.empty();
                    System.out.println("Stack has been emptied successfully");
                    break;
                case 5:
                    System.out.println(this.stack.isEmpty());
                    break;
                case 6:
                    System.out.println(this.stack.isFull());
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Error Occured when running thread " + ex + "--" + this.stack);
        }
    }
   
    @Override
    public void update(Observable o, Object arg){
        try {
            System.out.println("Something happened in stack class this is TestStack");
            (new StackTest(this.stack)).start();
        } catch (Exception ex) {
           //Logger.getLogger(TestSTackClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
