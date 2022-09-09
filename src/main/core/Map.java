package main.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;

import org.json.JSONObject;

import static main.core.JSONRessources.*;

public class Map {
// ### Attributs ###
  private TiledMap aTiledMap;
  private CharacterList aCharacterList;
  private String aNomMap;
  private boolean aUpdate;
  private Character aTouchedCharacter;
  private Character aBackTouchedCharacter;
  private ItemList aItemList;
  private Item aTouchedItem;
  private Item aBackTouchedItem;
  private JSONObject aMapData;
  private Music aMusic;
  private int aSpawnPositionX;
  private int aSpawnPositionY;

// ###Constructeurs, Méthodes d'initialisation et d'affichage###
  public Map(final String pNomMap) {
	  this.aCharacterList=new CharacterList();
	  this.aNomMap=pNomMap;
	  this.aUpdate=false;
	  this.aItemList=new ItemList();
	  this.aTouchedItem=null;
	  this.aBackTouchedCharacter=null;
	  try {
		  this.aMapData=openJsonFile("src/main/data/Map/"+pNomMap+".json");
	  } catch (FileNotFoundException e) {
		  e.printStackTrace();
	  }
	  this.aSpawnPositionX = this.aMapData.getJSONObject("spawnPosition").getInt("posX");
	  this.aSpawnPositionY = this.aMapData.getJSONObject("spawnPosition").getInt("posY");
  }
  public void init()  throws SlickException {
	  this.aTiledMap = new TiledMap("src/main/resources/MapGameState/map/"+aMapData.getString("tiledMap")+".tmx","src/main/resources/MapGameState/map");
	  this.aMusic = new Music("src/main/resources/MapGameState/sound/"+aMapData.getString("music"));
	  startMusic();
	  loadItems(this.aMapData, this.aItemList);
	  loadCharacters(this.aMapData,this);
	  initMapCharacters();
	  initMapItems();
  }

  public void initMapCharacters() throws SlickException {
	  for ( String vS : this.aCharacterList.getCharacterList().keySet() ){
		  this.aCharacterList.getCharacterList().get(vS).init();
	  }
  }
  public void initMapItems() throws SlickException {
	  for ( String vS : this.aItemList.getItemList().keySet() ){
		  this.aItemList.getItemList().get(vS).init();
	  }
  }

  public void update(final int pDelta){
	  updateMapItems();
	  updateMapCharacters(pDelta);
  }
  public void updateMapItems(){
	  for ( String vS : this.aItemList.getItemList().keySet()) {
		  this.aItemList.getItemList().get(vS).update();
	  }
  }

  public void updateMapCharacters(final int pDelta){
	  for ( String vS :  this.aCharacterList.getCharacterList().keySet() ){
		  this.aCharacterList.getCharacter(vS).update(pDelta);
	  }
  }

  public void renderBackground(final Graphics pGraphics) throws SlickException {
		this.aTiledMap.render(0, 0, 0);
		this.aTiledMap.render(0, 0, 1);
		this.aTiledMap.render(0, 0, 2);
		this.aTiledMap.render(0, 0, 3);
	  this.renderMapItemsBackground(pGraphics);
	  this.renderMapCharactersBackground(pGraphics);
  }
public void renderForeground(final Graphics pGraphics) throws SlickException {
	//this.aTiledMap.render(0, 0, 4);
	this.renderMapCharactersForeground(pGraphics);
	this.renderMapItemsForeground(pGraphics);
}


  public void renderMapItemsBackground(Graphics pGraphics) throws SlickException{
	  for ( String vS : this.aItemList.getItemList().keySet()) {
		  if(this.aItemList.getItemList().get(vS)!=this.aBackTouchedItem) {
				  this.aItemList.getItemList().get(vS).render(pGraphics);
		  }
	  }
  }

  public void renderMapItemsForeground(Graphics pGraphics) throws SlickException{
	  for ( String vS : this.aItemList.getItemList().keySet()) {
		  if(this.aItemList.getItemList().get(vS)==this.aBackTouchedItem) {
				  this.aItemList.getItemList().get(vS).render(pGraphics);
		  }
	  }
  }

  public void renderMapCharactersForeground(Graphics pGraphics) throws SlickException {
	  for ( String vS : this.aCharacterList.getCharacterList().keySet() ){
		  if(this.aCharacterList.getCharacterList().get(vS)==this.aBackTouchedCharacter) {
			this.aCharacterList.getCharacterList().get(vS).render(pGraphics);
		}
	  }
  }

