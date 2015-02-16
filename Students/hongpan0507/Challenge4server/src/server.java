/**
 * Created by hpan on 1/29/15.
 */

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class server {
    private ServerSocket server_sock = null;
    private Socket sock = null;
    private Socket temp_sock = null;
    private int port_num = 0;
    private int max_num = 0;

    //constructor
    public server(int _port_num, int _max_num) {
        port_num = _port_num;
        max_num = _max_num;
    }   //end of constructor

    public void start_server() {
        try {
            System.out.println("Binding to port: " + port_num);
            InetAddress address = getCurrentIp();
            server_sock = new ServerSocket(port_num, max_num);
            System.out.println("Server running: " + address.getHostAddress());
            System.out.println("Waiting for clients to connect...");
            while (true) {
                try {
                    sock = server_sock.accept();
                    if (sock != null) {
                        server_thread client_connection = new server_thread(sock);
                        System.out.println(sock.getInetAddress() + " Connected");
                        client_connection.start();
                    }
                } catch (IOException IOE) {
                    System.out.println("server socket accept() error " + IOE);
                }
            }   //end of while
        } catch (IOException IOE) {
            System.out.println(IOE);
        }
    }   //end of start_sever

    public InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {

        }
        return null;
    }
}   // end of server
