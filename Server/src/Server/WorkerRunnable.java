package Server;/* This class contains code based on the following: https://paulbourke.net/geometry/pointlineplane/
* See getIntersection() method. */

public class WorkerRunnable implements Runnable {
    Command command;

    WorkerRunnable(Command command) {
        this.command = command;
    }
    @Override
    public void run() {
        switch (command.type) {
            case MOVE_USER -> updateUserPosition(command.direction);
            case MOVE_PARTICLE -> updateParticlePosition(command.p);
            case GENERATE_PARTICLE -> generateParticle();
        }
    }

    private void updateUserPosition(Direction direction) {
        if (ParticleArea.user != null) {
            Double v = ParticleArea.user.velocity;

            switch (direction) {
                case UP -> {
                    if(ParticleArea.user.y - v < 0) {
                        ParticleArea.user.y = 0.00;
                    } else {
                        ParticleArea.user.y -= v;
                    }

                    ParticleArea.user.cameraY = ParticleArea.user.y - 81.00;
                }
                case DOWN -> {
                    if(ParticleArea.user.y + v > 711) {
                        ParticleArea.user.y = 711.00;
                    } else {
                        ParticleArea.user.y += v;
                    }

                    ParticleArea.user.cameraY = ParticleArea.user.y - 81.00;
                }
                case LEFT -> {
                    if(ParticleArea.user.x - v < 0) {
                        ParticleArea.user.x = 0.00;
                    } else {
                        ParticleArea.user.x -= v;
                    }

                    ParticleArea.user.cameraX = ParticleArea.user.x - 144.00;
                }
                case RIGHT -> {
                    if(ParticleArea.user.x + v > 1271) {
                        ParticleArea.user.x = 1271.00;
                    } else {
                        ParticleArea.user.x += v;
                    }


                    ParticleArea.user.cameraX = ParticleArea.user.x - 144.00;
                }
            }
        }
    }

    private void updateParticlePosition(Particle p) {
        //First get the velocity
        Double xVelocity = Math.cos(Math.toRadians(command.angle)) * command.velocity/16;
        Double yVelocity = Math.sin(Math.toRadians(command.angle)) * command.velocity/16;
        //Get next positions
        double newX = p.x + xVelocity;
        double newY = p.y - yVelocity;

        //Check if next position is either: Past the border
        boolean bounceFlag = false;
        double perpendicularAngle = 0.0;

        if (newX > 1271) {
            bounceFlag = true;
            newX = 1271.00;
            perpendicularAngle = 180.00;
            command.angle += 180.00; //Hitting from left
        } else if (newX < 0) {
            bounceFlag = true;
            newX = 0.00;
            perpendicularAngle = 0.00;
            command.angle -= 180.00; //Hitting from right
        } else if (newY < 0) {
            bounceFlag = true;
            newY = 0.00;
            perpendicularAngle = 90.00;
            command.angle += 180.00; //Hitting from below
        } else if (newY > 711) {
            bounceFlag = true;
            newY = 711.00;
            perpendicularAngle = 270.00;
            command.angle -= 180.00; //Hitting from below

        }

        if(bounceFlag) {
            if(perpendicularAngle > command.angle) {
                command.angle = perpendicularAngle + (perpendicularAngle - command.angle);
            }
            else {
                command.angle = perpendicularAngle - (command.angle - perpendicularAngle);
            }

            xVelocity = Math.cos(Math.toRadians(command.angle)) * 1;
            yVelocity = Math.sin(Math.toRadians(command.angle)) * 1;
            newX += xVelocity;
            newY -= yVelocity;

            if(newX > 1270) {
                newX = 1270.00;
            }
            if(newX < 0) {
                newX = 0.00;
            }
            if(newY < 0) {
                newY = 0.00;
            }
            if(newY > 711) {
                newY = 711.00;
            }
        }

        //Move the particle
        p.moveParticle(newX, newY);
        Main.commandQueue.add(new Command(p, command.velocity, command.angle));
    }

    private void generateParticle() {
        System.out.println("Generated");
        Particle temp = new Particle(command.x, command.y, command.velocity, command.angle);
        ParticleArea.particleList.add(temp);
        Main.commandQueue.add(new Command(temp, command.velocity, command.angle));
    }

}