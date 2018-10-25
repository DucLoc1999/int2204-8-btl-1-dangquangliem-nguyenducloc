
import dictionary.DictionaryManagement;
import dictionary.Word;
import java.util.Scanner;
import speechToTextAPI.CaptureVoice;
import speechToTextAPI.SpeechRecognize;
import textToSpeech.TextToSpeech;
import translateAPI.Language;
import translateAPI.Translate;
import imageToText.ImageToText;

public class Main {
    public static DictionaryManagement dictionary = new DictionaryManagement();
    public static Scanner scan = new Scanner(System.in);
    public static boolean running = true;
    public static boolean dataHadChanged = false;
    
    
    public static void showOption(){
        System.out.println("\n=============================================================================");
        System.out.println("\t\t\tOPTION:");
        System.out.println("Type \"1\" to search for word in local data.");
        System.out.println("Type \"2\" to get text form voice and translate it (if you want).");
        System.out.println("Type \"3\" to translate word (or paragraph) form English to Vietnamese.");
        System.out.println("Type \"4\" to speech a word (or paragraph) in English.");
        System.out.println("Type \"5\" to get text from image (.png only).");
        System.out.println("Type \"6\" to change data base.");
        System.out.println("\n\tType \"#close#\" to close the dictionary program.\n");
    }
    
    public static void myContinue(){
        System.out.println("enter any thing to continue");
        scan.nextLine();
    }
        
    public static void processing(){
        String input = scan.nextLine();
                
        if(input.compareTo("1") == 0){
            searching();
        } else if(input.compareTo("2") == 0){
            voiceToText();
        } else if(input.compareTo("3") == 0){
            translate();
        } else if(input.compareTo("4") == 0){
            speech();
        } else if(input.compareTo("5") == 0){
            imageToText();
        } else if(input.compareTo("6") == 0){
            changeData();
        } else if(input.compareTo("#close#") == 0){
            running = false;
        } else {
            System.out.println("Input invalid, please enter your option again.");
        }
    }
    
    public static void searching(){
        System.out.println("Please enter word which you want to find: ");
        searching(scan.nextLine().trim().toLowerCase()); 
    }
    public static void searching(String word){ // type 1

        int pos = dictionary.findPosition(word);
        if(pos < dictionary.getData().size() && dictionary.getData().get(pos).compareTo(word) == 0){
            dictionary.print(pos);
            myContinue();
        } else {
            System.out.println("Can't find this word(or paragraph) in local data.\nWould you like to translate it:");
            System.out.println("Enter \"1\" for YES other for NO");
            if(scan.nextLine().compareTo("1") == 0)
                translate(word);
            
        }
    }
    
    public static void voiceToText(){ // type 2
        CaptureVoice capVoice = new CaptureVoice();
        System.out.println("We will Take your voice in 6 sec.");
        myContinue();
        capVoice.start();
        
        while(capVoice.isAlive()){}
        System.out.println("End capture");
        
        System.out.println("Please wait ...");
        
        String result = (SpeechRecognize.voiceToText(capVoice.getData()));
        
        if(result.compareTo("") != 0){
            System.out.println("The text result is :\n" + result + "\n");

            System.out.println("Would you like to find this text in local data or translate it.");
            System.out.println("\"1\" for find it in local data, \"2\" for translate it, or enter other things to continue.");
            String input = scan.nextLine();
            if(input.compareTo("1") == 0){
                searching(result);
            } else if(input.compareTo("2") == 0){
                translate(result);
            }
        }
    }
    
    public static void translate(){ // type 3
        System.out.println("Please enter text which you want to translate: ");
        translate(scan.nextLine());
    }
    public static void translate(String text){
        String result = "";
        try {
            System.out.println("Please waiting ...");
            result = Translate.execute(text, Language.ENGLISH, Language.VIETNAM);
        } catch (Exception ex) {
            System.out.println("ERROR: can't connect to the network.");
        }
        if(result.compareTo("") == 0 || result.compareTo(text) == 0)
            System.out.println("Can't translate text: \"" + text + "\" to Vietnamese.");
        else
            System.out.println(text + " :\n" +result);
        
        myContinue();
    }
    
