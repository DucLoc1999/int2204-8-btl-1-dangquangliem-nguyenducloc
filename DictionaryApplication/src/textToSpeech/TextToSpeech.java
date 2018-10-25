package textToSpeech;

import com.sun.speech.freetts.*;


/**
 *using freeTTS
 *voice: kevin16
 */

public class TextToSpeech {
    static private final String  voiceName = "kevin16";
    static public void speak(String text){
        try {
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            voice.allocate();//ready to speak: creates an audio output.
            voice.speak(text);
            voice.deallocate();//Shuts down the voice processing.
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }

}