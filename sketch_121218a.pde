/**
 * Simple game template
 */

splashscrn spsn;
gamescrn gmsn;


void setup() {
  // setup background size and color
  size(460, 700);
  background(102);
//  frameRate(20);
  // create new splash screen
  spsn = new splashscrn();
}

void draw() {
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
