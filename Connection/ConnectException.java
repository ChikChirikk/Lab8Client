package Connection;

import java.sql.Connection;

public class ConnectException extends Exception{
    private String message = "";
    public ConnectException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
