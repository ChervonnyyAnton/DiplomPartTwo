package site.nomoreparties.stellarburgers.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ingredients {

    private HashMap<String, String> ingredients;
    private String[] list = new String[] {"Флюоресцентная булка R2-D3", "Мясо бессмертных моллюсков Protostomia", "Биокотлета из марсианской Магнолии","Говяжий метеорит (отбивная)","Соус Spicy-X","Филе Люминесцентного тетраодонтимформа","Соус фирменный Space Sauce","Соус традиционный галактический","Соус с шипами Антарианского плоскоходца","Хрустящие минеральные кольца","Плоды Фалленианского дерева","Кристаллы марсианских альфа-сахаридов","Мини-салат Экзо-Плантаго","Сыр с астероидной плесенью"};
    private ArrayList<String> order;
    private Random random;

    public HashMap<String, String> setUpHashMap() {
        ingredients = new HashMap<String, String>();
        ingredients.put("Флюоресцентная булка R2-D3", "61c0c5a71d1f82001bdaaa6d");
        ingredients.put("Мясо бессмертных моллюсков Protostomia", "61c0c5a71d1f82001bdaaa6f");
        ingredients.put("Биокотлета из марсианской Магнолии", "61c0c5a71d1f82001bdaaa71");
        ingredients.put("Говяжий метеорит (отбивная)", "61c0c5a71d1f82001bdaaa70");
        ingredients.put("Соус Spicy-X", "61c0c5a71d1f82001bdaaa72");
        ingredients.put("Филе Люминесцентного тетраодонтимформа", "61c0c5a71d1f82001bdaaa6e");
        ingredients.put("Соус фирменный Space Sauce", "61c0c5a71d1f82001bdaaa73");
        ingredients.put("Соус традиционный галактический", "61c0c5a71d1f82001bdaaa74");
        ingredients.put("Соус с шипами Антарианского плоскоходца", "61c0c5a71d1f82001bdaaa75");
        ingredients.put("Хрустящие минеральные кольца", "61c0c5a71d1f82001bdaaa76");
        ingredients.put("Плоды Фалленианского дерева", "61c0c5a71d1f82001bdaaa77");
        ingredients.put("Кристаллы марсианских альфа-сахаридов", "61c0c5a71d1f82001bdaaa78");
        ingredients.put("Мини-салат Экзо-Плантаго", "61c0c5a71d1f82001bdaaa79");
        ingredients.put("Сыр с астероидной плесенью", "61c0c5a71d1f82001bdaaa7a");
        return ingredients;
    }

    public String getIngredientsHash(String name){
        return ingredients.get(name);
    }

    public ArrayList getRandomList(){
        ingredients = new HashMap<>();
        ingredients = setUpHashMap();
        random = new Random();
        order = new ArrayList<>();
        int length = list.length;
        int count = random.nextInt(length);
        for(int i = 0; i <= count; i++){
            int n = random.nextInt(length);
            String m = list[n];
            order.add(ingredients.get(m));
        }
        return order;
    }

    public ArrayList getRandomInvalidList(){
        ingredients = new HashMap<>();
        ingredients = setUpHashMap();
        random = new Random();
        order = new ArrayList<>();
        int length = list.length;
        int count = random.nextInt(length);
        for(int i = 0; i <= count; i++){
            int n = random.nextInt(length);
            order.add("61c0c5a71d1f82001bdaaa6g");
        }
        return order;
    }
}
