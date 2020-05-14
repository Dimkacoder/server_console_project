import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    public static Scanner sc;
    public String[] splitCommand;

    public Console() {
        sc = new Scanner(System.in);
    }
    public Console(String filename) throws FileNotFoundException {
        File file = new File(filename);
        sc = new Scanner(file);
    }

    public void writeCommand() throws IOException, ClassNotFoundException {
        System.out.println("Введите команду: ");

        String command = sc.nextLine();
        splitCommand = command.split(" ");
        String[] splitCommand2 = stringWithoutCom();
        Command sendingCom = null;
        switch (splitCommand[0]) {
            case "replace_if_lower"://
                sendingCom = new ReplaceIfLowerCommandUnex();
                break;
            case "replace_if_greater"://
                sendingCom = new ReplaceIfGreaterCommandUnex();
                break;
            case "remove_lower"://
                sendingCom = new RemoveLowerCommandUnex();
                break;
            case "print_ascending"://
                sendingCom = new PrintAscendingCommandUnex();
                break;
            case "max_by_transport"://
                sendingCom = new MaxTransportCommandUnex();
                break;
            case "update_id"://
                sendingCom = new UpdateIdCommandUnex();
                break;
            case "insert"://
                sendingCom = new InsertElementCommandUnex();
                break;
            case "execute_script":
                Scanner lastScanner = sc;
                ExecuteScript executeScript = new ExecuteScript();
                executeScript.execute(splitCommand2[0]);
                sc = lastScanner;
                return;
            case "sum_of_number_of_rooms"://
                sendingCom = new SumOfNumberOfRoomsCommandUnex();
                break;
            case "exit"://
                throw new NoSuchElementException();
            case "help"://
                sendingCom = new HelpCommandUnex();
                break;
            case "info"://
                sendingCom= new InfoCommandUnex();
                break;
            case "show"://
                sendingCom = new ShowCommandUnex();
                break;
            case "clear"://
                sendingCom = new ClearCommandUnex();
                break;
            case "remove_key"://
                sendingCom = new RemoveKeyCommandUnex();
                break;
            default:
                System.out.println("Таких команд я не понимаю, жалкий человек");
                return;
        }
        if(sendingCom.checkCom(splitCommand2)){
            byte[] bytes = Serializer.serialize(sendingCom);
            sending(bytes);
        }
    }
    private String[] stringWithoutCom(){
        String[] b = new String[splitCommand.length-1];
        for (int i = 0; i < splitCommand.length-1; i++) {
            b[i] = splitCommand[i+1];
        }
        return b;
    }
    private String sending(byte[] bytes) throws IOException, ClassNotFoundException {

        DatagramChannel channel = null;
        channel = DatagramChannel.open();
        channel.bind(null);
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        ByteBuffer intBuf = ByteBuffer.wrap(Serializer.serialize(buf.capacity()));
        InetSocketAddress address = new InetSocketAddress("localhost",8989);
        CheckingConnection checkingConnection = new CheckingConnection();
        checkingConnection.start();
        channel.send(intBuf,address);
        channel.send(buf,address);
        System.out.println("Отправка и ожидание ответа");
        buf.clear();


        intBuf = ByteBuffer.allocate(256);

        channel.receive(intBuf);

        intBuf.flip();
        int limits = intBuf.limit();
        bytes = new byte[limits];
        intBuf.get(bytes, 0, limits);
        int capacity = (int) Serializer.deserialize(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        channel.receive(buffer);
        checkingConnection.stop();
        System.out.println("Команда выполнена. Ответ сервера:");
        buffer.flip();
        int limits2 = buffer.limit();
        bytes = new byte[limits2];
        buffer.get(bytes, 0, limits2);
        System.out.println(new String(bytes));
        buffer.clear();
        intBuf.clear();


        return "";
    }
}
