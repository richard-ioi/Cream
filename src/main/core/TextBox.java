package main.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

public class TextBox extends Controller {
    protected static Camera aCamera;
    protected static Map aMap;
    protected static Character aMainCharacter;
    protected int aCompteur;
    protected String aFinalText;
    protected String aTextCount;
    protected boolean aConversationFinie;

    protected TextBox(){
        super(true);
        this.aCompteur=0;
        this.aFinalText="";
        this.aTextCount="";
        this.aConversationFinie=false;
    }


    protected Rectangle setupRenderBox(final Graphics pGraphics, final float vX, final float vY){
        pGraphics.setColor(new Color(255, 255, 255, .9f));
        RoundedRectangle vDialogueBox=new RoundedRectangle(vX-275, vY+150, 550, 100, 15);
        pGraphics.draw(vDialogueBox);
        pGraphics.fill(vDialogueBox);
        pGraphics.setColor(new Color(0, 0, 0, .9f));
        pGraphics.draw(vDialogueBox);
        return new Rectangle(vDialogueBox.getX()+20,vDialogueBox.getY()+10,vDialogueBox.getWidth()-40,vDialogueBox.getHeight()-20);
    }

    protected void displayTextInBox(final Graphics pGraphics, final String pTexte) {
        float vX=this.aCamera.getCameraPositionX();
        float vY=this.aCamera.getCameraPositionY();
        Rectangle vRenderBox=setupRenderBox(pGraphics,vX,vY);
        float cX=vRenderBox.getX();
        float cY=vRenderBox.getY();

        if(this.aCompteur<pTexte.length()) {
            if(pGraphics.getFont().getHeight(this.aFinalText) < (int)vRenderBox.getHeight()) {
                this.aFinalText += pTexte.charAt(this.aCompteur);
                this.aTextCount += pTexte.charAt(this.aCompteur);
                if (pGraphics.getFont().getWidth(this.aTextCount) > (int)vRenderBox.getWidth()) {
                    this.aTextCount="";
                    if (pTexte.charAt(this.aCompteur)!=' '){
                        for(int i=this.aCompteur;this.aFinalText.charAt(i)!=' ';i--){
                            if(this.aFinalText.charAt(i-1)==' '){
                                this.aCompteur=i-1;
                                this.aFinalText=this.aFinalText.substring(0,this.aCompteur)+'\n';
                                break;
                            }
                        }
                    } else {
                        this.aFinalText += '\n';
                    }
                }
                pGraphics.drawString(this.aFinalText,cX,cY);
            }else{
                String vSuiteTexte=pTexte.substring(this.aCompteur);
                if(!vSuiteTexte.equals(this.aMap.getTouchedCharacter().getDialogueList().get(this.aMap.getTouchedCharacter().getDialogueL()).get(this.aMap.getTouchedCharacter().getDialogueC()+1))){
                    this.aMap.getTouchedCharacter().getDialogueList().get(this.aMap.getTouchedCharacter().getDialogueL()).add(this.aMap.getTouchedCharacter().getDialogueC()+1,vSuiteTexte);
                }
                this.aCompteur=pTexte.length()+1;
            }
        }else{
            this.aTextCount="";
        }

        if(this.aCompteur>=pTexte.length()) {
            this.aTextCount="";
            pGraphics.drawString(this.aFinalText,cX,vY+160);
            this.aCompteur=0;
            this.aConversationFinie=true;
        }
    }

    protected void renderFinalConversation(final Graphics pGraphics){
        float vX=this.aCamera.getCameraPositionX();
        float vY=this.aCamera.getCameraPositionY();
        pGraphics.setColor(new Color(255, 255, 255, .9f));
        RoundedRectangle vDialogueBox=new RoundedRectangle(vX-275, vY+150, 550, 100, 15);
        pGraphics.draw(vDialogueBox);
        pGraphics.fill(vDialogueBox);
        pGraphics.setColor(new Color(0, 0, 0, .9f));
        pGraphics.draw(vDialogueBox);
        pGraphics.drawString(this.aFinalText,vX-255,vY+160);
    }

    protected void displayInfo(final Graphics pGraphics, final String pTexte) { //
        float vX=this.aCamera.getCameraPositionX();
        float vY=this.aCamera.getCameraPositionY();
        pGraphics.setColor(new Color(255, 255, 0, .5f));
        RoundedRectangle vDialogueBox=new RoundedRectangle(vX-210, vY+230, 450, 40, 15);
        pGraphics.draw(vDialogueBox);
        pGraphics.fill(vDialogueBox);
        pGraphics.setColor(new Color(0, 0, 0, .9f));
        pGraphics.draw(vDialogueBox);
        pGraphics.drawString(pTexte,vX-190,vY+240);
    }

    protected void renderNextDialogueArrow(final Graphics pGraphics){
        pGraphics.setColor(new Color(255, 255, 0, .9f));
        RoundedRectangle vMiniBox=new RoundedRectangle(this.aCamera.getCameraPositionX()+250, this.aCamera.getCameraPositionY()+220, 40, 40, 15);
        pGraphics.draw(vMiniBox);
        pGraphics.fill(vMiniBox);
        pGraphics.setColor(new Color(0, 0, 0, .9f));
        pGraphics.draw(new RoundedRectangle(this.aCamera.getCameraPositionX()+250, this.aCamera.getCameraPositionY()+220, 41, 41, 15));
        Polygon vTriangle= new Polygon();
        vTriangle.addPoint(this.aCamera.getCameraPositionX()+260,this.aCamera.getCameraPositionY()+235);
        vTriangle.addPoint(this.aCamera.getCameraPositionX()+280, this.aCamera.getCameraPositionY()+235);
        vTriangle.addPoint(this.aCamera.getCameraPositionX()+270, this.aCamera.getCameraPositionY()+250);
        pGraphics.fill(vTriangle);
    }
}
