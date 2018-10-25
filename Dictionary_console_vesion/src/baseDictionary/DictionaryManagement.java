package dictionary;
//import static dictionary.ApiKeys.YANDEX_API_KEY;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryManagement{
    private ArrayList<Word> DictionaryData = new ArrayList<>();
    
    public ArrayList<Word> getData() {
        return DictionaryData;
    }
    
    //finding position of a word if dictionary has it or the position of the word front of it
    public int findPosition(String w){
        w = w.trim();
        int n = this.getData().size() - 1;
        int i = 0;
        
        if(w.compareTo(this.getData().get(0).getWord_target()) <= 0)
            return 0;
        
        while(i < n){
            if(w.compareTo(this.getData().get(n).getWord_target()) == 0)
                return n;
            
            if(w.compareTo(this.getData().get(n).getWord_target()) > 0)
                return n+1;//find "c" in "a", "b", "d" will return 2 pos of "d"
            
            Word w1 = this.getData().get((i + n)/2);
            int compare = w.compareTo(w1.getWord_target());

            if(compare == 0){
                return (i+n)/2;
                
            }else if(compare > 0){
                i = (i+n)/2 + 1;
                n --;
            } else {
                n = (i+n)/2;
            }
        }
        if(w.compareTo(this.getData().get(i).getWord_target()) > 0)
            return i+1;//find "c" in "a", "b", "d" will return 2 pos of "d"
        return i;
    }
    
    ///useless
    void addWord(String word, String pronounce, String info){
        this.DictionaryData.add(new Word(word, pronounce, info));
    }
    
    //add word into dictionary if it doesn't have
    public boolean addWord(Word word){

        if(this.getData().isEmpty()){
            this.DictionaryData.add(word);
            return true;
        }

        int i = this.findPosition(word.getWord_target());
        if(i >= this.DictionaryData.size()){
            this.DictionaryData.add(word);
        } else if(this.DictionaryData.get(i).compareTo(word) != 0){
            this.DictionaryData.add(i,word);
            return true;
        }
        return false;
    }
    
    //remove word from dictionary if it has
    boolean removeWord(Word word){
        if(this.DictionaryData.remove(word))
            return true;
                
        if(this.DictionaryData.isEmpty())
            return false;
            
        int i = this.findPosition(word.getWord_target());
        
        if(this.DictionaryData.get(i).compareTo(word) == 0){
            this.DictionaryData.remove(i);
            return true;
        } else
            return false;
    }
    
    // print out word and it's info
    void print(){
        for(Word w : this.DictionaryData)
            w.print();
    }
    
    //useless
    public void print(int tt){
        Word w = this.DictionaryData.get(tt);
        if(w != null)
            w.print();
    }
    //
    public void readFile(String filePath){
        String line;
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            while ((line = reader.readLine()) != null){//lấy đến hết
                String[] w = line.split("/");// tách phần phát âm ra
                String word = "";
                String pronounce ="";
                word += w[0].substring(0, w[0].length() - 1);//xếp phần chữ lại
                for(int i = 1; i < w.length; i++){
                        pronounce += "/" + w[i];//xếp phần phát âm lại
                }
                pronounce += "/";
                
                String explain = "" + reader.readLine();
                // "" là dòng giữa các từ khác nhau lấy hết phần info của từ trước dòng này
                while((line = reader.readLine())!= null && !line.equals("")){
                    explain += "\n" + line;
                }
                
                this.DictionaryData.add(new Word(word, pronounce, explain));
                //this.DictionaryData.get(this.DictionaryData.size()-1).print();
                
            }
        } catch (Exception e) {
            System.out.println(this.DictionaryData.size());
            System.out.println("can't read file: " + filePath);
        }
    }
    
    public void save(String filePath){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("\n");
            writer.flush();
            for( int i = 0; i < this.DictionaryData.size(); i++){
                Word word = this.DictionaryData.get(i);
                writer.write(word.getWord_target() + " " + word.getPronounce() + "\n" + word.getWord_explain() + "\n\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}