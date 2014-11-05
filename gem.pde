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
  
  void display(){
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
  
  void setHit(){
    this.hit = true;
  }
}
