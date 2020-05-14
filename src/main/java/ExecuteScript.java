import java.io.FileNotFoundException;
import java.io.IOException;

public class ExecuteScript {

    public void execute(String filename) {
        try {
            Console console = new Console(filename);
            while (Console.sc.hasNextLine()){
                console.writeCommand();
            }
        } catch (FileNotFoundException e){
            System.out.println("Файл скрипта с таким именем не найден");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Скрипт в файле " + filename + " исполнен");
    }
}
