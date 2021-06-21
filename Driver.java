package JotDownAudio;

import java.util.Scanner;

public class Driver {
    // method for the menu for the users to navigate
    public static int menu() {

        int selection = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("||      Welcome to Jot Down Audio!      ||");
        System.out.println("||Audio Monitoring and Recording Program||");
        System.out.println("Enter an integer to navigate");
        System.out.println("1. Stream audio input");
        System.out.println("2. Stop audio stream");
        System.out.println("3. Record while monitoring audio");
        System.out.println("4. Record without monitoring audio");
        System.out.println("5. Stop recording audio");
        System.out.println("6. Exit program");

        // Try statement to make sure that the user inputs an integer
        try{
            int selection2;
            selection2 = sc.nextInt();
            selection = selection2;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer");
        }

        return selection;
    }

    public static void main(String[] args) {

        AudioController audioHub = new AudioController();
        int userChoice;


        // do while loop for the menu
        do {
            // Have the menu() method inside the do while loop and have switch cases for the integer the user inputs
            userChoice = menu();
            switch (userChoice) {
                case 1:
                    // Checks if the user has the mic engine thread on or not to ensure they can't continuously
                    // have multiple threads open
                    if (audioHub.mic_engine_thread == null) {
                        audioHub.startMonitor();
                    } else {
                        System.out.println("Please turn off the stream first!");
                    }
                    break;

                case 2:
                    // Checks if the user has turned on the monitor stream or not to be able to use this option
                    try {
                        audioHub.stopMonitor();
                        audioHub.mic_engine_thread = null;
                        break;
                    } catch (Exception e) {
                        System.out.println("Please turn on the stream first!");
                        break;
                    }

                case 3:
                    // If statement to check if the recording thread is on or not to make sure they dont create
                    // multiple recordings at once
                    if (audioHub.recording_engine_thread == null) {
                        try {
                            System.out.println("Enter the name of the file: ");
                            // uses the method in the soundRecorder class to get the file name
                            audioHub.getSoundRecorder().fileName();
                            // Set the file name
                            String filename = audioHub.getSoundRecorder().getFileName();
                            // Alter the sample rate of the input and output
                            audioHub.startRecording();
                            System.out.println("recording started\n");

                            break;
                        } catch (Exception e) {
                            System.out.println("Unable to record");
                            break;
                        }
                    } else {
                        System.out.println("Audio recording in progress!\n");
                        break;
                    }

                case 4:
                    if (audioHub.recording_engine_thread == null) {
                        try {
                            System.out.println("Enter the name of the file: ");
                            // uses the method in the soundRecorder class to get the file name
                            audioHub.getSoundRecorder().fileName();
                            // Set the file name
                            String filename = audioHub.getSoundRecorder().getFileName();
                            // Alter the sample rate of the input and output
                            audioHub.startRecordNoMonitor();
                            System.out.println("recording started\n");

                            break;
                        } catch (Exception e) {
                            System.out.println("Unable to record");
                            break;
                        }
                    } else {
                        System.out.println("Audio recording in progress!\n");
                        break;
                    }

                case 5:
                    try {
                        audioHub.stopRecording();
                        audioHub.recording_engine_thread = null;
                        audioHub.mic_engine_thread = null;
                        break;
                    } catch (Exception e) {
                        System.out.println("Audio recording isn't in progress!\n");
                        break;
                    }
                case 6:
                    System.exit(0);

                default:
                    System.out.println("Enter enter a correct integer\n");
            }

        } while (userChoice != 6);
    }
}


