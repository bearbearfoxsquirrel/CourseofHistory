package test.puigames.courseofhistory.framework.engine.gameobjects;

/**
 * Created by Michael on 20/02/2017.
 */


public abstract class GameController {
    ControllerAction controllerAction;
    public GameController() {
        this.controllerAction = ControllerAction.NONE;
    }


    //TODO set up way of getting player action
   // public abstract ControllerAction getControllerAction();

   // public abstract ControllerAction setControllerAction(ControllerAction action);


    public static class ControllerException extends Exception {
        public ControllerException(String message) {
            super(message);
        }
    }
}
