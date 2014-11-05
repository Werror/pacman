import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class sketch_121218a extends PApplet {

class Packman {
  int x,y;
  int bodyR;
  int eyeR;
  float theta = 0.0f;
  float mouseAngle;
  
  Packman (int x, int y, int r){
    this.x = x;
    this.y = y;
    this.bodyR = r;
    this.eyeR = r/10;
    this.mouseAngle = 2*PI-QUARTER_PI;
  }
  
  public void rotatePackman(){
  }
  
  public void display(){
  }
}

class splashPackman extends Packman{
  splashPackman(int x, int y, int r){
    super(x, y, r);
  }
  
  public void rotatePackman(){
    translate(x,y);
    rotate(theta*QUARTER_PI);
    fill(245, 123, 212);
    arc(0, 0, bodyR, bodyR, 0, mouseAngle, PIE);
    fill(255);
    ellipse(0, 0 - bodyR/4, eyeR, eyeR);
    theta += 0.065f;
  }
}

class gamePackman extends Packman{
  int xInd, yInd;
  
  gamePackman(int x, int y, int xInd, int yInd, int r){
    super(x, y, r);
    this.xInd = xInd;
    this.yInd = yInd;
  }
  
  public void display(){
    fill(255);
    arc(x, y, bodyR, bodyR, 0, mouseAngle, PIE);
    fill(0);
    
    mouseAngle = 2*PI - sin(theta*QUARTER_PI)*QUARTER_PI;
    theta += 0.5f;
    
    translate(x,y);
    rotate(mouseAngle);
    ellipse(0 + bodyR/8, 0 - bodyR/4, eyeR, eyeR);
  }
  
  public void setXpos(int x){
    this.x = x;
  }
  
  public void setYpos(int y){
    this.y = y;
  }
}


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
  
  public void display(int mx, int my){
    drawButton();
    rollover_and_click(mx, my);
  }
  
  public void drawButton(){
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
  public void rollover_and_click(int mx, int my) {
    if (mx > x - w/2 && mx < x + w/2 && my > y - h/2 && my < y + h/2) {
      rollover = true;
      if (mousePressed == true) clicking = true;
      else clicking = false;
    } else {
      rollover = false;
      
    }
  }
  
  public void clicked(){}
}

class playButton extends button{
  playButton(int x, int y, String str){
    super(x, y, str);
  }
  
  public void display(int mx, int my){ super.display(mx, my);}

  public void clicked(){
    
  }
}

class quitButton extends button{
  quitButton(int x, int y, String str){
    super(x, y, str);
  }
  
  public void display(int mx, int my){ super.display(mx, my);}

  public void clicked(){
    exit();
  }
}

class cell{
  float xPos,yPos;
  int xInd, yInd;
  int cellSize;
  boolean wall = false;
  boolean visited = false;
  gem thisGem = null;
  
  int neighborValid = -1;
  Integer[] neighborX = new Integer[4];
  Integer[] neighborY = new Integer[4];
  // direction step
  // 1 = x + 1 east
  // 2 = y + 1 south
  // 3 = x - 1 west
  // 4 = y - 1 north
  Integer[] step = new Integer[4];
  
  cell(float xPos, float yPos, int xInd, int yInd, int cellSize, boolean wall){
    this.xPos = xPos + cellSize/2;
    this.yPos = yPos + cellSize/2;
    this.xInd = xInd;
    this.yInd = yInd;
    this.cellSize = cellSize;
    this.wall = wall;
    this.visited = false;
  }
  
  public void display(){
    rectMode(CENTER);
//    stroke(0);
    if (wall) fill(0);
    else fill(100, 192, 10);
    rect(xPos, yPos, cellSize, cellSize);
    
    if (thisGem != null)
      thisGem.display();
  }
  
  public void setNeighborValid(int validNum){
    this.neighborValid = validNum;
  }
  
  public void setNeighborXY(int index, int x, int y){
    this.neighborX[index] = x;
    this.neighborY[index] = y;
  }
  
  public void setDirectionStep(int index, int dir){
    this.step[index] = dir;
  }
  
  public int getNeighborX(int index){
    return this.neighborX[index];
  }
  
  public int getNeighborY(int index){
    return this.neighborY[index];
  }
  
  public int getDirectionStep(int index){
    return this.step[index];
  }
  
  public void setWall(){
    this.wall = true;
  }
  
  public void setPath(){
    this.wall = false;
  }
  
  public void setGem(float thresh){
    float randVal = random(1,3);
    if (randVal < thresh)
      thisGem = new gem(xPos, yPos, cellSize);
//    else
//      thisGem = null;
  }
  
  public boolean getWall(){
    return this.wall;
  }
  
  public boolean isWall(){
    if (this.wall == true)
      return true;
    return false;
  }
  
  public void findGem(){
    if (this.thisGem != null)
      thisGem.setHit();
  }
  
  public boolean isGem(){
    if (this.thisGem != null)
      return true;
    return false;
  }
}

class circle {
  float x,y;
  float r;
  
  // constructor for splash screen
  circle(float x, float y, float r) {
    this.x = x;
    this.y = y;
    this.r = r;
//    ellipse(x, y, r, r); // not need to show it here
  }
  
  public void oscillate(float oscY){
//    println("trying to oscillate the circle");
    y += oscY;
    fill(255);
    ellipse(x, y, r, r);
  }
}

class cloud {
  int x,y;
  ArrayList circles;
  float theta = 0.0f;
  private static final int NUM_CIRCLE = 19; // number of circle to draw for the cloud
  
  private static final int CLOUDR_MIN = 50; // the min radius for each circle in the cloud
  private static final int CLOUDR_MAX = 180; // the max radius
  private static final int CLOUDW_MIN = 140; // the min x position for each circle in the cloud
  private static final int CLOUDH_MAX = 50; // the max x position

  
  private static final float CLOUDYOSC = 1.5f; // the oscillation range of the cloud
  
  // constructor for splash screen
  cloud(int x, int y) {
    this.x = x;
    this.y = y;
    this.circles = new ArrayList();
    for (int i = 0; i < NUM_CIRCLE; i++){
      circles.add(new circle(random(x - CLOUDW_MIN, x + CLOUDW_MIN), random(y - CLOUDH_MAX, y + CLOUDH_MAX), random(CLOUDR_MIN, CLOUDR_MAX)));
    }
  }
  
  // lets oscillates the cloud
  public void oscillate(){
//    println("trying to oscillate the cloud");
    float cloudY = (sin(theta)) * CLOUDYOSC;
    for (int i = 0; i < NUM_CIRCLE; i++){
      circle c = (circle) circles.get(i);
      c.oscillate(cloudY);
    }
    theta += 0.05f;
//    println(cloudY);
  }
}

class gamescrn {
  boolean active = false;
  int mazeCellSize = 20;
  int wNumCell;
  int hNumCell;
  maze randMaze;
  int currentCellXInd = 1;
  int currentCellYInd = 1;
  gamePackman packman;
  int packmanR = mazeCellSize;
  
  gamescrn() {
    for(; mazeCellSize < 100; mazeCellSize += 4){
      int tmpW = width/mazeCellSize;
      int tmpH = height/mazeCellSize;
      if(tmpW%2 == 1 && tmpH%2 == 1){
        this.wNumCell = tmpW;
        this.hNumCell = tmpH;
        break;
      }
      else{
        this.wNumCell = width/mazeCellSize;
        this.hNumCell = height/mazeCellSize;
      }
    }
    randMaze = new maze(mazeCellSize, wNumCell, hNumCell);
    randMaze.mazeInit();
    packman = new gamePackman(xInd2CenterCoor(currentCellXInd), yInd2CenterCoor(currentCellYInd), currentCellXInd, currentCellYInd, packmanR);
    this.active = true;
  }
  
  public void display(){
    randMaze.display();
    packman.display();
  }
  
  public int xInd2CenterCoor(int xInd){
    return xInd * mazeCellSize + mazeCellSize/2;
  }
  
  public int yInd2CenterCoor(int yInd){
    return yInd * mazeCellSize + mazeCellSize/2;
  }
  
  public void movePackman(){
    if (randMaze.getMazeGenDone()){
      int dir = getMouseDir(mouseX, mouseY, pmouseX, pmouseY);
      if (dir == 1 && currentCellXInd < wNumCell - 1 && !randMaze.isWallXY(currentCellXInd + 1, currentCellYInd))
        packman.setXpos(xInd2CenterCoor(++currentCellXInd));
      else if (dir == 2 && currentCellYInd < hNumCell - 1 && !randMaze.isWallXY(currentCellXInd, currentCellYInd + 1))
        packman.setYpos(yInd2CenterCoor(++currentCellYInd));
      else if (dir == 3 && currentCellXInd > 0 && !randMaze.isWallXY(currentCellXInd - 1, currentCellYInd))
        packman.setXpos(xInd2CenterCoor(--currentCellXInd));
      else if (dir == 4 && currentCellYInd > 0 && !randMaze.isWallXY(currentCellXInd, currentCellYInd - 1))
        packman.setYpos(yInd2CenterCoor(--currentCellYInd));
  //    println("moving packman " + currentCellXInd + " " + currentCellYInd);
      if (randMaze.isGemCell(currentCellXInd, currentCellYInd))
        randMaze.getGem(currentCellXInd, currentCellYInd);
    }
  }
  
  public int getMouseDir(int x, int y, int px, int py){
    int xDir = x - px;
    int yDir = y - py;
    int returnVal = -1; // -1 is invalid direction
    // direction step
    // 1 = x + 1 east
    // 2 = y + 1 south
    // 3 = x - 1 west
    // 4 = y - 1 north
    
    if (abs(xDir) > abs(yDir)){
      if (abs(xDir) > 20){
        if (xDir > 0)
          returnVal = 1;
        else if (xDir < 0)
          returnVal = 3;
      }
    }
    else if (abs(xDir) < abs(yDir)){
      if (abs(yDir) > 20){
        if (yDir > 0)
          returnVal = 2;
        else if (yDir < 0)
          returnVal = 4;
      }
    }
    return returnVal;
  }
  
}


class gem{
  float xPos,yPos;
  int gemSize;
  String pointStr = "+10";
  boolean hit = false;
  int textTrans = 255;
  
  gem(float xPos, float yPos, int cellSize){
    this.xPos = xPos;
    this.yPos = yPos;
    this.gemSize = cellSize/4;
  }
  
  public void display(){
    if (hit){
      
      textSize(9);
      fill(255, textTrans);
      text(pointStr, xPos, yPos);
      if (textTrans > 0 || yPos > 0){
        textTrans -= 1;
        yPos -= gemSize/4;
      }
      
    }
    else if (!hit){
      fill(190, 233, 223);
      ellipse(xPos, yPos, gemSize, gemSize);
    }
   
  }
  
  public void setHit(){
    this.hit = true;
  }
}

class maze{
  int cellSize;
  int wNumCell;
  int hNumCell;
  int cellCount = 0;
  int cellIndex = 0;
  ArrayList cells;
  ArrayList<Integer> backtrackX;
  ArrayList<Integer> backtrackY;
  int nextX = 1;
  int nextY = 1;
  boolean mazeGenDone = false;
  int numGem;
  
  maze(int cellSize, int wNumCell, int hNumCell){
    this.cellSize = cellSize;
    this.wNumCell = wNumCell;
    println("wNumCell "  + wNumCell);
    this.hNumCell = hNumCell;
    println("hNumCell "  + hNumCell);
    this.cells = new ArrayList();
    this.backtrackX = new ArrayList<Integer>();
    this.backtrackY = new ArrayList<Integer>();
  }
  
  public void mazeInit(){
    boolean wall;
    for (int r = 0; r < hNumCell; r++){
      for (int c = 0; c < wNumCell; c++){
        if (r % 2 == 0 || c % 2 == 0) wall = true;
        else wall = false;
        cells.add(new cell(c*cellSize, r*cellSize, c, r, cellSize, wall));
      }
    }
    backtrackX.add(1);
    backtrackY.add(1);
    
//    mazeGeneration(1, 1);
//    print2term();
  }
  
  public void display(){
    printMazeBG();
    if (!mazeGenDone)
      mazeGeneration(nextX, nextY);
      
    println("number of gem is " + numGem);
  }
  
  public void mazeGeneration(int cx, int cy){
//    println("I am in mazeGeneration");
    if (cellCount < 150){
      int neighborValid = -1;
      int xNext = 0;
      int yNext = 0;
      
      cell thisCell = getCellByXY(cx, cy);
      thisCell.setGem(2.5f);
      if (isGemCell(thisCell));
        this.numGem++;
      thisCell.setNeighborValid(neighborValid);
      
      if (cx - 2 > 0 && isClosed(cx - 2, cy)) // west
      {
        thisCell.setNeighborXY(++neighborValid, cx - 2, cy);
        thisCell.setDirectionStep(neighborValid, 1);
      }
      
      if (cy - 2 > 0 && isClosed(cx, cy - 2)) // north
      {
        thisCell.setNeighborXY(++neighborValid, cx, cy - 2);
        thisCell.setDirectionStep(neighborValid, 2);
      }
      
      if (cx + 2 < wNumCell - 1 && isClosed(cx + 2, cy)) // east
      {
        thisCell.setNeighborXY(++neighborValid, cx + 2, cy);
        thisCell.setDirectionStep(neighborValid, 3);
      }
      
      if (cy + 2 < hNumCell - 1 && isClosed(cx, cy + 2)) // east
      {
        thisCell.setNeighborXY(++neighborValid, cx, cy + 2);
        thisCell.setDirectionStep(neighborValid, 4);
      }
      
      // no valid neighbor
      if (neighborValid == -1){
        xNext = (int)backtrackX.get(cellIndex);
        yNext = (int)backtrackY.get(cellIndex);
        backtrackX.remove(cellIndex);
        backtrackY.remove(cellIndex);
        cellIndex--;
        
        if (cellIndex < 0) // maze generation have finished
          mazeGenDone = true;
      }
      
      if (neighborValid != -1){
        int randNum = (int)random(0, neighborValid + 1); // pick one direction with in the valid range
        xNext = thisCell.getNeighborX(randNum);
        yNext = thisCell.getNeighborY(randNum);
        
        cellIndex++;
        backtrackX.add(xNext);
        backtrackY.add(yNext);
        
        int nextStep = thisCell.getDirectionStep(randNum);
        
        if (nextStep == 1){
          cell tmp = getCellByXY(xNext + 1, yNext);
          tmp.setPath();
        }
        else if (nextStep == 2){
          cell tmp = getCellByXY(xNext, yNext + 1);
          tmp.setPath();
        }
        else if (nextStep == 3){
          cell tmp = getCellByXY(xNext - 1, yNext);
          tmp.setPath();
        }
        else if (nextStep == 4){
          cell tmp = getCellByXY(xNext, yNext - 1);
          tmp.setPath();
        }
        cellCount++;
      }
      
//      println(cellCount + " " + xNext + " " + yNext);
      this.nextX = xNext;
      this.nextY = yNext;
/*
      mazeGeneration(xNext, yNext); // for recursion
*/
    }
  }

  public boolean isClosed (int cx, int cy){
//    cell centerCell = (cell) cells.get(twoDim2oneDim(x, y));
    cell northCell = getCellByXY(cx, cy + 1);
    cell southCell = getCellByXY(cx, cy - 1);
    cell eastCell = getCellByXY(cx + 1, cy);
    cell westCell = getCellByXY(cx - 1, cy);
    if (northCell.getWall() & southCell.getWall() & eastCell.getWall() & westCell.getWall()) // the center cell have been surraounded by wall
      return true;
      
    return false;
  }

  public cell getCellByXY(int cx, int cy){
//    println("getCellByXY " + cx + " " + cy);
    return (cell) cells.get(twoDim2oneDim(cx, cy));
  }
  
  public int twoDim2oneDim(int cx, int cy){
//    println(cy*(wNumCell) + cx);
    return cy*(wNumCell) + cx;
  }
  
  public void printMazeBG(){
    while (cellCount < hNumCell*wNumCell){
      cell c = (cell) cells.get(cellCount);
      c.display();
      cellCount++;
    }
    cellCount = 0;
  }
  
  public void print2term(){
    for (int r = 0; r < hNumCell; r++){
      for (int c = 0; c < wNumCell; c++){
         cell tmp = getCellByXY(c, r);
         if (tmp.wall)
           print(" ");
         else
           print("#");
      }
      print("\n");
    }
  }
  
  public boolean isWallXY(int xInd, int yInd){
    cell tmp = getCellByXY(xInd, yInd);
    if (tmp.isWall() == true)
      return true;
    return false;
  }
  
  public void getGem(int xInd, int yInd){
     cell tmp = getCellByXY(xInd, yInd);
     tmp.findGem();
  }
  
  public boolean isGemCell(int xInd, int yInd){
     cell tmp = getCellByXY(xInd, yInd);
     return tmp.isGem();
  }
  
  public boolean isGemCell(cell tmpCell){
     return tmpCell.isGem();
  }
  
  public boolean getMazeGenDone(){
    return this.mazeGenDone;
  }
}

/**
 * Simple game template
 */

splashscrn spsn;
gamescrn gmsn;


public void setup() {
  // setup background size and color
  size(460, 700);
  background(102);
//  frameRate(20);
  // create new splash screen
  spsn = new splashscrn();
}

public void draw() {
  // get back to top left corner
  translate(0,0);
  if (spsn.active) { // splash screen animation
    background(102); 
    spsn.display();
    if(mousePressed){
      if(spsn.playBtn.clicking){
        println("playBtn is clicked!!");
        spsn.active = false;
        gmsn = new gamescrn();
      }
      else if (spsn.quitBtn.clicking){
        println("quitBtn is clicked!!");
        spsn.quitBtn.clicked();
      }
    }
  } 
  else if (gmsn.active) { // game screen animation
    gmsn.display();
    if(mousePressed){
//      println("you are on game screen!");
    }
    gmsn.movePackman();
  }
}

/*
// mouse clicked event
void mousePressed() {
  if (spsn.active){
    if (spsn.playBtn.clicking){
      println("playBtn is clicked!!");
      spsn.active = false;
      gmsn = new gamescrn();
    }
    if (spsn.quitBtn.clicking){
      println("quitBtn is clicked!!");
      spsn.quitBtn.clicked();
    }
  }
  else if (gmsn.active){
    println("you are on game screen!");
  }
}
*/

// simple splash screen class

class splashscrn {
  boolean active = false;
  cloud topCloud;
  int topCloudX = width/2;
  int topCloudY = 120;
  playButton playBtn;
  int playBtnX = 140;
  int playBtnY = height - 80;
  String playBtnStr = "PLAY";
  quitButton quitBtn;
  int quitBtnX = width-playBtnX;
  int quitBtnY = playBtnY;
  String quitBtnStr = "QUIT";
  splashPackman packman;
  int packmanX = width/2;
  int packmanY = height/2;
  int packmanR = 80;
  
  // constructor for splash screen
  splashscrn() {
    noStroke();
    this.topCloud = new cloud(topCloudX, topCloudY);
    this.playBtn = new playButton(playBtnX, playBtnY, playBtnStr);
    this.quitBtn = new quitButton(quitBtnX, quitBtnY, quitBtnStr);
    this.packman = new splashPackman(packmanX, packmanY, packmanR);
    this.active = true;
  }
  
  // animate the splash screen element
  public void display(){
//    println("running splash screen");
    if (active){
      variableEllipse(mouseX, mouseY, pmouseX, pmouseY);
      noStroke();
      topCloud.oscillate();
      playBtn.display(mouseX,mouseY);
      quitBtn.display(mouseX,mouseY);
      packman.rotatePackman();
    }
  }
  
  // The simple method variableEllipse() was created specifically 
  // for this program. It calculates the speed of the mouse
  // and draws a small ellipse if the mouse is moving slowly
  // and draws a large ellipse if the mouse is moving quickly 

  public void variableEllipse(int x, int y, int px, int py) {
    float speed = abs(x-px) + abs(y-py);
    stroke(speed);
    ellipse(x, y, speed, speed);
  }
}

    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#ECE9D8", "sketch_121218a" });
    }
}
