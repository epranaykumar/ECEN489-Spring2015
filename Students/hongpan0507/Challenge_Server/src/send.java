/**
 * Created by hpan on 1/29/15.
 */

import java.io.*;
import java.util.Scanner;

public class send extends Thread {
    private ObjectOutputStream output_stream = null;
    private String message = "";

    // constructor
    public send(ObjectOutputStream _output_stream) {
        output_stream = _output_stream;
    }

    public void run() {
        try {
            Scanner keyboard = new Scanner(System.in);
            while (message != ".disconnect") {
                message = keyboard.nextLine();
                output_stream.writeUTF(message);
                output_stream.flush();
            } // end of while
            System.out.println("The client has been disconnected");
            keyboard.close();
            output_stream.close();
        } catch (IOException e) {
            System.out.println("Error occurred when sending message" + e);
        } // end of try block
    } // end of run
}
