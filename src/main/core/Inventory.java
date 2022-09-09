package main.core;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * @author Richard Fouquoire
 * @version 12/09/2021
 */
public class Inventory
{
    private Character aMainCharacter;
    private Camera aCamera;
    private Map aMap;
    private int aCompteurItem;
    private int aEnterLevel;
    private int aDownLevel;
    private String[] aInventoryOptions;
    private Polygon aCurseur;
    private boolean aIsTriggered;
    private boolean aRenderItems;


    // ###Constructeurs###
    /**
     * Constructeur des objets de la classe Inventory
     * @param pCharacter le personnage associé à l'inventaire
     * @throws SlickException
     */
    public Inventory(final Character pCharacter, final Map pMap, final Camera pCamera)
    {
        this.aMainCharacter=pCharacter;
        this.aMap=pMap;
        this.aCamera=pCamera;
        this.aCompteurItem=0;
        this.aIsTriggered=false;
        this.aRenderItems=false;
        this.aEnterLevel=0;
        this.aDownLevel=0;
        this.aInventoryOptions = new String[]{"Inventaire","Sauvegarder", "Quitter"};
        int vXbaseCurseur=(int)this.aCamera.getCameraPositionX()-170;
        int vYbaseCurseur=(int)this.aCamera.getCameraPositionY()-185;
        this.aCurseur=new Polygon();
        this.aCurseur.addPoint(vXbaseCurseur,vYbaseCurseur);
        this.aCurseur.addPoint(vXbaseCurseur, vYbaseCurseur-10);
        this.aCurseur.addPoint(vXbaseCurseur+10, vYbaseCurseur-5);
    }

    public void setTrigger(final boolean pState){
        this.aIsTriggered=pState;
    }

    public boolean isTriggered(){
        return this.aIsTriggered;
    }

    public void render(final Graphics pGraphics) throws SlickException {
        if(this.aIsTriggered) {
            renderMenu(pGraphics);
            renderCurseur(pGraphics);
            if(this.aRenderItems){
                renderItems(pGraphics);
            }
        }

    }

    public void setRenderItems(final boolean pState){
        this.aRenderItems=pState;
    }

    public void decreaseEnterLevel() { this.aEnterLevel--; }

    public int getEnterLevel(){ return this.aEnterLevel; }

    public void incrementEnterLevel(){ this.aEnterLevel++; }

    public void incrementDownLevel(){ this.aDownLevel++; }

    public void decreaseDownLevel(){ this.aDownLevel--; }

    public int getDownLevel(){ return this.aDownLevel; }

    public int getMaxDownLevel(){ return this.aInventoryOptions.length-1; }

    public String getPointedOption(){ return this.aInventoryOptions[this.aDownLevel]; }

    public void renderMenu(Graphics pGraphics){
        pGraphics.setColor(new Color(50, 50, 50, 230));
        pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()-220,this.aCamera.getCameraPositionY()-250,500,270,5));
        pGraphics.setColor(new Color(255, 255, 255, 250));
        pGraphics.draw(new RoundedRectangle(this.aCamera.getCameraPositionX()-220,this.aCamera.getCameraPositionY()-250,500,270,5));
        pGraphics.setColor(new Color(25, 25, 25, 250));
        pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()-195,this.aCamera.getCameraPositionY()-225,450,225,5));
        pGraphics.setColor(new Color(10, 10, 10, 250));
        pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()-180,this.aCamera.getCameraPositionY()-210,120,200,5));
        renderMenuChoices(pGraphics);
    }

    public void renderItems(Graphics pGraphics) throws SlickException {
        int i=0;
        Item[] vItemList=new Item[this.aMainCharacter.getItemList().getItemList().size()];
        for ( String vS :  this.aMainCharacter.getItemList().getItemList().keySet() ){
            pGraphics.drawString(this.aMainCharacter.getItemList().getItem(vS).getNom(), this.aCamera.getCameraPositionX()-30, this.aCamera.getCameraPositionY()-200+i*20);
            Image vIcon=new Image(this.aMainCharacter.getItemList().getItem(vS).getImage());
            vIcon.draw(this.aCamera.getCameraPositionX()+20,this.aCamera.getCameraPositionY()-200+i*20);
            vItemList[i]=this.aMainCharacter.getItemList().getItem(vS);
            i++;
        }
        if(i==0) {
            pGraphics.drawString("Vous n'avez aucun item !", this.aCamera.getCameraPositionX()-30, this.aCamera.getCameraPositionY()-200);
        }
    }

    public void renderCurseur(Graphics pGraphics){
        pGraphics.setColor(new Color(200, 180, 180, 250));
        pGraphics.fill(this.aCurseur);
        pGraphics.setColor(new Color(255, 15, 10, 250));
        pGraphics.draw(this.aCurseur);
    }

    public void updateCurseur(){
        int vXbaseCurseur=(int)this.aCamera.getCameraPositionX()-155;
        int vYbaseCurseur=(int)this.aCamera.getCameraPositionY()-50;
        this.aCurseur.setX(vXbaseCurseur+this.aEnterLevel*125);
        this.aCurseur.setY(vYbaseCurseur+this.aDownLevel*20);
    }

    public void renderMenuChoices(final Graphics pGraphics){
        pGraphics.setColor(new Color(255, 255, 255, 250));
        for(int i=0;i<this.aInventoryOptions.length;i++){
                pGraphics.drawString(this.aInventoryOptions[i], this.aCamera.getCameraPositionX() - 160, this.aCamera.getCameraPositionY() - 200 + i * 20);
        }
    }

    public void renderItemChoices(final Graphics pGraphics){
        pGraphics.setColor(new Color(10, 10, 10, 250));
        pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()+50,this.aCamera.getCameraPositionY()-210,100,50,5));
        pGraphics.setColor(new Color(255, 255, 255, 250));
        pGraphics.draw(new RoundedRectangle(this.aCamera.getCameraPositionX()+50,this.aCamera.getCameraPositionY()-210,100,50,5));
        pGraphics.setColor(new Color(255, 255, 255, 250));
        pGraphics.drawString("Utiliser",this.aCamera.getCameraPositionX()+70,this.aCamera.getCameraPositionY()-207);
        pGraphics.drawString("Jeter",this.aCamera.getCameraPositionX()+70,this.aCamera.getCameraPositionY()-187);
    }

    public void addItemToMap(final Item[] vItemList){
        this.aMap.getItemList().addItem(vItemList[this.aCompteurItem].getNom(),vItemList[this.aCompteurItem]);
        this.aMap.getItemList().getItem(vItemList[this.aCompteurItem].getNom()).setPositionX(this.aMainCharacter.getCharacterPositionX()-1);
        this.aMap.getItemList().getItem(vItemList[this.aCompteurItem].getNom()).setPositionY(this.aMainCharacter.getCharacterPositionY()-1);
        this.aMainCharacter.getItemList().delItem(vItemList[this.aCompteurItem].getNom());
    }
}
