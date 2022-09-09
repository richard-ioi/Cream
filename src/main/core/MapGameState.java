package main.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static main.core.JSONRessources.loadMainCharacter;

class MapGameState extends BasicGameState {

	// ###ATTRIBUTS###
	public static final int ID = 2;
	private Map aMap;
	private Character aMainCharacter;
	private Camera aCamera;
	private int aCompteurFade;
	private int aCompteurRevealFade;
	private boolean aFade;
	private Scenario aScenario;
	private String aNextScenario;
	private String aNomSauvegarde;
	private Inventory aInventaire;
	private InventoryController aInventoryController;
	private CharacterController aCharacterController;
	private TriggerController aTriggerController;
	private DialogueController aDialogueController;
	private ItemController aItemController;
	private Controller[] aControllers;
	private String aMapName;

	// ###CONSTRUCTEURS###
    public MapGameState(final String pMapName) {
		this.aMapName=pMapName;
    }
    
    // ###Méthodes de base du jeu (initialisation, rendu, mise à jour)###
    @Override
    public void init(final GameContainer pContainer, final StateBasedGame game) throws SlickException {
		this.aMap=new Map(this.aMapName);
		this.aMainCharacter=loadMainCharacter(this.aMap);
		this.aCamera=new Camera(this.aMainCharacter);
		this.aInventaire=new Inventory(this.aMainCharacter,this.aMap,this.aCamera);
		this.aCompteurFade=0;
		this.aCompteurRevealFade=50;
		this.aFade=false;
		this.aNomSauvegarde="principale";
		loadScenario();
    	this.aMap.init(); //Chargement de la carte
    	this.aMainCharacter.init(); //Chargement du personnage
    	this.aMainCharacter.setSpeed("normal");

		TextBox.aMap=this.aMap;
		TextBox.aCamera=this.aCamera;
		TextBox.aMainCharacter=this.aMainCharacter;
		initControllers(pContainer);
    }

	public void initControllers(final GameContainer pContainer){
		pContainer.getInput().removeAllKeyListeners();
		this.aInventoryController = new InventoryController(this.aInventaire);
		this.aCharacterController = new CharacterController(this.aMainCharacter);
		this.aTriggerController = new TriggerController(this.aMap, this.aMainCharacter, this, this.aCharacterController);
		this.aDialogueController = new DialogueController();
		this.aItemController = new ItemController();

		this.aControllers=new Controller[5];
		this.aControllers[0]=this.aInventoryController;
		this.aControllers[1]=this.aCharacterController;
		this.aControllers[2]=this.aTriggerController;
		this.aControllers[3]=this.aDialogueController;
		this.aControllers[4]=this.aItemController;
		Controller.setControllers(this.aControllers);

		pContainer.getInput().addKeyListener(this.aCharacterController);
		pContainer.getInput().addKeyListener(this.aInventoryController);
		pContainer.getInput().addKeyListener(this.aDialogueController);
		pContainer.getInput().addKeyListener(this.aItemController);
	}

	@Override
    public void render(final GameContainer pContainer,final StateBasedGame game, final Graphics pGraphics) throws SlickException {
    	this.aCamera.place(pContainer, pGraphics);
    	this.aMap.renderBackground(pGraphics); // Render de la map (arrière plan)
		this.aMainCharacter.render(pGraphics);
    	this.aMap.renderForeground(pGraphics); // Render de la map (premier plan)
    	renderFade(pGraphics);
		renderRevealFade(pGraphics);
		this.aInventaire.render(pGraphics);
		this.aDialogueController.render(pGraphics);
		this.aItemController.render(pGraphics);
	}
	
	@Override
    public void update(final GameContainer pContainer, final StateBasedGame game, final int pDelta) throws SlickException {
    	this.aTriggerController.update(pContainer, game); // Mise à jour du trigger
    	this.aMainCharacter.update(pDelta); // Mise à jour du personnage (déplacements etc)
    	this.aCamera.update(pContainer); //Mise à jour de la caméra
		this.aDialogueController.update();
		this.aItemController.update();
		this.aMap.update(pDelta);
    }

	@Override
	public int getID() {
		return ID;
	}

    // ### Méthodes LOAD ###

