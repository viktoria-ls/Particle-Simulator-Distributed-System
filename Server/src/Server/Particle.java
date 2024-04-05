package Server;

public class Particle {
    Double x;
    Double y;
    Double velocity;
    Double angle;
    String type;

    Particle(Double x, Double y, Double velocity, Double angle) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.angle = angle;
        this.type = "normal";
    }

    Particle(Double x, Double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void moveParticle(Double nx, Double ny) {
        this.x = nx;
        this.y = ny;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getJSON() {
        return "{\"type\":" + "\"" + type + "\"" +  ", \"x\":" + x + ", \"y\":" + y + "}";
    }
}
