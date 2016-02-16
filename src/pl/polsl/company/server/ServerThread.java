package pl.polsl.company.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import pl.polsl.company.controller.AuthenticationController;
import pl.polsl.company.controller.UnauthorizedAccessException;
import pl.polsl.company.model.AuthorizableTransaction;
import pl.polsl.company.model.RoomRentTransaction;
import pl.polsl.database.entities.Seances;
import pl.polsl.database.manager.PrivilegeLevels;

/**
 * Server thread to communicate between single client and server.
 *
 * @author Wojciech Dębski
 * @version 1.0
 */
public class ServerThread implements Runnable {

    /**
     * Incoming client socket.
     */
    private final Socket incomingSocket;

    /**
     * DataOutputStream to client.
     */
    DataOutputStream outStreamToClient;

    /**
     * BufferedReader from client.
     */
    BufferedReader inFromClient;

    /**
     * Client ID.
     */
    private final int clientID;

    /**
     * Controller
     */
    private AuthenticationController authController;

    /**
     * It creates server thread.
     *
     * @param incomingSocket incoming socket
     * @param clientID client ID
     */
    public ServerThread(Socket incomingSocket, Integer clientID) {
        this.clientID = clientID;
        this.incomingSocket = incomingSocket;
        authController = new AuthenticationController();
    }

    /**
     * Main loop which reacts on client commands.
     */
    @Override
    public void run() {
        try {
            try {
                outStreamToClient = new DataOutputStream(incomingSocket.getOutputStream());
                inFromClient = new BufferedReader(new InputStreamReader(incomingSocket.getInputStream()));

                boolean done = false;
                System.out.println("Server thread started.");
                while (!done) {
                    String command = inFromClient.readLine();

                    System.out.println("Input command : " + command);

                    if (!reactOnCommand(command)) {
                        outStreamToClient.writeBytes("ERROR" + System.getProperty("line.separator"));
                    }
                    if (command.equals("exit")) {
                        done = true;
                    }
                }
                System.out.println("Client closing " + clientID);
            } catch (IOException exception) {
                System.out.println("Connection error.");
            } finally {
                incomingSocket.close();
                System.out.println("Server thread exited.");
            }
        } catch (IOException exception) {
            System.out.println("Connection error.");
        }

    }

    /**
     * It causes correct method connected with command.
     *
     * @param command command content
     * @return true if gets correct command, otherwise returns false
     */
    private synchronized boolean reactOnCommand(String command) {
        switch (command) {
            case "login":
                login();
                break;
            case "gettransactions":
                getTransactions();
                break;
            case "accepttransaction":
                acceptTransaction();
                break;
            case "refusetransaction":
                refuseTransaction();
                break;
            case "getroomsoccupancy":
                getRoomsOccupancy();
                break;
            case "sendtransaction":
                sendTransaction();
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println("Incorrect command");
                return false;
        }
        return true;
    }

    /**
     * Performs actions connected with login command.
     */
    private void login() {
        confirm();
        System.out.println("I acknowledge receipt of login command.");
        try {
            String userName = inFromClient.readLine();
            if (userName != null) {
                confirm();
            } else {
                refuse();
            }

            String password = inFromClient.readLine();
            if (password != null) {
                confirm();
            } else {
                refuse();
            }

            PrivilegeLevels userLevel = authController.authenticateUser(userName, password);
            if (userLevel != null) {
                operationDone();
            } else {
                operationNotDone();
                return;
            }

            if (userLevel == PrivilegeLevels.CHIEF) {
                sendResponse("CHIEF");
            } else if (userLevel == PrivilegeLevels.CONSULTANT) {
                sendResponse("CONSULTANT");
            }

        } catch (IOException exception) {
            System.out.println("Data transfer error.");
        } catch (UnauthorizedAccessException ex) {
            System.out.println("Wrong username or password.");
            operationNotDone();
        }
    }

    /**
     * Performs actions connected with exit command.
     */
    private void exit() {
        confirm();
        System.out.println("I acknowledge receipt of exit command.");
    }

    /**
     * Performs actions connected with gettransactions command.
     */
    private void getTransactions() {
        confirm();
        System.out.println("I acknowledge receipt of gettransactions command.");
        try {
            List<AuthorizableTransaction> transactions = authController.getManagementController().getUnauthorizedTransactions();
            if (transactions == null) {
                operationNotDone();
            }
            operationDone();

            sendResponse(Integer.toString(transactions.size()));

            for (AuthorizableTransaction transaction : transactions) {
                //KONIECZNIE NALEŻY PRZEŁADOWAĆ toString() Format: 
                //{"ID;Nazwa firmy;Numer sali;Godzina;Data;Cena"}
                if (!sendResponse(transaction.toString())) {
                    return;
                }
            }
        } catch (UnauthorizedAccessException exception) {
            System.out.println("Unauthorised access.");
            operationNotDone();
        }
    }