  public void renderMapCharactersBackground(Graphics pGraphics) throws SlickException {
	  for ( String vS : this.aCharacterList.getCharacterList().keySet() ){
		  if(this.aCharacterList.getCharacterList().get(vS)!=this.aBackTouchedCharacter) {
			this.aCharacterList.getCharacterList().get(vS).render(pGraphics);
		}
	  }
  }

  // ###Accesseurs###
	public Item getTouchedItem() {
	  return this.aTouchedItem;
	}
	public ItemList getItemList() {
	  return this.aItemList;
	}
	public Character getTouchedCharacter() {
		return this.aTouchedCharacter;
	}
	public CharacterList getCharacterList(){
		return this.aCharacterList;
	}
	public boolean getUpdate() {
	  return this.aUpdate;
	}
	public String getNomMap() {
	  return this.aNomMap;
	}
	public int getObjectCount() {
	return this.aTiledMap.getObjectCount(0);
	}
	public String getObjectType(int objectID) {
	return this.aTiledMap.getObjectType(0, objectID);
	}
	public float getObjectX(int objectID) {
	return this.aTiledMap.getObjectX(0, objectID);
	}
	public float getObjectY(int objectID) {
	return this.aTiledMap.getObjectY(0, objectID);
	}
	public float getObjectWidth(int objectID) {
	return this.aTiledMap.getObjectWidth(0, objectID);
	}
	public float getObjectHeight(int objectID) {
	return this.aTiledMap.getObjectHeight(0, objectID);
	}
	public String getObjectProperty(int objectID, String propertyName, String def) { return this.aTiledMap.getObjectProperty(0, objectID, propertyName, def);}
	public int getSpawnPositionX(){return this.aSpawnPositionX;}
	public int getSpawnPositionY(){return this.aSpawnPositionY;}
	public JSONObject getMapData(){ return this.aMapData; }

  // ###Modificateurs###
  public void setTouchedItem(final Item pItem) {
	  this.aTouchedItem=pItem;
  }
  public void setTouchedCharacter(final Character pCharacter) {
	this.aTouchedCharacter=pCharacter;
}
  public void setBackTouchedCharacter(final Character pCharacter) {
	this.aBackTouchedCharacter=pCharacter;
}
  public void setBackTouchedItem(final Item pItem) {
	this.aBackTouchedItem=pItem;
}
  public void setCharacter(final String pNom, final Character pCharacter){ this.aCharacterList.setCharacter(pNom,pCharacter); }
  public void setUpdate(final boolean pState) {
	this.aUpdate=pState;
}

// ###Autres méthodes###
	public boolean isItemCollision(final Character pCharacter) {
		for ( String vS :  this.aItemList.getItemList().keySet() ){
			if (this.aItemList.getItem(vS).getHitbox().intersects(pCharacter.getHitbox())) {
				this.aTouchedItem=this.aItemList.getItem(vS);
				return true;
			}
		}
		return false;
	}

	public boolean isItemBack(final Character pCharacter) {
		for ( String vS :  this.aItemList.getItemList().keySet() ){
			if (this.aItemList.getItem(vS).getBackHitbox().intersects(pCharacter.getHitbox())) {
				this.aBackTouchedItem=this.aItemList.getItem(vS);
				return true;
			}
		}
		return false;
	}

	public boolean isCharacterCollision(final Character pCharacter) {
		for ( String vS :  this.aCharacterList.getCharacterList().keySet() ){
			if(!this.aCharacterList.getCharacter(vS).equals(pCharacter)) {
				if(this.aCharacterList.getCharacter(vS).getHitbox().intersects(pCharacter.getHitbox())){
					this.aTouchedCharacter=this.aCharacterList.getCharacter(vS);
					return true;
				}
			}
		}
		return false;
	}
	public boolean isCharacterBack(final Character pCharacter) {
		for ( String vS :  this.aCharacterList.getCharacterList().keySet() ){
			if(!vS.equals(pCharacter.getCharacterName())) {
				if (this.aCharacterList.getCharacter(vS).getBackHitbox().intersects(pCharacter.getHitbox())) {
					this.aBackTouchedCharacter=this.aCharacterList.getCharacter(vS);
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollision(final float pX, final float pY) {
		int tileW = this.aTiledMap.getTileWidth();
		int tileH = this.aTiledMap.getTileHeight();
		int collisionLayer = this.aTiledMap.getLayerIndex("collision");
		Image tile = this.aTiledMap.getTileImage((int) pX / tileW, (int) pY / tileH, collisionLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) pX % tileW, (int) pY % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	private void startMusic(){this.aMusic.loop();}
	private void stopMusic(){this.aMusic.stop();}
}