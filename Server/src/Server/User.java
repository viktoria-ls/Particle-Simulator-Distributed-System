package Server;

public class User {
    Double velocity = 1.0;
    Double x;
    Double y;
    Double cameraX;
    Double cameraY;

    User() {
        this.x = 1271.0;
        this.y = 0.0;

        cameraX = x - 144;
        cameraY = y - 81;
    }

    User(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}