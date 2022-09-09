package main.core;

import java.util.HashMap;
/**
 * La classe ItemList est une liste d'Items où des items pouvant être indexés
 * par exemple dans une Room ou sur un Player.
 *
 * @author Richard Fouquoire
 * @version 06/05/2019
 */
public class ItemList
{
    // instance variables - replace the example below with your own
    private HashMap<String, Item> aItems;
    
    // ###Constructeurs###
    /**
     * Constructeur des objets pour la classe ItemList
     */
    public ItemList()
    {
        this.aItems= new HashMap<String, Item>();
    }
    
    // ###Accesseurs###
    /**
     * Accesseur retournant l'Item pour la String correspondante passée 
     * en paramètre (son nom).
     * @param pItem le nom en String de l'Item concerné
     * @return L'item correspondant au nom rentré en paramètre
     */
    public Item getItem(final String pItem){
        return this.aItems.get(pItem);
    } 
    
    public HashMap<String, Item> getItemList(){
    	return this.aItems;
    }
    
    // ###Modificateurs###
    
    /**
     * Ajoute un Item.
     * @param pNom le Nom de l'Item
     * @param pItem L'Item
     */
    public void addItem(final String pNom, final Item pItem){
        this.aItems.put(pNom,pItem);
    }
    
    /**
     * Supprime un Item
     * @param pNom le Nom de l'item concerné
     */
    public void delItem(final String pNom){
        this.aItems.remove(pNom);
    }
    
    // ###Autres méthodes###
    /**
     * Vérifie si l'Item passé en paramètre est dans l'ItemList et retourne
     * un booléen vrai ou faux.
     * @param pItem le nom de l'item concerné
     * @return un booléen nous informant si l'item est présent dans l'ItemList
     */
    public boolean verifItem(final String pItem){
        return this.aItems.containsKey(pItem);
    }
    
    /**
     * Procédure retournant une String constituée des Items de l'ItemList.
     * @return La string constituée des tems de l'ItemList.
     */
    public String getItemsString(){
        StringBuilder returnString = new StringBuilder( "Items:" );
        for ( String vS : aItems.keySet() ){
            returnString.append(vS+ ", " ); 
        }
        if (returnString.toString().equals("Items:")){
            return "";
        }else{
            return returnString.toString();
        }
    }
    
}