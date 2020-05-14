import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveKeyCommand extends RemoveKeyCommandUnex implements ExecuteCommand {
    public RemoveKeyCommand(RemoveKeyCommandUnex e){
        this.key = e.key;
    }
    @Override
    public String execute() {
        int lengthPrev = MainServer.flats.size();
        LinkedHashMap<Integer,Flat> newFlat = new LinkedHashMap<>();
        MainServer.flats.entrySet()
                .stream()
                .filter(x -> x.getKey()!=key)
                .forEach((k)->newFlat.put(k.getKey(),k.getValue()));
        if(newFlat.size()==lengthPrev){
            return "Не найдено элемента с таким ключом";
        }else {
            MainServer.flats = newFlat;
            return "Элемент под номером "+key+" удалён";
        }

    }
}
