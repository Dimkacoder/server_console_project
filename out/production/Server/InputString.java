import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class InputString extends Thread {
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String s = scanner.nextLine();
            SaveCommand saveCommand = new SaveCommand();
            switch (s){
                case "save":
                    System.out.println("Сохраняю");
                    MainServer.LOGGER.log(Level.INFO, "Сохранение текущей коллекции");
                    saveCommand.execute();
                    break;
                case "exit":
                    System.out.println("Выхожу с сохранением");
                    MainServer.LOGGER.log(Level.OFF, "Завершение с последующим сохранением коллекции");
                    saveCommand.execute();
                    try {
                        GetAndSendMessage.server.close();
                    } catch (IOException e) {
                        System.out.println("Выход");
                    }
                    System.exit(0);
                    break;
            }
        }
    }
}