    public static void speech(){ // type 4
        System.out.println("Please enter text which you want to speech: ");
        String input = scan.nextLine();
        TextToSpeech.speak(input);
    }
    
    public static void imageToText(){ // type 5
        System.out.println("Please enter path of image which you want to get text from: ");
        String path = scan.nextLine();
        String text = ImageToText.getText(path);
        if(!text.equals("ready")){
            System.out.println("The text in this image is: \n" + text + "\n");
            System.out.println("Would you like to translate it.");
            System.out.println("Enter \"1\" for YES other for NO");
            if(scan.nextLine().compareTo("1") == 0)
                translate(text);
        } else {
            System.out.println("Can't open this file.");
            myContinue();
        }
    }

    public static void changeData(){ // type 6
        System.out.println("Please enter word which you want to add, delete or change: ");
        String word = scan.nextLine().trim().toLowerCase(); 
        int pos = dictionary.findPosition(word);
        
        
        if(pos < dictionary.getData().size() && dictionary.getData().get(pos).compareTo(word) == 0){// word already exist 
            dictionary.print(pos);
            System.out.println("This word already exist, would you like to delete it, change it or do nothing.");
            System.out.println("\"1\" for delete it, \"2\" for change it or enter other to do nothing.");
            String input = scan.nextLine();
            
            if(input.compareTo("1") == 0){
                dictionary.getData().remove(pos);
                System.out.println("Word \"" + word + "\" have been deleted from data.");
                dataHadChanged = true;
                myContinue();
            } else if(input.compareTo("2") == 0){
                Word pWord = dictionary.getData().get(pos);
                pWord = createWordInfo(pWord);
                System.out.println("Infomation of \"" + word + "\" have been change.");
                dataHadChanged = true;
                myContinue();
            }
                
        } else { // word don't exist
            System.out.println("This word is not in the data, would you like to add this word to the dictionary.");
            System.out.println("Enter \"1\" for YES other for NO");
            String input = scan.nextLine();
            
            if(input.compareTo("1") == 0){
                Word pWord = new Word(word, "", "");
                dictionary.addWord(createWordInfo(pWord));
                System.out.println("Word \"" + word + "\" have been add to data.");
                dataHadChanged = true;
                myContinue();
            }
            
        }
    }
    
    public static Word createWordInfo(Word word){
        System.out.println("Please enter how \""+ word.getWord_target() +"\" should be procenuce(eg:pronunciation /prəˌnənsēˈāSHən/).");////
        word.setPronounce(scan.nextLine().trim());
        
        System.out.println("Please enter explanation of \""+ word.getWord_target() +"\", like \"danh từ\", \"tính từ\", etc and meaning of word.");
        System.out.println("Enter \"#stop_explain#\" to stop explaining.");
        String line;
        String explain = "";
        int  i = 0;
        if((line = scan.nextLine()).compareTo("#stop_explain#") != 0){
            explain += line;
            while((line = scan.nextLine()).compareTo("#stop_explain#") != 0 && i < 20){
                explain += "\n" + line;
                i++;
            }
        }
        
        if(i == 20)
            System.out.println("Stop explain, explaining have too many line.");
        word.setWord_explain(explain);
        return word;
    }
    public static void main(String[] args) throws Exception {
        System.out.println("\n=============================================================================");
        dictionary.readFile("./dataBase\\AnhViet1.txt");
        System.out.println("Size of local dictionary's data: "+dictionary.getData().size());
        
        while(running){
            showOption();
            processing();
        }
        
        if(dataHadChanged)//////
            dictionary.save("./dataBase\\AnhViet1.txt");
        
        System.out.println("\nThank for using this program.\n");
        
    }
}
