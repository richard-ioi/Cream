package main.core;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.io.FileNotFoundException;

import static main.core.JSONRessources.*;

public class ItemController extends TextBox implements KeyListener {

    private boolean aRenderWaitingItemAction;

    public ItemController(){
        this.aRenderWaitingItemAction=false;
    }

    public void render(final Graphics pGraphics){
        if(this.aRenderWaitingItemAction){
            renderWaitingItemAction(pGraphics);
        }
        //renderItemInterractions(pGraphics);
    }

    public void update(){
        if((this.aMap.getTouchedItem()!=null)&&(this.aMainCharacter.getItemCollision())){
            this.aRenderWaitingItemAction=true;
        }
        if(this.aRenderWaitingItemAction && !this.aMainCharacter.getItemCollision()){
            this.aRenderWaitingItemAction=false;
        }
    }

    public void renderWaitingItemAction(final Graphics pGraphics){
        displayInfo(pGraphics, "Appuyez sur Entrée pour ramasser l'item " + this.aMap.getTouchedItem().getNom());
    }


    /*public void renderItemInterractions(final Graphics pGraphics){
        float vX=this.aCamera.getCameraPositionX();
        float vY=this.aCamera.getCameraPositionY();
        if(this.aScenario.getType().equals("Item")){
            for (String vS : this.aMainCharacter.getItemList().getItemList().keySet()) {
                if(this.aMainCharacter.getItemList().getItem(vS).getNom().equals(this.aScenario.getSearchedItem())){
                    String vTexte="Félicitations, vous avez trouvé l'item "+this.aScenario.getSearchedItem();
                    if (this.aConversationFinie) {
                        this.aCompteur = 0;
                        pGraphics.setColor(new Color(255, 255, 255, .9f));
                        RoundedRectangle vDialogueBox=new RoundedRectangle(vX-250, vY+150, 550, 100, 15);
                        pGraphics.draw(vDialogueBox);
                        pGraphics.fill(vDialogueBox);
                        pGraphics.setColor(new Color(0, 0, 0, .9f));
                        pGraphics.draw(vDialogueBox);
                        pGraphics.drawString(this.aFinalText,vX-230,vY+160);
                        /*if(this.aEnterIsPressed) { A FAIRE DANS LE KEYPRESSED
                            this.aFinalText = "";
                            this.aConversationFinie = false;
                            //loadScenario();
                            this.aEnterIsPressed=false;
                            this.aMainCharacter.setAllowedMovement(true);
                        }
                        this.aEnterIsPressed=false;
                    } else {
                        displayTextInBox(pGraphics, vTexte);
                        this.aCompteur++;
                        this.aMainCharacter.setAllowedMovement(false);
                        /*if(this.aEnterIsPressed){
                            this.aEnterIsPressed=false;
                        }
                    }
                }
            }
        }
    }*/

    @Override
    public void keyPressed(int pKey, char pC) {
        switch (pKey) {
            case Input.KEY_ENTER:
                if((this.aMap.getTouchedItem()!=null)&&(this.aMainCharacter.getItemCollision())) {
                    this.aRenderWaitingItemAction=false;
                    this.aMainCharacter.getItemList().addItem(this.aMap.getTouchedItem().getNom(), this.aMap.getTouchedItem());
                    manageJSONData();
                    this.aMap.setTouchedItem(null);
                }
                break;
        }
    }

    @Override
    public boolean isAcceptingInput() { return super.aIsActive; }

    @Override
    public void keyReleased(int i, char c) {}

    @Override
    public void setInput(Input input) {}

    @Override
    public void inputEnded() {}

    @Override
    public void inputStarted() {}

    private void manageJSONData(){
        deleteItemFromJSONObject(this.aMap.getMapData(),this.aMap.getTouchedItem().getNom());
        addItemToJSONObject(this.aMainCharacter.getCharacterData(),this.aMap.getTouchedItem());
        this.aMap.getItemList().delItem(this.aMap.getTouchedItem().getNom());
        try {
            saveJsonFile(this.aMap.getMapData(),"src/main/data/Map/"+this.aMap.getNomMap()+".json");
            saveJsonFile(this.aMainCharacter.getCharacterData(),"src/main/data/MainCharacter/MainCharacter.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