    public void loadScenario() {
    	try{
    		CharacterList vCharacterList=new CharacterList();
    		Scanner vLecteurScenario=new Scanner(new File("src/main/data/Scenario/"+this.aNextScenario+".txt"));
    		String vCharacters[]=vLecteurScenario.nextLine().split("//");
    		for(int i=0;i<vCharacters.length;i++) {
				vCharacterList.setCharacter(vCharacters[i], this.aMap.getCharacterList().getCharacter(vCharacters[i]));
				this.aMap.getCharacterList().getCharacter(vCharacters[i]).setDialogueL(this.aMap.getCharacterList().getCharacter(vCharacters[i]).getDialogueL() + 1);
				this.aMap.getCharacterList().getCharacter(vCharacters[i]).setDialogueC(0);
    		}
    		String vSecondLine[]=vLecteurScenario.nextLine().split("//");
			this.aScenario=new Scenario(vCharacterList,vSecondLine[0],this.aNextScenario);
    		if(vSecondLine[0].equals("Item")) {
				this.aScenario.setSearchedItem(vSecondLine[1]); // this.aMap.getItemList().getItem(vSecondLine[1])
			} else if (vSecondLine[0].equals("Character")){
				this.aScenario.setSearchedCharacter(vSecondLine[1]);
			} else if (vSecondLine[0].equals("Map")){
    			this.aScenario.setSearchedMap(vSecondLine[1]);
			}
    		if(vLecteurScenario.hasNextLine()) {
    			this.aNextScenario=vLecteurScenario.nextLine();
    		}
    	}catch (final FileNotFoundException pFNFE){
    	}
    }

    // ###Autres méthodes annexes###

    private void renderFade(final Graphics pGraphics) {
    	if(this.aCompteurFade>0){
    		if((!this.aFade)&&(this.aCompteurFade<50)){
    			this.aCharacterController.DisallowMovement();
	    		this.aCompteurFade+=1;
	    	}else if(this.aCompteurFade==50) {
				this.aCharacterController.DisallowMovement();
				this.aFade = true;
				this.aTriggerController.setFinishedTransition();
			}
    		if(this.aFade) {
				if(this.aCompteurFade==49){
					this.aCamera.setCameraPositionX(this.aMainCharacter.getCharacterPositionX());//On recentre la caméra
					this.aCamera.setCameraPositionY(this.aMainCharacter.getCharacterPositionY());//Ici aussi
				}
    			if(this.aCompteurFade>20){
					this.aCharacterController.DisallowMovement();
    			}
    			this.aCompteurFade-=1;
	    	}
			pGraphics.setColor(new Color(0, 0, 0, this.aCompteurFade*10));
			pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()-1000,this.aCamera.getCameraPositionY()-500,2000,1160,0));
    	}else {
    		this.aFade=false;
			this.aCharacterController.AllowMovement();
    	}
    }

	/**
	 * Used to create the "reveal" fade when changing map
	 * @param pGraphics
	 */
	private void renderRevealFade(final Graphics pGraphics){
		if(this.aCompteurRevealFade>0){
			this.aCompteurRevealFade-=1;
			pGraphics.setColor(new Color(0, 0, 0, this.aCompteurRevealFade*10));
			pGraphics.fill(new RoundedRectangle(this.aCamera.getCameraPositionX()-1000,this.aCamera.getCameraPositionY()-500,2000,1160,0));
		}
	}
    
    public void renderHitboxes(Graphics pGraphics) {
    	for ( String vS :  this.aMap.getItemList().getItemList().keySet() ){
    		pGraphics.setColor(new Color(0, 0, 0, 250));
    		pGraphics.draw(this.aMap.getItemList().getItem(vS).getHitbox());
    		pGraphics.setColor(new Color(0, 255, 0, 250));
    		pGraphics.draw(this.aMap.getItemList().getItem(vS).getBackHitbox());
    	}
    	for ( String vS :  this.aMap.getCharacterList().getCharacterList().keySet() ){
    		pGraphics.setColor(new Color(0, 0, 0, 250));
    		pGraphics.draw(this.aMap.getCharacterList().getCharacter(vS).getHitbox());
    		pGraphics.setColor(new Color(0, 255, 0, 250));
    		pGraphics.draw(this.aMap.getCharacterList().getCharacter(vS).getBackHitbox());
    	}
		pGraphics.setColor(new Color(0, 0, 0, 250));
		pGraphics.draw(this.aMainCharacter.getHitbox());
		pGraphics.setColor(new Color(0, 255, 0, 250));
		pGraphics.draw(this.aMainCharacter.getBackHitbox());
    }

	public void LoadNewMap(String pMapName, GameContainer pContainer, StateBasedGame pGame) throws SlickException {
		this.aMapName=pMapName;
		init(pContainer,pGame);
	}

	public void StartFade(){
		this.aCompteurFade+=1;
	}
}