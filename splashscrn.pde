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
  void display(){
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

  void variableEllipse(int x, int y, int px, int py) {
    float speed = abs(x-px) + abs(y-py);
    stroke(speed);
    ellipse(x, y, speed, speed);
  }
}
