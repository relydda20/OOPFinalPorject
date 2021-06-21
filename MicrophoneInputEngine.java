package JotDownAudio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneInputEngine extends AudioSettings implements Runnable {

    private AudioController audio_c;
    private TargetDataLine microphoneLine;
    private AudioFormat microphoneFormat;

    public int readBytes;

    // MicrophoneInputEngine constructor which allows access of this class and others through one java class
    public MicrophoneInputEngine(AudioController audio_c) {
        this.audio_c = audio_c;

        // Try/ Catch statement to open up the user's TargetDataLine
        try {
            // Specified audio format from the AudioSettings class
            microphoneFormat = new AudioFormat(getSampleRate(), getSampleSizeInBits(), getChannels(), isSignData(), isBigEndian());

            // Receives the format and appends to the TargetDataLine
            microphoneLine = AudioSystem.getTargetDataLine(microphoneFormat);

            // Opens the TargetDataLine based on the format and the buffer size specified in AudioSettings class
            microphoneLine.open(microphoneFormat, getBufferSize());
            System.out.println("Microphone Line Opened.");

        } catch (LineUnavailableException e) {
            System.out.println("Microphone Line Unavailable!");
        }
    }

    // A Thread that functions to continuously receive audio samples from user
    @Override
    public void run() {

        // Creates a new Byte array with the buffer size specified from AudioSettings class
        // Byte array is used because it is the required parameter for a SourceDataLine to write audio into the mixer
        byte[] data = new byte[getBufferSize()];

        // A while loop is used to be able to read the audio input buffer and then write it to the user's mixer
        while(true) {
            // Read bytes from the microphone buffer
            readBytes = microphoneLine.read(data, 0, data.length);
            audio_c.getSpeakerOutputEngine().getSpeakerLine().write(data, 0, readBytes);

        }
    }

    // Method to start the microphone line and allows to receive audio samples
    public void startLine() {
        microphoneLine.start();
        System.out.println("Microphone Started.");
    }

    // Method to stop the microphone line from inputting audio samples into the buffer
    public void stopLine() {
        microphoneLine.stop();
        System.out.println("Microphone Closed.");
    }

    // Getter to allow other classes to access the methods in this class
    public TargetDataLine getMicrophoneLine() {
        return microphoneLine;
    }

}