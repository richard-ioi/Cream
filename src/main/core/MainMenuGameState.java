package main.core;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class MainMenuGameState extends BasicGameState {

    public static final int ID = 1;
    private StateBasedGame game;
    private int aCompteur;

    @Override
    public void init(GameContainer container, StateBasedGame game) {
        this.game = game;
        this.aCompteur=0;

    }

    /**
     * Contenons nous d'afficher l'image de fond.
     * Le text est placé approximativement au centre.
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics pGraphics) throws SlickException {
        if (this.aCompteur<container.getHeight()/7) {
            this.aCompteur++;
        }
        pGraphics.drawImage(new Image("src/main/resources/MainMenuGameState/background/banniere.png"),container.getWidth()/8,this.aCompteur);
        pGraphics.setColor(new Color(255, 255, 255, .9f));
        RoundedRectangle vDialogueBox=new RoundedRectangle((container.getWidth()/2)-130, (container.getHeight()/2)+150, 250, 120, 15);
        pGraphics.draw(vDialogueBox);
        pGraphics.fill(vDialogueBox);
        pGraphics.setColor(new Color(0, 0, 0, .9f));
        pGraphics.draw(vDialogueBox);
        pGraphics.drawString("Nouvelle partie", (container.getWidth()/2)-75, (container.getHeight()/2)+180);
    }

    /**
     * Passer à l’écran de jeu à l'appui de n'importe quel touche.
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
    }

    @Override
    public void keyReleased(int key, char c) {
        game.enterState(MapGameState.ID);
    }

    /**
     * L'identifiant permet d'identifier les différentes boucles.
     * Pour passer de l'une à l'autre.
     */
    @Override
    public int getID() {
        return ID;
    }
}