package model;

import geometry.Point;
import org.slf4j.LoggerFactory;
import tick.Tickable;


public final class  Bomb implements Tickable, Positionable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bomb.class);
    private static final long LIFE_TIME = 2500; //after pass death

    private final int id;
    private long lifeTime = 0;
    private int rangeExplosion;
    private final Point position;

    public Bomb(final Point position, final int rangeExplosion) {
        this.position = position;
        this.id = GameSession.nextId();
        this.rangeExplosion = rangeExplosion;
        this.lifeTime = 0;

        log.info("New Bomb: id={}, position({}, {})\n", id, position.getX(), position.getY()) ;
    }

    @Override
    public void tick(final long elapsed) {
        lifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
