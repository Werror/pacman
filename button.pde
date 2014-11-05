class button {
  int x,y;
  int w = 120;
  int h = 40;
  String str;
  boolean rollover = false; // Is the mouse over the rect?
  boolean clicking = false; // Is the mouse clicked?
  
  private static final int RECT_CORNER_RAD = 7;
  private static final int TEXT_Y_OFFSET = 6;
  
  // constructor
  button(int x, int y, String str){
    this.x = x;
    this.y = y;
    this.str = str;
  }
  
  void display(int mx, int my){
    drawButton();
    rollover_and_click(mx, my);
  }
  
  void drawButton(){
    rectMode(CENTER); 
    if (clicking) fill(20);
    else if (rollover) fill (50);
    else fill(255);
    rect(x, y, w, h, RECT_CORNER_RAD);
    fill(0, 102, 153);
    textAlign(CENTER);
    textSize(20);
    text(str, x, y + TEXT_Y_OFFSET);
  }
  
  // Is a point inside the rectangle (for rollover)
  void rollover_and_click(int mx, int my) {
    if (mx > x - w/2 && mx < x + w/2 && my > y - h/2 && my < y + h/2) {
      rollover = true;
      if (mousePressed == true) clicking = true;
      else clicking = false;
    } else {
      rollover = false;
      
    }
  }
  
  void clicked(){}
}

class playButton extends button{
  playButton(int x, int y, String str){
    super(x, y, str);
  }
  
  void display(int mx, int my){ super.display(mx, my);}

  void clicked(){
    
  }
}

class quitButton extends button{
  quitButton(int x, int y, String str){
    super(x, y, str);
  }
  
  void display(int mx, int my){ super.display(mx, my);}

  void clicked(){
    exit();
  }
}
