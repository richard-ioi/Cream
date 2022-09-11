package main.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.Input;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

class StateGame extends StateBasedGame implements KeyListener {

    private GameContainer aContainer;

    public static void main(String[] args) throws SlickException {
        AppGameContainer vApp = new AppGameContainer(new ScalableGame(new StateGame(),640,640,true));
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int fps = gd.getDisplayMode().getRefreshRate();
        vApp.setDisplayMode(640,640,false);
        vApp.setTargetFrameRate(fps);
        vApp.start();
    }

    public StateGame() { super("Projet Jeu"); }

    /**
     * Ici il suffit d'ajouter nos deux boucles de jeux.
     * La première ajoutèe sera celle qui sera utilisée au début
     */
    @Override
    public void initStatesList(GameContainer container){
        addState(new MainMenuGameState());
        addState(new MapGameState("MainMap"));
        this.aContainer=container;
    }

    @Override
    public void keyPressed(int pKey, char pC){
        switch(pKey){
            case Input.KEY_F4:
                AppGameContainer gc = (AppGameContainer) this.aContainer;
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                if(gc.getWidth()!=width&&gc.getHeight()!=height){
                    try {
                        gc.setDisplayMode(width, height, true);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        gc.setDisplayMode(640, 640, false);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}