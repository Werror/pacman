class cloud {
  int x,y;
  ArrayList circles;
  float theta = 0.0;
  private static final int NUM_CIRCLE = 19; // number of circle to draw for the cloud
  
  private static final int CLOUDR_MIN = 50; // the min radius for each circle in the cloud
  private static final int CLOUDR_MAX = 180; // the max radius
  private static final int CLOUDW_MIN = 140; // the min x position for each circle in the cloud
  private static final int CLOUDH_MAX = 50; // the max x position

  
  private static final float CLOUDYOSC = 1.5; // the oscillation range of the cloud
  
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
  void oscillate(){
//    println("trying to oscillate the cloud");
    float cloudY = (sin(theta)) * CLOUDYOSC;
    for (int i = 0; i < NUM_CIRCLE; i++){
      circle c = (circle) circles.get(i);
      c.oscillate(cloudY);
    }
    theta += 0.05;
//    println(cloudY);
  }
}
