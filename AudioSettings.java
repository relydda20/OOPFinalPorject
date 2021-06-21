package JotDownAudio;

public class AudioSettings {

    // Default values for a common WAV file and required values for SourceDataLine and
    // TargetDataLine parameters.

    // The amount of samples per second measured in khz
    public float sampleRate = 44100.0f;
    // The amount bits of data per audio sample
    public int SampleSizeInBits = 16;
    // The amount of audio channels
    public int channels = 1;
    // Determines how the data will be stored (signed means that the data can be expressed in negatives and positives
    // whereas if it isn't signed then the values will be stored from 0 and above)
    public boolean signData = true;
    // Determines if the audio samples will be stored in big endian or small endian
    public boolean bigEndian = false;
    // The amount of time it takes for the computer to process incoming audio
    // Set to the default value of buffer size
    public int bufferSize = 4096;

    public float getSampleRate() {
        return sampleRate;
    }

    public int getSampleSizeInBits() {
        return SampleSizeInBits;
    }

    public int getChannels() {
        return channels;
    }

    public boolean isSignData() {
        return signData;
    }

    public boolean isBigEndian() {
        return bigEndian;
    }

    public int getBufferSize() {
        return bufferSize;
    }

}
