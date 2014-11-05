class Packman {
  int x,y;
  int bodyR;
  int eyeR;
  float theta = 0.0;
  float mouseAngle;
  
  Packman (int x, int y, int r){
    this.x = x;
    this.y = y;
    this.bodyR = r;
    this.eyeR = r/10;
    this.mouseAngle = 2*PI-QUARTER_PI;
  }
  
  void rotatePackman(){
  }
  
  void display(){
  }
}

class splashPackman extends Packman{
  splashPackman(int x, int y, int r){
    super(x, y, r);
  }
  
  void rotatePackman(){
    translate(x,y);
    rotate(theta*QUARTER_PI);
    fill(245, 123, 212);
    arc(0, 0, bodyR, bodyR, 0, mouseAngle, PIE);
    fill(255);
    ellipse(0, 0 - bodyR/4, eyeR, eyeR);
    theta += 0.065;
  }
}

class gamePackman extends Packman{
  int xInd, yInd;
  
  gamePackman(int x, int y, int xInd, int yInd, int r){
    super(x, y, r);
    this.xInd = xInd;
    this.yInd = yInd;
  }
  
  void display(){
    fill(255);
    arc(x, y, bodyR, bodyR, 0, mouseAngle, PIE);
    fill(0);
    
    mouseAngle = 2*PI - sin(theta*QUARTER_PI)*QUARTER_PI;
    theta += 0.5;
    
    translate(x,y);
    rotate(mouseAngle);
    ellipse(0 + bodyR/8, 0 - bodyR/4, eyeR, eyeR);
  }
  
  void setXpos(int x){
    this.x = x;
  }
  
  void setYpos(int y){
    this.y = y;
  }
}

