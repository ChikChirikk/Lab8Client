import Commands.*;
import Connection.App;
import Connection.ClientReceiver;
import Connection.ClientSender;
import Controller.Commands;
import FrameWork.AuthorizeFrame;
import Utilites.LocaleController;

import java.awt.*;
import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;
import java.nio.channels.UnresolvedAddressException;
import java.security.NoSuchAlgorithmException;


public class Main {



    public static void main(String args[]) throws InterruptedException {
        new LocaleController();
        initCommands();
        Runnable startWindow = () -> {
            new AuthorizeFrame();
        };
        new Thread(startWindow).start();
        App.startProgramm();
        System.out.println(Color.red.getRGB());
    }

    public static void initCommands() {
        Commands commands = new Commands();
        commands.regist(new Help(), new Show(), new Remove_by_id(), new Remove_first(), new Remove_lower(), new Add(),
                new Exit(), new Clear(), new Info(), new Update(), new Count_less_than_impact_speed(),
                new Print_ascending(), new Execute_script());
    }


}

//TODO
//хьюман фрэйм чек бокс
//exit
//обновление коллекции
//раскоментить сохранение коллекции при закрытии jframe