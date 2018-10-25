package TranslatorAPI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * this class using TESSERACT program and we have to run it with cmd so
 * be sure that all file path are correct and not similar to other file or forder
 *
 */

public class ImageToText {

    //path of tesseract.exe in Tesseract-OCR forder where you install it <without .exe>
    private static String TESSERACT_PATH = "C:\\Tesseract-OCR\\tesseract";

    //be sure in you c:\ don't have this text before or you can chage it name
    private static String OUT_FILE_PATH;

    private static void setOUT_FILE_PATH() {
        String[] Sarr = TESSERACT_PATH.split("tesseract");
        String preS = "";
        for(String s : Sarr)
            preS += s;

        OUT_FILE_PATH = preS + "textResultFromImage";//a name
    }// creat a file path to Tesseract-OCR foder

    private static void readyFile(String filePath){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath+".txt"));
            bw.write("ready");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private static String readFile(String filePath) {
        BufferedReader br = null;
        String result ="";
        try {
            String preLine;
            br = new BufferedReader(new FileReader(filePath));
            while ((preLine = br.readLine()) != null) {
                result += preLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }

    public static String getText(String imagePath) {
        if(OUT_FILE_PATH == null || OUT_FILE_PATH.equals(""))
            setOUT_FILE_PATH();
        System.out.println("the result will be print to text: "+OUT_FILE_PATH+".txt");// output file path
        readyFile(OUT_FILE_PATH);

        Process p;
        try {
            System.out.println("\n###process start:");
            p = Runtime.getRuntime().exec("cmd");
            //print error
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            //print input things
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            //print to cmd
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("\""+TESSERACT_PATH+"\" \""+imagePath+"\" \""+OUT_FILE_PATH+"\" -l eng");// the command in cmd
            stdin.close();//close input and error stream before wait
            p.waitFor();//wait the process
            System.out.println("\n###process end.\n");
            String result = readFile(OUT_FILE_PATH+".txt");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}// end of class ImageToText


class SyncPipe implements Runnable{
    private final OutputStream ostrm_;
    private final InputStream istrm_;

    public SyncPipe(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }
    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = istrm_.read(buffer)) != -1; ){
                ostrm_.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//end of class SyncPipe