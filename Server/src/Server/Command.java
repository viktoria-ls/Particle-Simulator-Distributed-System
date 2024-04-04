package Server;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Command implements Delayed {
    CommandType type;
    Double x;
    Double y;
    public Double velocity;
    public Double angle;
    long time;
    Particle p;
    Direction direction;

    /* Constructor for moving particle */
    public Command(Particle p, Double velocity, Double angle) {
        this.type = CommandType.MOVE_PARTICLE;
        this.time = System.currentTimeMillis()
                + 62;
        this.velocity = velocity;
        this.angle = angle;
        this.p = p;
    }

    /* Constructor for generating particle */
    Command(Double a, Double b, Double c, Double d) {
        this.type = CommandType.GENERATE_PARTICLE;

        this.x = a;
        this.y = b;
        this.velocity = c;
        this.angle = d;
    }

    // Constructor for spawning user
    Command() {
        this.type = CommandType.SPAWN_USER;
    }

    Command(Direction direction) {
        this.type = CommandType.MOVE_USER;
        this.direction = direction;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = time - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.time, ((Command) o).time);
    }
}

enum CommandType {
    MOVE_PARTICLE,
    GENERATE_PARTICLE,
    MOVE_USER,
    SPAWN_USER
}