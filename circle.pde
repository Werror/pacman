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
  
  void oscillate(float oscY){
//    println("trying to oscillate the circle");
    y += oscY;
    fill(255);
    ellipse(x, y, r, r);
  }
}
