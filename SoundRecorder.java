package JotDownAudio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static JotDownAudio.Driver.menu;

public class SoundRecorder extends AudioSettings implements Runnable {

    private AudioController audio_c;
    private AudioFormat recording_format;
    private String filename;
    private Scanner nScan = new Scanner(System.in);

    // SoundRecorder constructor which allows access of this class and others through one java class
    public SoundRecorder(AudioController audio_c) {
        this.audio_c = audio_c;
    }

    // Receives the user's input from a scanner and applies it to the getter for the filename
    public void fileName() {
        filename = nScan.nextLine();
    }

    // A Thread that functions to record audio based on user specified input of file name and default parameters of a WAV file
    public void run() {
        try {
            // Receives the audio format from AudioSettings class
            recording_format = new AudioFormat( getSampleRate(), getSampleSizeInBits(), getChannels(), isSignData(), isBigEndian());

            // Creates a new file to allow the user to write their audio input into
            File wavFile = new File( getFileName() + ".wav");

            // Specify the audio file to be a WAV file
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

            // Creates a new AudioInputStream to be used in appending the audio samples from the input line in the
            // MicrophoneInputEngine class
            AudioInputStream ais = new AudioInputStream(audio_c.getMicrophoneInputEngine().getMicrophoneLine());

            // Starts recording
            AudioSystem.write(ais, fileType, wavFile);

            // Catches an exception if the user inputs an invalid character
        } catch (IOException e) {
            audio_c.stopRecording();
            System.out.println("A filename cannot contain any of the following characters: \\ / : * ? \" < > | ");
            menu();
        }
    }


    // Stops the recording by invoking the stop line from the controller to the MicrophoneInputEngine class
    void stopRecording() {
        audio_c.getMicrophoneInputEngine().getMicrophoneLine().stop();
        System.out.println("Finished recording");
    }

    public String getFileName() {
        return filename;
    }

}
