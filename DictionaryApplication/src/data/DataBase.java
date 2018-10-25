package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class DataBase {
    private final String FILE_PATH = "AnhViet.txt";
    private HashMap<String, String> dicData;
    private ArrayList<String> keyList;

    public DataBase() {
        dicData = new HashMap<String, String>();
        keyList = new ArrayList<String>();
    }
    public String getInfoWord(String key){
        return dicData.get(key);
    }
    public ArrayList<String> getKeyList(){
        return keyList;
    }
    public String getFILE_PATH() {
        return FILE_PATH;
    }

    //finding position of a word if TranslatorImage has it or the position of the word front of it
//    public int findPosition(String word) {
//        System.out.println( " find:   " +word);
//        if (keyList.get(0).compareTo(word)>=0) return 0;
//        int i = 0, n = keyList.size();
//        while (i<=n-1){
//            int x = (i+n)/2;
//            if (keyList.get(x).compareTo(word)<0)
//                i=x;
//            else n=x;
//        }
//        return n;
//    }
    public int findPosition(String w){
        w = w.trim();
        int n = this.keyList.size() - 1;
        int i = 0;

        if(w.compareTo(this.keyList.get(0)) <= 0)
            return 0;

        while(i < n){
            if(w.compareTo(this.keyList.get(n)) == 0)
                return n;

            if(w.compareTo(this.keyList.get(n)) > 0)
                return n+1;//find "c" in "a", "b", "d" will return 2 pos of "d"

            String s = this.keyList.get((i + n)/2);
            int compare = w.compareTo(s);

            if(compare == 0){
                return (i+n)/2;

            }else if(compare > 0){
                i = (i+n)/2 + 1;
                n --;
            } else {
                n = (i+n)/2;
            }
        }
        if(w.compareTo(this.keyList.get(i)) > 0)
            return i+1;//find "c" in "a", "b", "d" will return 2 pos of "d"
        return i;
    }

    ///useless
    public void addWord(String word, String info) {
        if(!keyList.contains(word)){
            this.dicData.put(word, info);
            this.keyList.add(word);
            Collections.sort(keyList);
        }


    }

    //remove word from TranslatorImage if it has
    public void removeWord(String word)
    {
        if(keyList.contains(word)){
            this.dicData.remove(word);
            this.keyList.remove(word);
            System.out.println("test remove:  " + word);
        }

    }

    //make change in word's info
    public void changeWordInfo(String word, String info) {

        if(this.keyList.contains(word)){
            this.dicData.remove(word);
            this.dicData.put(word, info);
            System.out.println("test change:  " + word);
        }

    }

    public void readFile(String filePath) {
        String line;
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));


            while ((line = reader.readLine()) != null) {
                String word = "";
                String info;
                char[] a = line.toCharArray();

                int i = 0;
                while (a[i] != '<') {
                    word += a[i];
                    i++;
                }
                info = line.substring(i);
                this.dicData.put(word, info);
                this.keyList.add(word);
            }
            Collections.sort(keyList);
        } catch (Exception e) {
            System.out.println(this.keyList.size());
            System.out.println("Can't read file:" + filePath);
            e.printStackTrace();
        }
        System.out.println(this.keyList.size());
    }
}