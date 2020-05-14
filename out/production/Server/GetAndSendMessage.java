import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;

public class GetAndSendMessage extends Thread {
    public static DatagramChannel server;
    public void run() {
        try {
            server = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InetSocketAddress iAdd = new InetSocketAddress("localhost", 8989);
        try {
            server.bind(iAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer intBuf = ByteBuffer.allocate(256);
        //receive buffer from client.
        SocketAddress remoteAdd = null;
        try {
            server.receive(intBuf);
        } catch (IOException e) {
            System.out.println(" ");
        }
        intBuf.flip();
        int limitsInt = intBuf.limit();
        byte bytesInt[] = new byte[limitsInt];
        intBuf.get(bytesInt, 0, limitsInt);
        int capacity = 0;
        try {
            capacity = (int) Serializer.deserialize(bytesInt);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        try {
            remoteAdd = server.receive(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //change mode of buffer
        buffer.flip();
        int limits = buffer.limit();
        byte bytes[] = new byte[limits];
        buffer.get(bytes, 0, limits);
        Command gotCommand = null;
        MainServer.LOGGER.log(Level.INFO, "Принял пакет с коммандой");
        try {
            gotCommand = (Command) Serializer.deserialize(bytes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        RefactorUnexecutableClass<Command> refactoring = new RefactorUnexecutableClass<>();
        String msg = refactoring.executeCommand(gotCommand);
        buffer.clear();
        buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            intBuf = ByteBuffer.wrap(Serializer.serialize(buffer.capacity()));
            MainServer.LOGGER.log(Level.INFO, "Отправка ответа");
            server.send(intBuf, remoteAdd);
            server.send(buffer,remoteAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
