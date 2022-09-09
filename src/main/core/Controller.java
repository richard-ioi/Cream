package main.core;

public class Controller {

    protected boolean aIsActive;
    protected static Controller[] aControllers;
    public Controller(final boolean pIsActive){
        this.aIsActive=pIsActive;
    }

    public static void setControllers(final Controller[] pControllers){
        aControllers=pControllers;
    }

    private void deactivate(){
        this.aIsActive=false;
    }

    private void activate(){
        this.aIsActive=true;
    }

    protected void deactivateOtherControllers(){
        for(Controller vController : aControllers){
            if(!vController.equals(this)){
                vController.deactivate();
            }
        }
    }

    protected void activateAllControllers(){
        for(Controller vController : aControllers){
            vController.activate();
        }
    }
}
