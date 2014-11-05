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
  
  void mazeInit(){
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
  
  void display(){
    printMazeBG();
    if (!mazeGenDone)
      mazeGeneration(nextX, nextY);
      
    println("number of gem is " + numGem);
  }
  
  void mazeGeneration(int cx, int cy){
//    println("I am in mazeGeneration");
    if (cellCount < 150){
      int neighborValid = -1;
      int xNext = 0;
      int yNext = 0;
      
      cell thisCell = getCellByXY(cx, cy);
      thisCell.setGem(2.5);
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

  boolean isClosed (int cx, int cy){
//    cell centerCell = (cell) cells.get(twoDim2oneDim(x, y));
    cell northCell = getCellByXY(cx, cy + 1);
    cell southCell = getCellByXY(cx, cy - 1);
    cell eastCell = getCellByXY(cx + 1, cy);
    cell westCell = getCellByXY(cx - 1, cy);
    if (northCell.getWall() & southCell.getWall() & eastCell.getWall() & westCell.getWall()) // the center cell have been surraounded by wall
      return true;
      
    return false;
  }

  cell getCellByXY(int cx, int cy){
//    println("getCellByXY " + cx + " " + cy);
    return (cell) cells.get(twoDim2oneDim(cx, cy));
  }
  
  int twoDim2oneDim(int cx, int cy){
//    println(cy*(wNumCell) + cx);
    return cy*(wNumCell) + cx;
  }
  
  void printMazeBG(){
    while (cellCount < hNumCell*wNumCell){
      cell c = (cell) cells.get(cellCount);
      c.display();
      cellCount++;
    }
    cellCount = 0;
  }
  
  void print2term(){
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
  
  boolean isWallXY(int xInd, int yInd){
    cell tmp = getCellByXY(xInd, yInd);
    if (tmp.isWall() == true)
      return true;
    return false;
  }
  
  void getGem(int xInd, int yInd){
     cell tmp = getCellByXY(xInd, yInd);
     tmp.findGem();
  }
  
  boolean isGemCell(int xInd, int yInd){
     cell tmp = getCellByXY(xInd, yInd);
     return tmp.isGem();
  }
  
  boolean isGemCell(cell tmpCell){
     return tmpCell.isGem();
  }
  
  boolean getMazeGenDone(){
    return this.mazeGenDone;
  }
}
