package main.core;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class CharacterController extends Controller implements KeyListener {
	//###Attributs###
	private Character aCharacter;
	private boolean aAllowedMovement;
	
	public CharacterController(final Character pCharacter) {
		super(true);
		this.aCharacter=pCharacter;
		this.aAllowedMovement=true;
	}
	
	@Override
	public void setInput(Input input) { }

	@Override
	public boolean isAcceptingInput() { return aIsActive; }

	@Override
	public void inputEnded() { }

	@Override
	public void inputStarted() { }

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
			case Input.KEY_UP:
			case Input.KEY_Z:
				if (this.aAllowedMovement) {
					this.aCharacter.setCharacterDirection(0);
					this.aCharacter.setMovingState(true);
				}
				break;
			case Input.KEY_LEFT:
			case Input.KEY_Q:
				if (this.aAllowedMovement) {
					this.aCharacter.setCharacterDirection(1);
					this.aCharacter.setMovingState(true);
				}
				break;
			case Input.KEY_DOWN:
			case Input.KEY_S:
				if (this.aAllowedMovement) {
					this.aCharacter.setCharacterDirection(2);
					this.aCharacter.setMovingState(true);
				}
				break;
			case Input.KEY_RIGHT:
			case Input.KEY_D:
				if (this.aAllowedMovement) {
					this.aCharacter.setCharacterDirection(3);
					this.aCharacter.setMovingState(true);
				}
				break;
		}
		if(!isAcceptingInput()){
			this.aCharacter.setMovingState(false);
		}
	}


	@Override
	public void keyReleased(int pKey, char pC) {
		switch(pKey){
			case Input.KEY_UP:
			case Input.KEY_Z:
				if(aCharacter.getCharacterDirection() == 0) this.aCharacter.setMovingState(false);
				break;
			case Input.KEY_LEFT:
			case Input.KEY_Q:
				if(aCharacter.getCharacterDirection() == 1) this.aCharacter.setMovingState(false);
				break;
			case Input.KEY_DOWN:
			case Input.KEY_S:
				if(aCharacter.getCharacterDirection() == 2) this.aCharacter.setMovingState(false);
				break;
			case Input.KEY_RIGHT:
			case Input.KEY_D:
				if(aCharacter.getCharacterDirection() == 3) this.aCharacter.setMovingState(false);
			break;
		}
	}

	public void AllowMovement(){
		this.aAllowedMovement=true;
	}
	public void DisallowMovement(){
		this.aAllowedMovement=false;
		this.aCharacter.setMovingState(false);
	}
}