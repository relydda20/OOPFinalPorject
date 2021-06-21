package JotDownAudio;

public class AudioController {

    // Declaring each of the classes
    private MicrophoneInputEngine mic_input_engine;
    private SpeakerOutputEngine speaker_output_engine;
    public Thread mic_engine_thread;
    private SoundRecorder recording_engine;
    public Thread recording_engine_thread;

    // Initializing to use all of the classes specified
    public AudioController() {
        System.out.println("Opening Audio Interfaces.");
        mic_input_engine = new MicrophoneInputEngine(this);
        speaker_output_engine = new SpeakerOutputEngine(this);
        recording_engine = new SoundRecorder(this);
    }

    // Starts a monitoring stream using the startLine methods of the TargetDataLine and SourceDataLine
    public void startMonitor() {
        mic_input_engine.startLine();
        speaker_output_engine.startLine();

        // Creates a new thread to continuously receive new audio samples to the input buffer
        mic_engine_thread = new Thread(mic_input_engine);
        mic_engine_thread.start();

    }


    // Stops the monitoring stream using the stopLine methods of the TargetDataLine and SourceDataLine
    public void stopMonitor() {
        mic_input_engine.stopLine();
        speaker_output_engine.stopLine();

        // To terminate the microphone thread
        // Tried to use interrupt but the stream would still continue
        mic_engine_thread.stop();

    }

    // Starts recording with the user being able to monitor the audio
    public void startRecording() {
        mic_input_engine.startLine();
        speaker_output_engine.startLine();
        mic_engine_thread = new Thread(mic_input_engine);

        // Create a new thread for appending the audio input buffers to the WAV file
        recording_engine_thread = new Thread(recording_engine);
        mic_engine_thread.start();
        recording_engine_thread.start();

    }

    // Stops recording and turns off all threads and DataLines
    public void stopRecording() {
        recording_engine.stopRecording();
        speaker_output_engine.stopLine();

        mic_engine_thread.stop();
        recording_engine_thread.stop();

    }

    // Similar to the startRecording method but without starting the SourceDataLine
    public void startRecordNoMonitor() {
        mic_input_engine.startLine();
        mic_engine_thread = new Thread(mic_input_engine);

        recording_engine_thread = new Thread(recording_engine);
        mic_engine_thread.start();
        recording_engine_thread.start();
    }

    // Getter to allow access from class to class
    public SpeakerOutputEngine getSpeakerOutputEngine() {
        return speaker_output_engine;
    }

    public MicrophoneInputEngine getMicrophoneInputEngine() {
        return mic_input_engine;
    }

    public SoundRecorder getSoundRecorder() {
        return recording_engine;
    }

}