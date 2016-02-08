package pl.polsl.company.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import pl.polsl.database.menager.DAOMenager;

public class Services extends Thread {

    /**
     * socket representing connection to the client
     */
    private final Socket socket;
    /**
     * buffered input character stream
     */
    private final BufferedReader in;
    /**
     * Formatted output character stream
     */
    private final PrintWriter out;
    
    private DAOMenager daom;

    public Services(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),Charset.defaultCharset())), true);
        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(), Charset.defaultCharset()));
    }
    
    public void setDAOMenager(DAOMenager daom){
        this.daom = daom;
    }
    
    public DAOMenager getDAOMenager(){
        return daom;
    }

    /**
     * Method contains messaging loop to comunincate with client
     */
    @Override
    public void run() {
        try {
            String str;
            boolean userAccessFlag = authentication();
            while (((str = in.readLine()) != null) && userAccessFlag) {
                // operation.handleRequest(str, socket, control);
                System.out.println(str);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Method to send something to client
     *
     * @param message String containing message command to client
     */
    public void sendMessageToClient(String message) throws IOException {
        Integer timeoutCounter = 0;
        while (!in.readLine().equals("ACK") && timeoutCounter < 1000) {
            out.println(message);
            timeoutCounter++;
        }
        if(timeoutCounter >= 1000){
            throw new IOException();
        }
    }

    /**
     * Method to recieve answer from client
     *
     * @return String with client answer or inform client that there is an error
     */
    public String reciveAnswer() throws IOException {
        String str = null;
        try {
            Integer connectionCounter = 0;
            while (!in.ready() && connectionCounter < 1000) {
                connectionCounter++;
            }
            if (connectionCounter >= 1000) {
                sendMessageToClient("connection timeout");
                return null;
            } else {
                str = in.readLine();
                sendMessageToClient("ACK");
            }
        } catch (IOException ex) {
            sendMessageToClient("Wrong answer " + ex.getMessage());
        }
        return str;
    }
    
     public boolean authentication() throws IOException {
        sendMessageToClient("USER_REQ");
        String user = reciveAnswer();
        if (user != null) {
            sendMessageToClient("PASS_REQ");
            String pass = reciveAnswer();
            if (pass != null) {
                setDAOMenager(DAOMenager.getInstance("kino"));
                if(daom.authentificateUser(user, pass)){
                    sendMessageToClient("ACCESS");
                    return true;
                } else {
                    sendMessageToClient("ACCESS_DENIED");
                    return false;
                }
            } else {
                sendMessageToClient("ERROR");
                sendMessageToClient("NULL_PASS");
                return false;
            }
        } else {
            sendMessageToClient("ERROR");
            sendMessageToClient("NULL_USER");
            return false;
        }
    }

}
