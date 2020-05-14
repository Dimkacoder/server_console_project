import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainServer {
    public static String pathname;
    public static HashMap<String, String> commandList = new HashMap();
    public static LinkedHashMap<Integer, Flat> flats = new LinkedHashMap<>();
    public static Logger LOGGER;

    public static void main(String[] args) {
        try(FileInputStream ins = new FileInputStream("log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(MainServer.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
        LOGGER.log(Level.INFO,"Начало работы сервера.");
        commandList.put("help ", " вывести справку по доступным командам");//
        commandList.put("info ", " вывести в стандартный поток вывода информацию о коллекции");//
        commandList.put("show ", " вывести в стандартный поток вывода все элементы коллекции в строковом представлении");//
        commandList.put("remove_key \"ключ\" ", " удалить элемент из коллекции по его ключу");//
        commandList.put("insert \"ключ\" {элемент} ", " добавить элемент с заданным ключом");//
        commandList.put("update_id \"id элемента\" {элемент} ", " обновить значение элемента коллекции, id которого равен заданному");//
        commandList.put("clear ", " очистить коллекцию");//
        commandList.put("execute_script \"имя файла\" ", " считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        commandList.put("exit ", " завершить программу (без сохранения в файл)");//
        commandList.put("remove_lower {элемент} ", " удалить из коллекции все элементы, меньшие, чем заданный");
        commandList.put("replace_if_greater \"ключ\" {элемент} ", " заменить значение по ключу, если новое значение больше старого");
        commandList.put("replace_if_lower \"ключ\" {элемент} ", " заменить значение по ключу, если новое значение меньше старого");
        commandList.put("sum_of_number_of_rooms ", " вывести сумму значений поля numberOfRooms для всех элементов коллекции");//
        commandList.put("max_by_transport ", " вывести любой объект из коллекции значение поля transport которого является максимальным");//
        commandList.put("print_ascending ", " вывести элементы коллекции в порядке возрастания");//
        try{
            try {
                WriteFile writeFile = new WriteFile();
                writeFile.writeFile();
                LOGGER.log(Level.INFO,"Прочитан json, введённый пользователем");
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Название файла, введённое пользователем, не найдён");
                System.out.println("Файл не найден");
            } catch (ParseException e) {
                LOGGER.log(Level.WARNING, "В файле, введённое пользователем, отображены некорректные данные.");
                System.out.println("Что-то не так с внутри этого файла");
            }
            GetAndSendMessage getAndSendMessage = new GetAndSendMessage();
            InputString inputString = new InputString();
            inputString.start();
            while (true) {
                getAndSendMessage.run();
            }
        } catch (NoSuchElementException e){
            System.out.println("Выхожу из программы");
            LOGGER.log(Level.OFF, "Экстренный выход из программы");
            System.exit(0);
        }

    }

    public static long GenerateId() {
        while (true) {
            final Random random = new Random();
            long id = Math.abs(random.nextLong());
            long cap = MainServer.flats.entrySet()
                    .stream()
                    .filter(x -> x.getValue().getId() != id)
                    .count();
            if (cap == MainServer.flats.size()) {
                return id;
            }
        }
    }
}
