package exceptions;

public class InvalidGameMoveException extends Exception{
    public InvalidGameMoveException(String message){
        super(message);
    }
}
