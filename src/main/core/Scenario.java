package main.core;

public class Scenario {
	//###Attributs###
	private CharacterList aCharacterList;
	private String aType;
	private String aNom;
	private String aSearchedItem;
	private String aSearchedCharacter;
	private String aSearchedMap;
	
	//###Constructeurs###
	public Scenario(final CharacterList pCharacterList, final String pType, final String pNom) {
		this.aCharacterList=pCharacterList;
		this.aType=pType;
		this.aNom=pNom;
	}
	
	//###Accesseurs###
	public CharacterList getCharacterList() {
		return this.aCharacterList;
	}
	
	public String getType() {
		return this.aType;
	}
	
	public String getNom() {
		return this.aNom;
	}
	
	public String getSearchedItem() {
		return this.aSearchedItem;
	}

	public String getSearchedCharacter(){
		return this.aSearchedCharacter;
	}

	public String getSearchedMap(){
		return this.aSearchedMap;
	}
	
	
	//###Modificateurs###
	public void setCharacterList(final CharacterList pCharacterList) {
		this.aCharacterList=pCharacterList;
	}
	
	public void setType(final String pType) {
		this.aType=pType;
	}
	
	public void setNom(final String pNom) {
		this.aNom=pNom;
	}
	
	public void setSearchedItem(final String pItem) {
		this.aSearchedItem=pItem;
	}

	public void setSearchedCharacter(final String pCharacter){
		this.aSearchedCharacter=pCharacter;
	}

	public void setSearchedMap(final String pMap){
		this.aSearchedMap=pMap;
	}

}
