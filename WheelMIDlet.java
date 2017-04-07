/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package learningj2me;
//import java.lang.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 * @author Siddharth Arora
 */

/* Midlet Class*/
public class WheelMIDlet extends MIDlet {

    private final Display display;
    private final CircleCanvas c;
    
   public WheelMIDlet()
   {
    display = Display.getDisplay(this);
    c = new CircleCanvas(this);
   }
    public void startApp()
    {
        display.setCurrent(c);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    public void exitMIDlet()
  {
    destroyApp(false);
    notifyDestroyed();
  }
}

class CircleCanvas extends Canvas implements CommandListener {

private final WheelMIDlet midlet;
private int start_x,start_y,current_x,current_y;
int code,diff;
private final Command exit;
//arcOne/Two/Three define where this particular section of circle begins to be drawn from
int arcOne;
int arcTwo;
int arcThree;
//key_spinSpeed controls the amount by which arc shifts
int key_spinSpeed;


CircleCanvas(WheelMIDlet midlet){
    this.midlet=midlet;
    exit = new Command("Exit", Command.EXIT, 1);
    addCommand(exit);
    setCommandListener(this);
    arcOne = 0;
    arcTwo = 120;
    arcThree = 240;
    key_spinSpeed = 0;
}
int modSub(int a , int b , int mod) {
    return (a - b) % mod;
}
 
/*
paint method to redraw circle after shifting the arcs by key_spinSpeed amount
*/
public void paint(Graphics g){
   
    g.setColor(255,255,255);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(255,0,0);
    g.fillArc(getWidth()/2-100, getHeight()/2-100, 200, 200, arcOne%360, 120);
    g.setColor(0,255,0);
    g.fillArc(getWidth()/2-100, getHeight()/2-100, 200, 200, arcTwo%360, 120);
    g.setColor(0,0,255);
    g.fillArc(getWidth()/2-100, getHeight()/2-100, 200, 200, arcThree%360, 120);
     
    /*max method so that the key_spinSpeed is never negative and min method so that key_spinSpeed does not cross cap speed*/
    
    key_spinSpeed = Math.max(key_spinSpeed , 0);
    key_spinSpeed = Math.min(key_spinSpeed, 70);
    System.out.println("key speed = " + key_spinSpeed);
    System.out.println("mouse speed = " + key_spinSpeed);
    
    /*For handling when UP key is pressed*/
    
    if(code==-1)
    {      
        arcOne = modSub(arcOne, key_spinSpeed, 360); arcTwo = modSub(arcTwo, key_spinSpeed, 360); arcThree = modSub(arcThree, key_spinSpeed, 360);
           
        try {
                  
                  Thread.sleep(1000/(key_spinSpeed + 1));
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
           
           
    }
    
    /*For handling when DOWN key is pressed*/
    else if(code==-2)
    {
        
        
                arcOne = modSub(arcOne, key_spinSpeed, 360); arcTwo = modSub(arcTwo, key_spinSpeed, 360); arcThree = modSub(arcThree, key_spinSpeed, 360);
               try {
                   Thread.sleep(2000/(key_spinSpeed+ 1));
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
           
    }
    
    /*For handling when mouse dragged from left to right*/
    else if(diff>0)
    {
            arcOne =modSub(arcOne,key_spinSpeed,360); arcTwo =modSub(arcTwo,key_spinSpeed,360); arcThree = modSub(arcThree, key_spinSpeed,360);

               try {
                   Thread.sleep(1000/(1 + key_spinSpeed));
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
           
    }
    
    /*For handling when mouse dragged from right to left*/
    else if(diff<0)
    {
            arcOne =modSub(arcOne,key_spinSpeed,360); arcTwo =modSub(arcTwo,key_spinSpeed,360); arcThree = modSub(arcThree, key_spinSpeed,360);
        
               try {
                   if(key_spinSpeed!=0)
                   Thread.sleep(1000/(1+key_spinSpeed));
                   else
                       Thread.sleep(600);
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
           
    }
       
    repaint();
    
}
protected void keyPressed(int keyCode){
    
    switch (keyCode){
        //When the UP button is pressed, the wheel spins forward 3 degrees with increased key_spinSpeed
        case -1:
                    if(key_spinSpeed<120)
                    key_spinSpeed=key_spinSpeed+3;
                    this.code=keyCode;      
                    break;
        
        //When the DOWN button is pressed, the wheel spins forward 3 degrees with decreased key_spinSpeed
        case -2:
                    if(key_spinSpeed>0)
                      key_spinSpeed=key_spinSpeed-3;
                    this.code=keyCode;
                    break;
                
    }
    
}

//For getting starting position of mouse click
protected void pointerPressed(int x,int y)
{
    start_x=x;
    start_y=y;
}

//For getting position of mouse click when mousepress is released
protected void pointerReleased(int x,int y)
{
    current_x=x;
    current_y=y;
    diff = current_x-start_x;
    
    if(current_x>=0||current_x<=getWidth()) //To check whetehr mouse is in the canvas boudary
    {
    if(diff>0)
    {
        if(key_spinSpeed<120)
        key_spinSpeed=key_spinSpeed+diff;
    }
    else
    {   
        if(diff<0&&key_spinSpeed>0)
        key_spinSpeed=key_spinSpeed-Math.abs(diff);
        else
            key_spinSpeed=0;
    }
    }
}

//EXIT command Implemented
public void commandAction(Command c,Displayable d)
{
    if(c==exit)
    {
        midlet.exitMIDlet();
    }
}
}

