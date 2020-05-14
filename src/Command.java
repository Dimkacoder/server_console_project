import java.io.Serializable;

public abstract class Command implements Serializable {
    public abstract boolean checkCom(String[] s);
    public Command() {
    }
}
