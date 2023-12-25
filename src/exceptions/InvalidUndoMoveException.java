package exceptions;

public class InvalidUndoMoveException extends Exception{
    public InvalidUndoMoveException(String message){
        super(message);
    }
}
