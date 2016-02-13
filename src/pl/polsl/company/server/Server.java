package pl.polsl.company.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Calendar;

/**
 *
 * @author matis
 */
public class Server {

    /**
     * Port number
     */
    private static Integer PORT = 23;

    /**
     * Singleton server object
     */
    private static ServerSocket server = null;

    /**
     * Main method
     *
     * @param args String with command line arguments
     */
    public static void main(String args[]) {
        if (args.length == 1) {
            PORT = Integer.parseInt(args[0]);
        }
        try {
            server = createServer();
            System.out.println("Server started");
            try {
                while (true) {
                    Socket socket = server.accept();
                    System.out.println("server connected to " + socket.getInetAddress());
                    try {
                        Services service = new Services(socket);
                        service.start();
                    } catch (IOException e) {
                        socket.close();
                        System.err.println(e.getMessage());
                    }
                }
            } finally {
                server.close();
            }
        } catch (IOException ex) {
            System.out.println("Server socket error" + ex.getMessage());
        }
    }

    /**
     * Method to create singleton server object - to avoid creation of multiple
     * instances
     *
     * @return ServerSocket with new Object or current Instance
     * @throws IOException when SocketServer cannont be created
     */
    public static synchronized ServerSocket createServer() throws IOException {
        if (server == null) {
            server = new ServerSocket(PORT);
            return server;
        }
        System.out.println("Server is running, don't try to create multiple instances");
        return server;
    }

    public Integer getArgs(String args[]) {
        if (args.length == 1) {
            return Integer.parseInt(args[0]);
        } else {
            return null;
        }
    }
}
