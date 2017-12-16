package bomber.games.gamesession;

import bomber.games.gameobject.*;
import bomber.games.geometry.Bar;
import bomber.games.geometry.Point;
import bomber.games.model.GameObject;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MechanicsSubroutines {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MechanicsSubroutines.class);

    public boolean collisionCheck(GameObject currentPlayer, Map<Integer, GameObject> replica) {
        final int brickSize = 31;
        final int playerSize = 25;
        int player_X = currentPlayer.getPosition().getX();
        int player_Y = currentPlayer.getPosition().getY();

        Bar playerBar = new Bar(player_X,player_X+playerSize,player_Y,player_Y - playerSize);
        int playerId = currentPlayer.getId();




        //Повторюсь, надо проверить новые координаты игроков на коллизии с другими объектами
        for (GameObject gameObject : replica.values()) {
            int brick_X = gameObject.getPosition().getX();
            int brick_Y = gameObject.getPosition().getY();
            Bar brickBar = new Bar(brick_X,brick_X+brickSize,brick_Y,brick_Y - brickSize);
            if ((brickBar.isColliding(playerBar)) && !(gameObject.getId() == playerId)) {
                log.info("===================");
                log.info("Collision with " + gameObject);
                return false;
            }
        }
        return true;
    }

    public Integer bonusCheck(Player currentPlayer, Map<Integer, GameObject> replica) {


        int playerId = currentPlayer.getId();
        Point playerPosition = currentPlayer.getPosition();

        for (GameObject gameObject : replica.values()) {
            if (gameObject instanceof Bomb) {
                if (gameObject.getPosition() == playerPosition) {
                    return gameObject.getId();
                }
            }
        }
        return null;
    }

    public Boolean createExplosions(Point currentPoint, GameObject gameObject, Map<Integer, GameObject> replica) {

        if (gameObject.getPosition().isColliding(currentPoint)) { //если на пути взрыва встал НЛО
            if (gameObject instanceof Box) { //и это НЛО - коробка
                replica.remove(gameObject.getId()); //удаляем взорвавшуюся коробку
            }
            return false;//все, один объект взорвался, дальше не надо работать по этому кейсу
        }
        return true;
    }


    public void youDied(Map<Integer, GameObject> replica, Player player, Explosion explosion) {
        if (player.getPosition().isColliding(explosion.getPosition())) {
            replica.remove(player.getId());
        }
    }
}