package main.core;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;


public class DialogueController extends TextBox implements KeyListener{

    private boolean aRenderConversation;
    private boolean aRenderFinalConversation;
    private boolean aRenderWaitingConversation;
    private Music aSpeechMusic;


    public DialogueController() {
        this.aRenderConversation=false;
        this.aRenderFinalConversation=false;
        this.aRenderWaitingConversation=false;
    }

    public void render(final Graphics pGraphics) throws SlickException {

        if(this.aRenderConversation) {
            renderConversation(pGraphics);
        }
        if(this.aRenderFinalConversation){
            renderFinalConversation(pGraphics);
            drawNextDialogueIconIfExists(pGraphics);
        }
        if(this.aRenderWaitingConversation){
            renderWaitingConversation(pGraphics);
        }
    }

    public void update() {

        if(!this.aMainCharacter.getCharacterCollision()){
            this.aRenderConversation=false;
            this.aRenderFinalConversation=false;
            this.aRenderWaitingConversation=false;
            resetConversationParameters();
        }else if(this.aMap.getTouchedCharacter()!=null && this.aMap.getTouchedCharacter().getDialogue()!=null && !conversationIsLaunched()){
            this.aRenderWaitingConversation=true;
            this.aSpeechMusic=this.aMap.getTouchedCharacter().getSpeechMusic();
        }

        if(this.aRenderWaitingConversation && conversationIsLaunched()){
            this.aRenderWaitingConversation=false;
            stopSpeechMusic();
        }

        if (this.aConversationFinie) {
            this.aRenderConversation = false;
            this.aRenderFinalConversation = true;
            stopSpeechMusic();
        }
    }

    public void renderConversation(final Graphics pGraphics){
        this.aMap.getTouchedCharacter().renderParle(pGraphics);
        displayTextInBox(pGraphics,this.aMap.getTouchedCharacter().getDialogue());
        this.aCompteur++;
    }
    public void renderWaitingConversation(final Graphics pGraphics) throws SlickException{
        this.aMap.getTouchedCharacter().renderWaitParle(pGraphics);
        displayInfo(pGraphics,"Appuyez sur entrée pour parler avec "+this.aMap.getTouchedCharacter().getCharacterName());
        this.aMap.getTouchedCharacter().setDialogueC(0);
    }

    public void launchNextOrFinishConversation(){
        this.aRenderFinalConversation=false;
        this.aFinalText="";
        this.aConversationFinie=false;
        this.aCompteur=0;
        if((this.aMap.getTouchedCharacter().getDialogueC()+1)<(this.aMap.getTouchedCharacter().getDialogueList().get(this.aMap.getTouchedCharacter().getDialogueL()).size())) {
            this.aMap.getTouchedCharacter().setDialogueC(this.aMap.getTouchedCharacter().getDialogueC()+1);
            this.aFinalText="";
        }else {
            resetConversationParameters();
        }
    }

    public void resetConversationParameters(){
        this.aConversationFinie=false;
        this.aCompteur=0;
        this.aFinalText="";
        this.aTextCount="";
        this.aRenderConversation=false;
        this.aRenderWaitingConversation=false;
        this.aRenderFinalConversation=false;
        this.aMap.setTouchedCharacter(null);
        if(this.aSpeechMusic!=null) {
            stopSpeechMusic();
        }
    }

    public void faceCharacterDirectionWhenSpeaking(){
        if(!this.aRenderConversation && !this.aRenderFinalConversation) {
            if (this.aMainCharacter.getCharacterDirection() == 0) {
                this.aMap.getTouchedCharacter().setCharacterDirection(2);
                this.aMainCharacter.setCharacterPositionY(this.aMainCharacter.getCharacterPositionY() + 1);
            } else if (this.aMainCharacter.getCharacterDirection() == 1) {
                this.aMap.getTouchedCharacter().setCharacterDirection(3);
                this.aMainCharacter.setCharacterPositionX(this.aMainCharacter.getCharacterPositionX() + 1);
            } else if (this.aMainCharacter.getCharacterDirection() == 2) {
                this.aMap.getTouchedCharacter().setCharacterDirection(0);
                this.aMainCharacter.setCharacterPositionY(this.aMainCharacter.getCharacterPositionY() - 1);
            } else if (this.aMainCharacter.getCharacterDirection() == 3) {
                this.aMap.getTouchedCharacter().setCharacterDirection(1);
                this.aMainCharacter.setCharacterPositionX(this.aMainCharacter.getCharacterPositionX() - 1);
            }
        }
    }

    public void drawNextDialogueIconIfExists(final Graphics pGraphics) throws SlickException {
        if((this.aMap.getTouchedCharacter().getDialogueC()+1)<(this.aMap.getTouchedCharacter().getDialogueList().get(this.aMap.getTouchedCharacter().getDialogueL()).size())) {
            this.aMap.getTouchedCharacter().renderWaitParle(pGraphics);
            renderNextDialogueArrow(pGraphics);
        }
    }

    public boolean conversationIsLaunched(){
        if(this.aRenderConversation || this.aRenderFinalConversation){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void keyPressed(int pKey, char pC) {
        switch (pKey) {
            case Input.KEY_ENTER:
                if(this.aMap.getTouchedCharacter()!=null) {
                    if(this.aMap.getTouchedCharacter().getDialogue()!=null){
                        if(this.aMap.getTouchedCharacter().getDialogueList().get(0).get(0)!=null){
                            this.aRenderWaitingConversation=false;
                            faceCharacterDirectionWhenSpeaking();
                            this.aRenderConversation=true;
                            startSpeechMusic();
                        }
                    }
                }
                if(this.aRenderFinalConversation){
                    launchNextOrFinishConversation();
                }
                break;
        }

    }

    private void startSpeechMusic(){ this.aSpeechMusic.loop(); }
    private void stopSpeechMusic(){ this.aSpeechMusic.stop(); }

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
}
