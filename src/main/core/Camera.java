package main.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Camera {
	
  //###Attributs###
  private Character aCharacter;
  private float aCameraPositionX, aCameraPositionY;

  //###Constructeurs & initialisateurs ###

  public Camera(final Character pCharacter) {
	  this.aCharacter = pCharacter;
	  this.aCameraPositionX = this.aCharacter.getCharacterPositionX();
	  this.aCameraPositionY = this.aCharacter.getCharacterPositionY();
	}

  public void place(GameContainer container, Graphics pCharacter) {
	  pCharacter.translate(640 / 2 - this.aCameraPositionX, 640 / 2 - this.aCameraPositionY);
	}
  //###Accesseurs###
  public float getCameraPositionX() {
	  return this.aCameraPositionX;
  }

  public float getCameraPositionY() {
	  return this.aCameraPositionY;
  }
  //###Modificateurs###
  public void setCameraPositionX(float pX) {
	  this.aCameraPositionX=pX;
  }
  public void setCameraPositionY(float pY) {
	  this.aCameraPositionY=pY;
  }

  //###Autres méthodes###
  public void update(GameContainer pContainer) {
      int width = pContainer.getWidth() / 20;
      int height = pContainer.getHeight() / 20;
      if (this.aCharacter.getCharacterPositionX() > this.aCameraPositionX + width) this.aCameraPositionX = this.aCharacter.getCharacterPositionX()- width;
      if (this.aCharacter.getCharacterPositionX() < this.aCameraPositionX - width) this.aCameraPositionX = this.aCharacter.getCharacterPositionX() + width;
      if (this.aCharacter.getCharacterPositionY() > this.aCameraPositionY + height) this.aCameraPositionY = this.aCharacter.getCharacterPositionY() - height;
      if (this.aCharacter.getCharacterPositionY() < this.aCameraPositionY - height) this.aCameraPositionY = this.aCharacter.getCharacterPositionY() + height;
  }
}
