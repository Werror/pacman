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
  
  void display(){
    rectMode(CENTER);
//    stroke(0);
    if (wall) fill(0);
    else fill(100, 192, 10);
    rect(xPos, yPos, cellSize, cellSize);
    
    if (thisGem != null)
      thisGem.display();
  }
  
  void setNeighborValid(int validNum){
    this.neighborValid = validNum;
  }
  
  void setNeighborXY(int index, int x, int y){
    this.neighborX[index] = x;
    this.neighborY[index] = y;
  }
  
  void setDirectionStep(int index, int dir){
    this.step[index] = dir;
  }
  
  int getNeighborX(int index){
    return this.neighborX[index];
  }
  
  int getNeighborY(int index){
    return this.neighborY[index];
  }
  
  int getDirectionStep(int index){
    return this.step[index];
  }
  
  void setWall(){
    this.wall = true;
  }
  
  void setPath(){
    this.wall = false;
  }
  
  void setGem(float thresh){
    float randVal = random(1,3);
    if (randVal < thresh)
      thisGem = new gem(xPos, yPos, cellSize);
//    else
//      thisGem = null;
  }
  
  boolean getWall(){
    return this.wall;
  }
  
  boolean isWall(){
    if (this.wall == true)
      return true;
    return false;
  }
  
  void findGem(){
    if (this.thisGem != null)
      thisGem.setHit();
  }
  
  boolean isGem(){
    if (this.thisGem != null)
      return true;
    return false;
  }
}
