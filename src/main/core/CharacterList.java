package main.core;

import java.util.HashMap;
/**
 * La classe CharacterList est une liste de Characters où des Characters peuvent
 * être indexés sur une Map.
 *
 * @author Richard Fouquoire
 * @version 06/05/2019
 */
public class CharacterList
{
    // ###Attributs###
    private HashMap<String, Character> aCharacters;
    
    // ###Constructeurs###
    /**
     * Constructeur des objets pour la classe ItemList
     */
    public CharacterList()
    {
        this.aCharacters= new HashMap<String, Character>();
    }
    
    // ###Accesseurs###
    /**
     * Accesseur retournant le Character pour la String correspondante passée 
     * en paramètre (son nom).
     * @param pCharacter le nom en String de l'Item concerné
     * @return Le character correspondant au nom rentré en paramètre
     */
    public Character getCharacter(final String pCharacter){
        return this.aCharacters.get(pCharacter);
    } 
    
    public HashMap<String, Character> getCharacterList() {
		return aCharacters;
    }
    
    // ###Modificateurs###
    /**
     * Ajoute un Character
     * @param pNom le Nom du Character
     * @param pCharacter Character
     */
    public void setCharacter(final String pNom, final Character pCharacter){
        this.aCharacters.put(pNom,pCharacter);
    }
    
    /**
     * Supprime un Character
     * @param pNom le Nom du character concerné
     */
    public void delCharacter(final String pNom){
        this.aCharacters.remove(pNom);
    }
    
    // ###Autres méthodes###
    /**
     * Vérifie si le Character passé en paramètre est dans la CharacterList et retourne
     * un booléen vrai ou faux.
     * @param pCharacter le nom de l'item concerné
     * @return un booléen nous informant si le character est présent dans la CharacterList
     */
    public boolean verifCharacter(final String pCharacter){
        return this.aCharacters.containsKey(pCharacter);
    }
    
    /**
     * Procédure retournant une String constituée des Characters de la CharacterList.
     * @return La string constituée des Characters de la CharacterList.
     */
    public String getCharactersString(){
        StringBuilder returnString = new StringBuilder( "Personnages:" );
        for ( String vS : aCharacters.keySet() ){
            returnString.append(vS+", " ); 
        }
        if (returnString.toString().equals("Personnages:")){
            return "";
        }else{
            return returnString.toString();
        }
    }
    
}
