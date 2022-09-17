package main.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONRessources {
    public static JSONObject openJsonFile(String pFileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(pFileName));
        String str = "";
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();
        return new JSONObject(str);
    }

    public static void saveJsonFile(JSONObject pJSONObject, String pFileName) throws FileNotFoundException {
        try {
            FileWriter file = new FileWriter(pFileName);
            file.write(pJSONObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadItems(JSONObject pJSONObject, ItemList pItemList) throws NumberFormatException, SlickException {
        JSONArray itemsArray = pJSONObject.getJSONArray("items");
        for (int i=0; i < itemsArray.length(); i++) {
            JSONObject vItemData = itemsArray.getJSONObject(i);
            String vItemName = vItemData.getString("nom");
            String vItemRessource = vItemData.getString("ressourceItem");
            float vItemPosX = vItemData.getFloat("posX");
            float vItemPosY = vItemData.getFloat("posY");
            Item vItem= new Item(vItemName,vItemRessource,vItemPosX,vItemPosY);

            File itemFolder = new File("src/main/resources/MapGameState/item/"+vItemRessource);
            File[] listOfSprites = itemFolder.listFiles();
            for (File sprite : listOfSprites) {
                if (sprite.isFile()) {
                    vItem.getFrames().add("src/main/resources/MapGameState/item/"+vItemRessource+'/'+sprite.getName());
                }
            }
            pItemList.addItem(vItemName,vItem);
        }
    }

    public static void loadCharacters(JSONObject pJSONObject, Map pMap) {
        JSONArray charactersArray = pJSONObject.getJSONArray("characters");
        for (int i = 0; i < charactersArray.length(); i++) {
            JSONObject vCharacterData = charactersArray.getJSONObject(i);
            String vCharacterName = vCharacterData.getString("nom");
            String vCharacterRessource = vCharacterData.getString("ressourceCharacter");
            float vCharacterPosX = vCharacterData.getFloat("posX");
            float vCharacterPosY = vCharacterData.getFloat("posY");
            Music vCharacterMusicRessource=null;
            try {
                vCharacterMusicRessource = new Music("src/main/resources/MapGameState/sound/"+vCharacterData.getString("ressourceMusic"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
            Character vCharacter = new Character(vCharacterName, vCharacterRessource, vCharacterPosX, vCharacterPosY, pMap, vCharacterMusicRessource);

            JSONArray dialogueArray = charactersArray.getJSONObject(i).getJSONArray("dialogues");
            for (int j = 0; j < dialogueArray.length(); j++) {
                JSONArray ligneArray = dialogueArray.getJSONArray(j);
                ArrayList<String> vLigne = new ArrayList();
                for(int k=0;k<ligneArray.length();k++) {
                    vLigne.add(ligneArray.getString(k));
                }
                vCharacter.setDialogueList(j,vLigne);
            }
            pMap.getCharacterList().setCharacter(vCharacterName, vCharacter);
        }
    }

    public static Character loadMainCharacter(Map pMap){
        JSONObject vJSONObject= null;
        try {
            vJSONObject = openJsonFile("src/main/data/MainCharacter/MainCharacter.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ItemList vItemList=new ItemList();
        try {
            loadItems(vJSONObject,vItemList);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        String vCharacterName = vJSONObject.getString("nom");
        String vCharacterRessource = vJSONObject.getString("ressourceCharacter");
        return new Character(vCharacterName, vCharacterRessource, pMap, vItemList, vJSONObject);
    }

    public static void deleteItemFromJSONObject(JSONObject pJSONObject, String pSearchedItem) {
        JSONArray itemsArray = pJSONObject.getJSONArray("items");
        for (int i=0; i < itemsArray.length(); i++) {
            JSONObject vItemData = itemsArray.getJSONObject(i);
            if(vItemData.getString("nom").equals(pSearchedItem)){
                itemsArray.remove(i);
            }
        }
    }
    public static void addItemToJSONObject(JSONObject pJSONObject, Item pItem) {
        JSONArray itemsArray = pJSONObject.getJSONArray("items");
        JSONObject item = new JSONObject();
        item.put("nom",pItem.getNom());
        item.put("ressourceItem",pItem.getRessource());
        item.put("posX",pItem.getPositionX());
        item.put("posY",pItem.getPositionY());
        itemsArray.put(item);
    }
}
