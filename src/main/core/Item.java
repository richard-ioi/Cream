package main.core;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Classe permettant de créer des objets "items", ils sont caractérisés par un nom et 
 * leur poids.
 * Ces items peuvent être entreposés dans des rooms et aussi être récupérés par un Player.
 * Le stockage de ces items se fait par le biais de la classe Itemlist
 * @author Richard Fouquoire
 * @version 06/05/2019
 */
public class Item
{
    private String aNom;
    private String aImage;
    private float aX;
    private float aY;
    private float aAncientX;
    private float aAncientY;
    private Rectangle aHitbox;
    private Rectangle aBackHitbox;
	private Animation aAnimation;
	private ArrayList<String> aFrames;
	private String aRessource;
    
    // ###Constructeurs###
    /**
     * Constructeur des objets de la classe Item
     * @param pNom le nom de l'item en String
     * @throws SlickException 
     */
    public Item(final String pNom, final String pItemRessource, final float pX, final float pY) throws SlickException
    {
        this.aNom=pNom;
        this.aRessource=pItemRessource;
        this.aImage="src/main/resources/MapGameState/item/"+pItemRessource+"/"+pItemRessource+".png";
        this.aX=pX;
        this.aY=pY;
        this.aAncientX=this.aX;
        this.aAncientY=this.aY;
        this.aAnimation=new Animation();
        this.aFrames=new ArrayList<>();
    }
    
    public void init() throws SlickException {
    	for(int j=0;j<this.aFrames.size();j++) {
    	    this.aAnimation.addFrame(new Image(this.aFrames.get(j)), 200); // "src/main/resources/MapGameState/item/sprites/"
    	}
    	for(int i=this.aFrames.size()-2;i>1;i--) {
    		this.aAnimation.addFrame(new Image(this.aFrames.get(i)), 200);//"src/main/resources/MapGameState/item/sprites/"+
    	}
    }
    
    public void render(Graphics pGraphics){
    	pGraphics.setColor(new Color(0, 0, 0, .5f));
        pGraphics.fillOval(this.aX+1, this.aY + 20, 20, 16);
        this.aAnimation.draw(this.aX, this.aY);
    }

    public void update(){
        this.aHitbox=new Rectangle(this.aX,this.aY+this.aAnimation.getHeight()/2,this.aAnimation.getWidth(),this.aAnimation.getHeight()/2);
        this.aBackHitbox=new Rectangle(this.aX,this.aY,this.aAnimation.getWidth(),this.aAnimation.getHeight()/2);
    }
    
    // ###Accesseurs###
    /**
     * Accesseur retournant le nom de l'item concerné
     * @return Le nom de l'item
     */
    public String getNom()
    {
        return aNom;
    }
    
    /**
     * Accesseur retournant l'image de l'item concerné
     * @return L'image de l'item
     */
    public String getImage()
    {
        return this.aImage;
    }
    
    public float getPositionX() {
    	return this.aX;
    }
    
    public float getPositionY() {
    	return this.aY;
    }
    
    public float getAncientX() {
    	return this.aAncientX;
    }
    
    public float getAncientY() {
    	return this.aAncientY;
    }
    
    public Rectangle getHitbox() {
    	return this.aHitbox;
    }
    
    public Rectangle getBackHitbox() {
    	return this.aBackHitbox;
    }
    
    public ArrayList<String> getFrames(){
    	return this.aFrames;
    }

    public String getRessource(){ return this.aRessource; }
    
    // ###Modificateurs###
    
    public void setNom(final String pNom) {
    	this.aNom=pNom;
    }
    
    public void setImage(final String pImage) {
    	this.aImage=pImage;
    }
    
    public void setPositionX(final float pX) {
    	this.aX=pX;
    }
    
    public void setPositionY(final float pY) {
    	this.aY=pY;
    }
    
}
