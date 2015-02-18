import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Class that manages a client when connected to the server. Socket gets passed from ServerSetup to ClientManager constructor
public class ClientManager implements Runnable
{
    private Socket socket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private DBManager db;

    static int itemsReceived;

    //Constructor when client connects
    ClientManager (Socket socket, DBManager db)
    {
        this.socket = socket;
        this.db = db;
    }

    public void run()
    {
        try
        {
            //Create reader and writer objects
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toClient = new PrintWriter(socket.getOutputStream(), true);

            //send table ready message to client
            while (!db.tableExists()) {
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

            //send ready message to client after table is created
            String send = "Ready";
            System.out.println("Ready to receive from client...");
            toClient.println(send);
            String received = null;
            itemsReceived = 0;

            //begin receiving data
            while (socket.isConnected())
            {
                received = fromClient.readLine();
                //once statement received from client, send to database
                if (received.equals("Done") && itemsReceived == 0) {
                    System.out.println("No new data recorded!");
                    break;
                }

                if (received.equals("Done")) {
                    System.out.println("Finished receiving data! Recorded " + itemsReceived + " new entries.");
                    itemsReceived = 0;
                    break;
                }

                else if (received != null)
                    System.out.println(received);
                    try {
                        db.insertData(received);
                    }
                    catch (Exception e) {
                        System.out.println("exception thrown");
                    }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                fromClient.close();
                toClient.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
