package pl.polsl.company.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Main class of shop server. It monitors client connections and creates threads
 * for them.
 *
 * @author Wojciech Dębski
 * @version 1.0
 */
public class Server {

    /**
     * Connected clients counter.
     */
    static int i = 0;

    /**
     * Monitors client connections and creates threads for them.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(6789);
            Thread t = null;
            ArrayList<Thread> threads = new ArrayList<>();
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ServerThread(incoming, i);
                t = new Thread(r);
                t.start();
                threads.add(t);
                i++;
                for (Thread thread : threads) {
                    if (!thread.isAlive()) {
                        i--;
                    }
                    threads.remove(thread);
                }
            }

        } catch (IOException e) {
            System.out.println("Błąd połączenia.");
        }
    }
}
