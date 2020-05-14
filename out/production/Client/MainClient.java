import java.io.IOException;
import java.util.NoSuchElementException;

public class MainClient {
    public static void main(String[] args)  {
        System.out.println("Клиентское приложение запущено");
        Console console = new Console();
        try{
            while (true){
                console.writeCommand();
            }
        } catch (NoSuchElementException e){
            System.out.println("Выхожу из программы");
            System.exit(0);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
