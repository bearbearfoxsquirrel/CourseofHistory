package test.puigames.courseofhistory.framework.engine.gameobjects;

/**
 * Created by Michael on 20/02/2017.
 */


public abstract class GameController {
    public Pawn pawn;

    public interface ControllerStates{}


    public GameController() {

    }


    //TODO set up way of getting pawn action
   // public abstract ControllerAction getControllerAction();

   // public abstract ControllerAction setControllerAction(ControllerAction action);

  //  public abstract void update(InputBuddy inputBuddy, float deltaTime);
    //public abstract void update();

   // public abstract void update(float deltaTime);


    public void possessPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public static class ControllerException extends Exception {
        public ControllerException(String message) {
            super(message);
        }
    }
}
