package pl.polsl.company.server_OLD;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.OperationHandler;

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
            EntityManager em = DAOManager.getInstance("kino").getEntityManager();
            OperationHandler handler = new OperationHandler(em);
            ArrayList<IEntity> lis = (ArrayList<IEntity>) handler.handleRequest("FILMS", "CREATE_ENTITY", "MILCZENIE", "1:20:30");
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
        } catch (ArgsLengthNotCorrectException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArgsNotCorrectException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
