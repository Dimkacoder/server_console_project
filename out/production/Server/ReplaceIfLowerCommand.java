import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ReplaceIfLowerCommand extends ReplaceIfLowerCommandUnex implements ExecuteCommand {
    public ReplaceIfLowerCommand(ReplaceIfLowerCommandUnex commandUnex){
        this.newFlat = commandUnex.newFlat;
        this.key = commandUnex.key;
    }
    @Override
    public String execute() {
        Map.Entry<Integer,Flat> MainFlat = MainServer.flats.entrySet().stream()
                .filter(x -> x.getKey().equals(key))
                .findFirst()
                .get();
        if (newFlat.compareTo(MainFlat.getValue())<0){
            MainServer.flats.put(MainFlat.getKey(),newFlat);
            return "Заменено";
        }else {
            return "Введённое значение оказался больше";
        }
    }
}
