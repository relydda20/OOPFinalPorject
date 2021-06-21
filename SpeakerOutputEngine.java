package JotDownAudio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SpeakerOutputEngine extends AudioSettings{

    private AudioController audio_c;
    private SourceDataLine speakerLine;
    private AudioFormat speakerFormat;

    // SpeakerOutputEngine constructor which allows access of this class and others through one java class
    public SpeakerOutputEngine(AudioController audio_c) {
        this.audio_c = audio_c;

        // Try/ Catch statement to open up the user's SourceDataLine
        try {
            // Specified audio format from the AudioSettings class
            speakerFormat = new AudioFormat(getSampleRate(), getSampleSizeInBits(), getChannels(), isSignData(), isBigEndian());

            // Receives the format and appends to the SourceDataLine
            speakerLine = AudioSystem.getSourceDataLine(speakerFormat);

            // Opens the SourceDataLine based on the format and the buffer size specified in AudioSettings class
            speakerLine.open(speakerFormat, getBufferSize());
            System.out.println("Speaker Line Opened.");

        } catch (LineUnavailableException e) {
            System.out.println("Speaker Line Unavailable!");
        }
    }

    // Method to start and allow the user to hear the audio input
    public void startLine() {
        speakerLine.start();
        System.out.println("Speakers Started.");
    }


    // Method to stop the user from hearing their audio input by emptying out the speaker buffer and then stopping it
    public void stopLine() {
        speakerLine.drain();
        speakerLine.stop();
        System.out.println("Speakers Closed.");
    }

    // getter to allow other classes to access the methods in this class
    public SourceDataLine getSpeakerLine() {
        return speakerLine;
    }

}