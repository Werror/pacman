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
  
  void display(){
    randMaze.display();
    packman.display();
  }
  
  int xInd2CenterCoor(int xInd){
    return xInd * mazeCellSize + mazeCellSize/2;
  }
  
  int yInd2CenterCoor(int yInd){
    return yInd * mazeCellSize + mazeCellSize/2;
  }
  
  void movePackman(){
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
  
  int getMouseDir(int x, int y, int px, int py){
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

