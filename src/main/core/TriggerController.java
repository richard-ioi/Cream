package main.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TriggerController extends Controller{
	//###Attributs###
	private Map aMap;
	private Character aCharacter;
	private boolean aIsOnTrigger;
	private boolean aMapTriggered;
	private MapGameState aMapGameState;
	private boolean aFinishedTransition;
	private CharacterController aCharacterController;

	public TriggerController(final Map pMap, final Character pCharacter, final MapGameState pMapGameState, final CharacterController pCharacterController) {
		super(true);
		this.aMap = pMap;
		this.aCharacter = pCharacter;
		this.aIsOnTrigger=false;
		this.aMapTriggered=false;
		this.aMapGameState=pMapGameState;
		this.aFinishedTransition=false;
		this.aCharacterController=pCharacterController;
	}

  public void update(final GameContainer pContainer, final StateBasedGame pGame){
	  if(super.aIsActive) {
		  this.aCharacter.setOnStairL(false);
		  this.aCharacter.setOnStairR(false);
		  for (int objectID = 0; objectID < this.aMap.getObjectCount(); objectID++) {
			  if (isInTrigger(objectID)) {
				  if ("teleport".equals(this.aMap.getObjectType(objectID))) {
					  if (this.aFinishedTransition) {
						  this.aFinishedTransition=false;
						  teleport(objectID);
						  this.aCharacterController.AllowMovement();
						  this.aIsOnTrigger = false;
					  } else if (!this.aIsOnTrigger) {
						  this.aIsOnTrigger = true;
						  this.aMapGameState.StartFade();
						  this.aCharacterController.DisallowMovement();
					  }
				  } else if ("stairL".equals(this.aMap.getObjectType(objectID))) {
					  this.aCharacter.setOnStairL(true);
				  } else if ("stairR".equals(this.aMap.getObjectType(objectID))) {
					  this.aCharacter.setOnStairR(true);
				  } else if ("change-map".equals(this.aMap.getObjectType(objectID))) {
					  if(this.aFinishedTransition){
						  this.aFinishedTransition=false;
						  changeMap(objectID, pContainer, pGame);
						  this.aCharacterController.AllowMovement();
						  this.aMapTriggered = false;
					  } else if (!this.aMapTriggered) {
						  this.aMapGameState.StartFade();
						  this.aMapTriggered = true;
						  this.aCharacterController.DisallowMovement();
					  }
				  } else if ("additionalCollision".equals(this.aMap.getObjectType(objectID))) {
					  this.aCharacterController.DisallowMovement();
					  if (this.aCharacter.getCharacterDirection() == 0) {
						  this.aCharacter.setCharacterPositionY(this.aCharacter.getCharacterPositionY() + 1);
					  } else if (this.aCharacter.getCharacterDirection() == 1) {
						  this.aCharacter.setCharacterPositionX(this.aCharacter.getCharacterPositionX() + 1);
					  } else if (this.aCharacter.getCharacterDirection() == 2) {
						  this.aCharacter.setCharacterPositionY(this.aCharacter.getCharacterPositionY() - 1);
					  } else if (this.aCharacter.getCharacterDirection() == 3) {
						  this.aCharacter.setCharacterPositionX(this.aCharacter.getCharacterPositionX() - 1);
					  }
				  }
			  }
		  }
	  }
	}
	    
	private boolean isInTrigger(int id) {
		return this.aCharacter.getCharacterPositionX() > this.aMap.getObjectX(id)
				&& this.aCharacter.getCharacterPositionX() < this.aMap.getObjectX(id) + this.aMap.getObjectWidth(id)
				&& this.aCharacter.getCharacterPositionY() > this.aMap.getObjectY(id)
				&& this.aCharacter.getCharacterPositionY()  < this.aMap.getObjectY(id) + this.aMap.getObjectHeight(id);
	}

	private void teleport(int pId) {
		this.aCharacter.setCharacterPositionX(Float.parseFloat(this.aMap.getObjectProperty(pId, "dest-x", Float.toString(this.aCharacter.getCharacterPositionX()))));
		this.aCharacter.setCharacterPositionY(Float.parseFloat(this.aMap.getObjectProperty(pId, "dest-y", Float.toString(this.aCharacter.getCharacterPositionY()))));
	}

	private void changeMap(int objectID, GameContainer pContainer, StateBasedGame pGame) {
		String newMap = this.aMap.getObjectProperty(objectID, "dest-map", "undefined");
		try {
			this.aMapGameState.LoadNewMap(newMap, pContainer, pGame);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public void setFinishedTransition(){
		this.aFinishedTransition=true;
	}
}
