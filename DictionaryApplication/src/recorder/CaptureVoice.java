package recorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * this class capture voices and change them into byte[] data
 *
 */

public class CaptureVoice extends Thread{

    static final int MB = 262144;// 1 MB
    private static byte[] data;// to post data, it required be less than 1 MB
    public static byte[] getData() {return data;}

    private static  Thread stopper;// thread use to stop the capture
    // record duration, in milliseconds
    private static long CAPTURE_TIME = 6000;  // 6 sec

    public static long getCAPTURE_TIME() {return CAPTURE_TIME;}

    public static void setCAPTURE_TIME(long CAPTURE_TIME) {CaptureVoice.CAPTURE_TIME = CAPTURE_TIME;}

    // the line from which audio data is captured
    private static TargetDataLine line;//using SourceDataLine maybe bestter
    public static boolean alive = false;

    // Defines an audio format
    static AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;//stereo
        boolean singed = true;//nguyen / so tun nhien
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, singed, bigEndian);
    }


    public void run(){//start capture the voices and limit the the capture time
        this.alive = true;
        CaptureVoice thisThread = this;

        // creates a new thread that waits for a specified of time before stopping
        stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(CAPTURE_TIME);//limit time
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                thisThread.finish();//stop after running CAPTURE_TIME (mini sec)
            }
        });
        // start timming to stop capture
        stopper.start();

        // start recording
        this.CaptureStart();
    }

    private void CaptureStart() {//don't call this menthod if from outside this class
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format); // 1 megabyte * 4 / 8

            System.out.println("Start capturing...");
            line.start();   // start capturing

            int numBytesRead;
            byte[] byteArr = new byte[MB];
            // get voices data as byte[]
            int size = line.read(byteArr, 0, MB);
            finish();
            data = new byte[size];
            for(int i = 0; i < size; i++){
                data[i] = byteArr[i];
                this.alive = false;
            }//get data from arr to reduce size of data

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Closes the target data line to finish capturing and recording
    public static void finish() {
        //System.out.println("done");
        if(line.isOpen()){
            if(line.isActive())
                line.stop();
            line.close();
        }
        line.flush();

    }

}


