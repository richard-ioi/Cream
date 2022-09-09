package main.core;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class InventoryController extends Controller implements KeyListener {

    private Inventory aInventory;

    public InventoryController(final Inventory pInventory){
        super(true);
        this.aInventory=pInventory;
    }


    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return super.aIsActive;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void keyPressed(int pKey, char pC){
        switch (pKey) {
            case Input.KEY_ESCAPE:
                if (this.aInventory.isTriggered()) {
                    if(this.aInventory.getEnterLevel()==0) {
                        this.aInventory.setTrigger(false);
                        activateAllControllers();
                    }else{
                        this.aInventory.decreaseEnterLevel();
                    }

                } else {
                    this.aInventory.setTrigger(true);
                    deactivateOtherControllers();
                }
                break;
            case Input.KEY_ENTER:
                if(this.aInventory.isTriggered()) {
                    if (this.aInventory.getPointedOption().equals("Quitter")) {
                        System.exit(0);
                    } else if (this.aInventory.getPointedOption().equals("Sauvegarder")) {
                        System.out.println("Faire la fonction de sauvegarde");
                    } else if (this.aInventory.getPointedOption().equals("Inventaire")) {
                        this.aInventory.setRenderItems(true);
                        this.aInventory.incrementEnterLevel();
                    }
                }
                break;
            case Input.KEY_DOWN:
            case Input.KEY_S:
                if(this.aInventory.isTriggered() && this.aInventory.getDownLevel()<this.aInventory.getMaxDownLevel()){
                    this.aInventory.incrementDownLevel();
                }
                break;
            case Input.KEY_UP:
            case Input.KEY_Z:
                if(this.aInventory.isTriggered() && this.aInventory.getDownLevel()>0){
                    this.aInventory.decreaseDownLevel();
                }
                break;
        }
        this.aInventory.updateCurseur();
    }

    @Override
    public void keyReleased(int pKey, char pC) {
    }
}
