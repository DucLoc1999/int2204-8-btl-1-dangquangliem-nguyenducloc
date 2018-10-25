package recorder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class SpeechRecognize {

    /**api yandex speechkit key
     * sory although i know it bad but i stolen those key from another in github :"), please forgive me.
     *
     * 60556d09-0e84-42b7-8974-9d0b01cfee33
     * 936e9bf4-b019-494d-9cfc-931a62bc0886
     */
    private static String getUserID(){//create readom user ID
        String userID = "";
        Random rd = new Random();
        for(int i = 0; i < 32; i++){
            int a = rd.nextInt(16);
            if(a < 10){
                userID += "" + (char)((int)('0')+a);
            } else {
                userID += "" + (char)((int)('a')+a-10);
            }
        }
        return userID;
    }
    private static final String REQUEST = "https://asr.yandex.net/asr_xml?" +
            "uuid=" + getUserID() +
            "&key=" + "936e9bf4-b019-494d-9cfc-931a62bc0886" +
            "&topic=queries" +
            "&lang=en-US";

    public static String voiceToText(byte[] data) {
        String res = "";
        try {
            URL url = new URL(REQUEST);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "audio/x-pcm;bit=16;rate=16000");//audio/x-pcm;bit=16;rate=16000 || audio/x-wav
            connection.setRequestProperty("User-Agent", "BoBa");
            connection.setRequestProperty("Host", "asr.yandex.net");
            connection.setRequestProperty("Content-Length", "" + data.length);
            connection.setRequestProperty("Transfer-Encoding", "chunked");

            connection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();


            //System.out.println("Done");
            System.out.println();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            in.readLine();
            in.readLine();

            //while ((decodedString = in.readLine()) != null) {
            res = in.readLine();
            //}
            try{
                res = res.split(">")[1];
                res = res.split("<")[0];
                res = res.toLowerCase();
            } catch(Exception e) {
                System.out.println("reponse is null or have error in string convent");
                return "";
            }

            connection.disconnect();

        } catch (MalformedURLException c) {
            c.printStackTrace();
        } catch (ProtocolException p) {
            p.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        return res;

    }
}
