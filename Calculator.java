/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mobileapplication;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 * @author OM
 */
public class Calculator extends MIDlet implements CommandListener {
    //Declaration of variables of different classes
    private float num1,num2,num3;
    private Display display;
    private String selectedstring;
    private Form main;
    private Form operation;
    private Command exit;
    private Command clear;
    private Command select;
    private Command back;
    private Command result;
    private TextField a;
    private TextField b;
    private TextField f;
    private int ChoiceGroupAppend;
    private ChoiceGroup options;
    
    //Initialization of different values and display 
    public Calculator()
    {
        //Creating Choice group list
        options = new ChoiceGroup("Main Form",Choice.EXCLUSIVE);
        options.append("ADDITION",null);
        options.append("SUBTRACTION",null);
        options.append("MULTIPLICATION",null);
        options.append("DIVISION",null);
        
        display = Display.getDisplay(this);
        
        //Creating Forms
        main = new Form("MAIN FORM");
        ChoiceGroupAppend=main.append(options);
        operation = new Form("OPERATION");
       
        //Initializing different command buttons 
        exit = new Command("EXIT",Command.EXIT,0);
        select = new Command("SELECT",Command.OK,0);
        back = new Command("BACK",Command.BACK,0);
        result = new Command("RESULT",Command.OK,0);
        clear = new Command("CLEAR",Command.OK,0);
        //Adding command buttons to different forms
        main.addCommand(exit);
        main.addCommand(select);
        operation.addCommand(result);
        operation.addCommand(back);
        operation.addCommand(clear);
        main.setCommandListener(this);
        operation.setCommandListener(this);
        
        display.setCurrent(main);
    }

    public void startApp() {
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command c,Displayable d)
    {
        if(d==main) //Implementing command Listener for Main Form
        {
            if(c==exit)
            {
                notifyDestroyed();
            }
            else if(c==select)
            {
                selectedstring = new String(options.getString(options.getSelectedIndex()));
                operation.deleteAll();
                a = new TextField("Enter First Number","",40,TextField.DECIMAL);
                b = new TextField("Enter Second Number","",40,TextField.DECIMAL);
                f = new TextField("Result","",40,TextField.DECIMAL);
                operation.append(a);
                operation.append(b);
                operation.append(f);
                display.setCurrent(operation);
            }
        }
        else if(d==operation)//Implementing Command Listener for Operation Form
        {
            if(c==back)
            {
                display.setCurrent(main);
            }
            if(c==clear)
            {
                operation.deleteAll();
                a = new TextField("Enter First Number","",40,TextField.DECIMAL);
                b = new TextField("Enter Second Number","",40,TextField.DECIMAL);
                f = new TextField("Result","",40,TextField.DECIMAL);
                operation.append(a);
                operation.append(b);
                operation.append(f);
                display.setCurrent(operation);
            }
            else if(c==result)
            {
                num1 = Float.parseFloat(a.getString());
                num2 = Float.parseFloat(b.getString());
                if(selectedstring.equals("ADDITION"))
                    num3=num1+num2;
                else if(selectedstring.equals("SUBTRACTION"))
                    num3=num1-num2;
                else if(selectedstring.equals("MULTIPLICATION"))
                    num3=num1*num2;
                else
                    num3=num1/num2;
               f.setString(String.valueOf(num3));
               display.setCurrent(operation);
            }
        }
    }
}