    /**
     * Performs actions connected with accepttransaction command.
     */
    private void acceptTransaction() {
        confirm();
        System.out.println("I acknowledge receipt of accepttransaction command.");
        try {
            String id = inFromClient.readLine();
            confirm();
            authController.getManagementController().acceptTransaction(Integer.parseInt(id));
            operationDone();

        } catch (UnauthorizedAccessException exception) {
            System.out.println("Unauthorised access.");
            operationNotDone();
        } catch (IOException exception) {
            System.out.println("Data transfer error.");
        }
    }

    /**
     * Performs actions connected with refusetransaction command.
     */
    private void refuseTransaction() {
        confirm();
        System.out.println("I acknowledge receipt of refusetransaction command.");
        try {
            String id = inFromClient.readLine();
            confirm();
            authController.getManagementController().refuseTransaction(Integer.parseInt(id));
            operationDone();

        } catch (UnauthorizedAccessException exception) {
            System.out.println("Unauthorised access.");
            operationNotDone();
        } catch (IOException exception) {
            System.out.println("Data transfer error.");
        }
    }

    /**
     * Performs actions connected with getroomsoccupancy command.
     */
    private void getRoomsOccupancy() {
        confirm();
        System.out.println("I acknowledge receipt of getroomsoccupancy command.");
        try {
            // TODO: syf z polami
            List<Seances> allSeances = authController.getBusinessServiceController().getAllSeances();
            if (allSeances == null) {
                operationNotDone();
            }
            List<RoomRentTransaction> allRoomRentTransactions = authController.getBusinessServiceController().getAllRoomRentTransactions();
            if (allRoomRentTransactions == null) {
                operationNotDone();
            }
            operationDone();

            sendResponse(Integer.toString(allSeances.size() + allRoomRentTransactions.size()));

            for (Seances seances : allSeances) {
                //!!!!!!!! TODO KONIECZNIE NALEŻY PRZEŁADOWAĆ toString() Format: WTF? nic sienie zgadza
                //{"Film;Numer Sali;Data rozpoczęcia;Data zakończenia;Zarezerwowano"}
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");

                if (!sendResponse(seances.getFilm().getTitle() + ";"
                        + seances.getRoom().getId() + ";"
                        + sdf.format(seances.getDate().getTime()) + ";"
                        + sdf.format(seances.getDate().getTime())
                        + ";brak")) {
                    return;
                }
            }
            for (RoomRentTransaction roomRentTransaction : allRoomRentTransactions) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");

                if (!sendResponse(roomRentTransaction.getID() + ";"
                        + roomRentTransaction.getRoomNumber() + ";"
                        + sdf.format(roomRentTransaction.getStartDate().getTime()) + ";"
                        + sdf.format(roomRentTransaction.getEndDate().getTime())
                        + ";brak")) {
                    return;
                }
            }
        } catch (UnauthorizedAccessException exception) {
            System.out.println("Unauthorised access.");
            operationNotDone();
        }
    }

    /**
     * Performs actions connected with sendtransaction command.
     */
    private void sendTransaction() {
        confirm();
        System.out.println("I acknowledge receipt of sendtransaction command.");

        try {
            String startDate = inFromClient.readLine();
            if (startDate != null) {
                confirm();
            } else {
                refuse();
            }

            String endDate = inFromClient.readLine();
            if (endDate != null) {
                confirm();
            } else {
                refuse();
            }

            String price = inFromClient.readLine();
            if (price != null) {
                confirm();
            } else {
                refuse();
            }

            String companyName = inFromClient.readLine();
            if (companyName != null) {
                confirm();
            } else {
                refuse();
            }
            String roomNumber = inFromClient.readLine();
            if (roomNumber != null) {
                confirm();
            } else {
                refuse();
            }
            String type = inFromClient.readLine();
            if (type != null) {
                confirm();
            } else {
                refuse();
            }

            // TODO rozjazd argumentów względem GUI
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm");
                Calendar startDateC = Calendar.getInstance();
                startDateC.setTime(sdf.parse(startDate));
                Calendar endDateC = Calendar.getInstance();
                endDateC.setTime(sdf.parse(endDate));
                Double priceD = Double.parseDouble(price);
                Integer roomNumberI = Integer.parseInt(roomNumber);
                Integer typeI = Integer.parseInt(type);
                authController.getBusinessServiceController().createNewRoomRentTransaction(
                        startDateC, endDateC, priceD, companyName, roomNumberI, typeI);
                operationDone();
            } catch (NumberFormatException | UnauthorizedAccessException | ParseException exception) {
                operationNotDone();
            }
        } catch (IOException exception) {
            return;
        }
    }

    private boolean confirm() {
        try {
            outStreamToClient.writeBytes("OK" + System.getProperty("line.separator"));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    private boolean refuse() {
        try {
            outStreamToClient.writeBytes("NOK" + System.getProperty("line.separator"));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    private boolean operationDone() {
        try {
            outStreamToClient.writeBytes("DONE" + System.getProperty("line.separator"));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    private boolean operationNotDone() {
        try {
            outStreamToClient.writeBytes("NDONE" + System.getProperty("line.separator"));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    private boolean sendResponse(String response) {
        try {
            outStreamToClient.writeBytes(response + System.getProperty("line.separator"));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }
}
