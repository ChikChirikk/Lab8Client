package Utilites;

import FrameWork.AuthorizeFrame;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class Console {
    static Scanner in = new Scanner(System.in);

    public HashMap register() throws NoSuchAlgorithmException, InterruptedException {
        boolean pressedLog;
        while (!((pressedLog = AuthorizeFrame.isPressedLog()) || (AuthorizeFrame.isPressedSign()))) {
            Thread.currentThread().sleep(10);
        }
        AuthorizeFrame.pressedLog = false;
        AuthorizeFrame.pressedSign = false;
        HashMap loginAndPassword = this.getLoginAndPassword();
        if (pressedLog) loginAndPassword.put("reg", "log");
        else loginAndPassword.put("reg", "sign");
        return loginAndPassword;
    }


    public HashMap getLoginAndPassword(){
        HashMap loginAndPassword = new HashMap();
        loginAndPassword.put("login", AuthorizeFrame.getLogin());
        loginAndPassword.put("password", AuthorizeFrame.getPassword());
        return loginAndPassword;
    }
}

