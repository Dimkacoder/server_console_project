import java.util.Map;
import java.util.NoSuchElementException;

public class ReplaceIfGreaterCommand extends ReplaceIfGreaterCommandUnex implements ExecuteCommand {
    public ReplaceIfGreaterCommand(ReplaceIfGreaterCommandUnex commandUnex){
        this.newFlat = commandUnex.newFlat;
        this.key = commandUnex.key;
    }
    @Override
    public String execute() {
        Map.Entry<Integer,Flat> MainFlat = MainServer.flats.entrySet().stream()
                .filter(x -> x.getKey().equals(key))
                .findFirst()
                .get();
        if (newFlat.compareTo(MainFlat.getValue())>0){
            MainServer.flats.put(MainFlat.getKey(),newFlat);
            return "Заменено";
        }else{
            return "Введённое значение оказался меньше";
        }
    }
}
