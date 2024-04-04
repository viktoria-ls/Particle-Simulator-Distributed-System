package Server;

public class Particle {
    Double x;
    Double y;
    Double velocity;
    Double angle;

    Particle(Double x, Double y, Double velocity, Double angle) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.angle = angle;
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
        return "{\"x\":" + x + ", \"y\":" + y + "}";
    }
}
