package Connection;

import Controller.HumanCollection;
import FrameWork.AuthorizeFrame;
import FrameWork.MainFrame;
import Utilites.Console;

import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.UnresolvedAddressException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;

public class App {
    private static ClientReceiver receiver;
    private static ClientSender sender;
    boolean was = false;

    public HashMap loginAndPassword = null;
    private Console console = new Console();

    public App(ClientSender sender, ClientReceiver receiver) throws NoSuchAlgorithmException, IOException {
        this.receiver = receiver;
        this.sender = sender;
        System.out.println("Работа приложения запущена.");
        MainFrame.app = this;
        // sender.send("-1");
    }


    public static ClientReceiver getReceiver() {
        return receiver;
    }

    public static ClientSender getSender() {
        return sender;
    }

    public void begin() throws IOException {
        try {
            String ans = "notValid";
            if (loginAndPassword == null) {
                while (ans.equals("notValid")) {
                    loginAndPassword = console.register();
                    loginAndPassword.put("port", String.valueOf(sender.getClientPort()));
                    sender.send("-1");
                    sender.send(loginAndPassword);
                    ans = receiver.receive();
                    if (ans.equals("notValid"))
                        if (loginAndPassword.get("reg").equals("log")) {
                            AuthorizeFrame.messageFail = ("Проверьте логин или пароль.");
                            receiver.receive();
                        } else {
                            AuthorizeFrame.messageFail = ("Пользователь с таким логином уже зарегистрирован.");
                            receiver.receive();
                        }
                }
                new HumanCollection().setArray(receiver.receiveCollection());
                HumanCollection.setDateCreation((LocalDate) receiver.receiveObject());
                AuthorizeFrame.validLogAndPassword = true;
                if (loginAndPassword.get("reg").equals("log"))
                    AuthorizeFrame.messageFail = ("Пользователь успешно авторизирован.");
                else System.out.println("Пользователь успешно зарегистрирован.");
                String received = receiver.receive();
                if (received != null) {
                    System.out.print(received);
                }
                was = true;
                sender.setPortLoginAndPassword(loginAndPassword);
            } else {
                sender.send("-1");
                sender.send(loginAndPassword);
            }
        } catch (SocketTimeoutException | NoSuchAlgorithmException | InterruptedException | ClassNotFoundException e) {
            loginAndPassword = null;
            System.out.println("Сервер недоступен =(");
            this.begin();
            was = false;
        }
    }

    private static int serverPort = 17832;
    private static int clientPort;
    private static String host;

    public static void startProgramm() throws InterruptedException {
        try {

            while (!AuthorizeFrame.isPressedOk()) {
                Thread.currentThread().sleep(10);
            }
            clientPort = AuthorizeFrame.getPort();
            host = AuthorizeFrame.getHost();
            ClientSender sender = new ClientSender(host, serverPort, clientPort);
            ClientReceiver receiver = new ClientReceiver(clientPort);
            if (sender.isValidConnection()) AuthorizeFrame.validPortAndHost = true;
            App app = new App(sender, receiver);
            app.begin();
            //app.run();
        } catch (BindException e) {
            if (clientPort > 65536) AuthorizeFrame.messageFail = ("Этот порт слишком большой");
            else AuthorizeFrame.messageFail = "Невозможно подключиться по указанному адресу";
            Thread.currentThread().sleep(1000);
            startProgramm();
        } catch (SocketException | UnresolvedAddressException e) {
            AuthorizeFrame.messageFail = "Невозможно подключиться к указанному хосту";
            Thread.currentThread().sleep(1000);
            startProgramm();
        } catch (NoSuchAlgorithmException | IOException e) {
            AuthorizeFrame.messageFail = "Невозможно подключиться по указанному адресу";
            e.printStackTrace();
        }
    }

}


