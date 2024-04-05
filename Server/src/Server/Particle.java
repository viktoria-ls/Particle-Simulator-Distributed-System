package Server;

public class Particle {
    Double x;
    Double y;
    Double velocity;
    Double angle;
    String type;
    int id;

    Particle(Double x, Double y, Double velocity, Double angle, int id) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.angle = angle;
        this.type = "normal";
        this.id = id;
    }

    Particle(Double x, Double y, String type, int id) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.id = id;
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
        return "{\"type\":" + "\"" + type + "\"" + ", \"x\":" + x + ", \"y\":" + y + ", \"id\":" + id +"}";
    }
}
