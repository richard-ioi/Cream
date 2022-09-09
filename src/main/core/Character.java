package main.core;

import java.util.ArrayList;

import org.json.JSONObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Character
{
    // ###Attributs###
    private String aNom;
    private String aSprite;
    private ArrayList<ArrayList<String>> aDialogue;
    private float aCharacterPositionX;
	private float aCharacterPositionY;
    private float aFuturX;
    private float aFuturY;
	private float aVitesse;
	private int aCharacterDirection;
	private int aVitesseSprite;
	private int aDialogueL;
	private int aDialogueC;
    private int aCharacterWidth;
    private int aCharacterHeight;
	private boolean aMovingState;
	private boolean aOnStairR;
	private boolean aOnStairL;
	private boolean aCharacterCollision;
	private boolean aItemCollision;
	private boolean aIsInBack;
	private Animation[] aAnimationTab;
    private Rectangle aHitbox;
    private Rectangle aBackHitbox;
	private Map aMap;
	private ItemList aItemList;
	private boolean aIsInBackItem;
    private Animation aMovementAnimation;
    private static Animation aParle;
    private Music aSpeechMusic;
    private JSONObject aCharacterData;

    static {
        try {
            aParle = new Animation(new Image[]{new Image("src/main/resources/MapGameState/icons/parle1.png"), new Image("src/main/resources/MapGameState/icons/parle2.png"), new Image("src/main/resources/MapGameState/icons/parle3.png")},170);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    private static Image aWaitParle;
    static {
        try {
            aWaitParle = new Image("src/main/resources/MapGameState/icons/parle.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    // ###Constructeurs & update & rendering###
    public Character(final String pNom,final String pSprite, final Map pMap, final ItemList pItemList, final JSONObject pCharacterData)
    {
        this.aNom=pNom;
        this.aSprite="src/main/resources/MapGameState/characters/sprites/"+pSprite;
        this.aCharacterPositionX=pMap.getSpawnPositionX();
        this.aCharacterPositionY=pMap.getSpawnPositionY();
        this.aMap=pMap;
        this.aItemList=pItemList;
        try {
            this.aSpeechMusic = new Music("src/main/resources/MapGameState/sound/menu_text_types_out_01.wav");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.aCharacterData=pCharacterData;
        loadArguments();
    }

    public Character(final String pNom,final String pSprite, final float pPosX, final float pPosY, final Map pMap, final Music pSpeechMusic)
    {
        this.aNom=pNom;
        this.aSprite="src/main/resources/MapGameState/characters/sprites/"+pSprite;
        this.aCharacterPositionX=pPosX;
        this.aCharacterPositionY=pPosY;
        this.aMap=pMap;
        this.aItemList=new ItemList();
        this.aSpeechMusic=pSpeechMusic;
        loadArguments();
    }

    private void loadArguments(){
        this.aCharacterDirection=2;
        this.aMovingState=false;
        this.aOnStairR=false;
        this.aOnStairL=false;
        this.aAnimationTab=new Animation[8];
        this.aVitesse=.1f;
        this.aVitesseSprite=200;
        this.aDialogue = new ArrayList();
        this.aCharacterCollision=false;
        this.aDialogueL=0;
        this.aDialogueC=0;
        this.aItemCollision=false;
        this.aIsInBack=false;
        this.aIsInBackItem=false;
    }

    public void init() throws SlickException {
        SpriteSheet spriteSheet = new SpriteSheet(this.aSprite, 30, 48);
        this.aAnimationTab[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.aAnimationTab[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.aAnimationTab[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.aAnimationTab[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.aAnimationTab[4] = loadAnimation(spriteSheet, 1, 5, 0);
        this.aAnimationTab[5] = loadAnimation(spriteSheet, 1, 5, 1);
        this.aAnimationTab[6] = loadAnimation(spriteSheet, 1, 5, 2);
        this.aAnimationTab[7] = loadAnimation(spriteSheet, 1, 5, 3);

        this.aMovementAnimation=this.aAnimationTab[this.aCharacterDirection + (this.aMovingState ? 4 : 0)];

        this.aCharacterWidth=this.aMovementAnimation.getWidth();
        this.aCharacterHeight=this.aMovementAnimation.getHeight();
    }
    
    public void render(Graphics pGraphics){
        pGraphics.setColor(new Color(0, 0, 0, .5f));
        pGraphics.fillOval(this.aCharacterPositionX - 33, this.aCharacterPositionY - 20, 32, 16);
        pGraphics.drawAnimation(this.aMovementAnimation, this.aCharacterPositionX-32, this.aCharacterPositionY-60);
    }
    
    public void update(int pDelta) {
        this.aMovementAnimation=this.aAnimationTab[this.aCharacterDirection + (this.aMovingState ? 4 : 0)];
        this.aCharacterWidth=this.aMovementAnimation.getWidth();
        this.aCharacterHeight=this.aMovementAnimation.getHeight();
        this.aFuturX = getFuturX(pDelta);
        this.aFuturY = getFuturY(pDelta);
        this.aHitbox=new Rectangle(this.aFuturX-this.aCharacterWidth,this.aFuturY-this.aCharacterHeight/1.3f,this.aCharacterWidth,this.aCharacterHeight/2);
        this.aBackHitbox=new Rectangle(this.aFuturX-this.aCharacterWidth,this.aFuturY-this.aCharacterHeight*1.3f,this.aCharacterWidth,this.aCharacterHeight/2);
    	collisionManagement();
    }

    private void collisionManagement(){
        if (this.aMovingState) {
            boolean collisionMap = this.aMap.isCollision(this.aFuturX, this.aFuturY);
            this.aCharacterCollision = this.aMap.isCharacterCollision(this);
            this.aItemCollision = this.aMap.isItemCollision(this);
            this.aIsInBack = this.aMap.isCharacterBack(this);
            this.aIsInBackItem = this.aMap.isItemBack(this);
            if((!this.aCharacterCollision)&&(!this.aIsInBack)) {
                this.aMap.setBackTouchedCharacter(null);
            }
            if((!this.aItemCollision)&&(!this.aIsInBackItem)) {
                this.aMap.setBackTouchedItem(null);
            }
            if ((collisionMap)||(this.aCharacterCollision)) {
                this.aMovingState=false;
            }else if(this.aItemCollision){
                this.aMovingState=false;
            }else {
                this.aCharacterPositionX=this.aFuturX;
                this.aCharacterPositionY=this.aFuturY;
            }
        }
    }
    
    private Animation loadAnimation(final SpriteSheet spriteSheet, final int pStartX, final int pEndX, final int y) {
        Animation animation = new Animation();
        for (int x = pStartX; x < pEndX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), this.aVitesseSprite);
        }
        return animation;
    }
    
    public void renderParle(Graphics pGraphics) {
    	pGraphics.drawAnimation(aParle, this.aCharacterPositionX+3,this.aCharacterPositionY-60);
    }
    
    public void renderWaitParle(Graphics pGraphics) throws SlickException {
    	pGraphics.drawImage(aWaitParle,this.aCharacterPositionX+3,this.aCharacterPositionY-60);
    }
    
    // ###Accesseurs###

    public boolean getItemCollision() { return this.aItemCollision;}

    public ItemList getItemList() { return this.aItemList; }

    public Map getMap() { return this.aMap; }

    public int getDialogueL() { return this.aDialogueL; }

    public int getDialogueC() { return this.aDialogueC; }

    public boolean getCharacterCollision() { return this.aCharacterCollision; }

    public ArrayList<ArrayList<String>> getDialogueList(){ return this.aDialogue; }

    public Rectangle getHitbox(){ return this.aHitbox; }

    public Rectangle getBackHitbox(){ return this.aBackHitbox; }

    public String getCharacterName() { return this.aNom; }

    public float getCharacterPositionX() { return this.aCharacterPositionX; }

    public float getCharacterPositionY() { return this.aCharacterPositionY; }

    public int getCharacterDirection() { return this.aCharacterDirection; }

    public Music getSpeechMusic(){ return this.aSpeechMusic; }

    public JSONObject getCharacterData(){ return this.aCharacterData;}

    // ###Modificateurs###

    public void setDialogueL(final int pNombre) { this.aDialogueL=pNombre; }

    public void setDialogueC(final int pNombre) { this.aDialogueC=pNombre; }

    public void setDialogueList(final int pL, final ArrayList<String> pDialogue) { this.aDialogue.add(pL,pDialogue); }

    public void setCharacterPositionX(final float pX) {
        this.aCharacterPositionX= pX;
    }

    public void setCharacterPositionY(final float pY) {
        this.aCharacterPositionY=pY;
    }

    public void setCharacterDirection(final int pDirection) {
        this.aCharacterDirection=pDirection;
    }

    public void setMovingState(final boolean pState) {
        this.aMovingState=pState;
    }

    public void setOnStairL(final boolean pState) {
        this.aOnStairL=pState;
    }

    public void setOnStairR(final boolean pState) {
        this.aOnStairR=pState;
    }

    public void setSpeed(final String pVitesse) {
        if(pVitesse.equals("normal")) {
            this.aVitesse=.1f;
            this.aVitesseSprite=100;
        }else if(pVitesse.equals("rapide")) {
            this.aVitesse=.2f;
            this.aVitesseSprite=90;
        }else if(pVitesse.equals("god")) {
            this.aVitesse=.3f;
            this.aVitesseSprite=70;
        }
    }
    public String getDialogue(){
        try {
            return this.getDialogueList().get(this.getDialogueL()).get(this.getDialogueC());
        } catch (Exception e) {
            return "";
        }
    }
    private float getFuturX(final int pDelta) {
        float futurX = this.aCharacterPositionX;
        switch (this.aCharacterDirection) {
        case 1: futurX = this.aCharacterPositionX - this.aVitesse * pDelta; break;
        case 3: futurX = this.aCharacterPositionX + this.aVitesse * pDelta; break;
        }
        return futurX;
    }

    private float getFuturY(final int pDelta) {
        float futurY = this.aCharacterPositionY;
            switch (this.aCharacterDirection) {
                // cas ou monte je diminue Y
                case 0: futurY = this.aCharacterPositionY- this.aVitesse * pDelta; break;
                // cas ou descend j'augmente Y
                case 2: futurY = this.aCharacterPositionY + this.aVitesse * pDelta; break;
                // cas ou je vais à gauche
                case 1:
                    // si je suis sur un escalier qui monte
                    if (this.aOnStairL) {
                    // je diminue Y
                    futurY = this.aCharacterPositionY - this.aVitesse * pDelta;
                    // sinon si je suis sur un escalier qui descend
                    } else if (this.aOnStairR) {
                    // j'augmente Y
                    futurY = this.aCharacterPositionY + this.aVitesse * pDelta;
                    }
                    break;
                // cas ou je vais à droite
                case 3:
                    // si je suis sur un escalier qui descend
                    if (this.aOnStairL) {
                        // j'augmente Y
                        futurY = this.aCharacterPositionY + this.aVitesse * pDelta;
                    // sinon si je suis sur un escalier qui monte
                    } else if (this.aOnStairR) {
                        // je diminue Y
                        futurY = this.aCharacterPositionY - this.aVitesse * pDelta;
                    }
                    break;
          }
          return futurY;
    }
}