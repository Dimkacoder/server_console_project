import java.util.LinkedHashMap;

public class RemoveLowerCommand extends RemoveLowerCommandUnex implements ExecuteCommand {
    public RemoveLowerCommand(RemoveLowerCommandUnex commandUnex){
        this.newFlat = commandUnex.newFlat;
    }
    @Override
    public String execute() {
        LinkedHashMap<Integer,Flat> newMap = new LinkedHashMap<>();
        MainServer.flats.entrySet()
                .stream()
                .filter(x -> {
                    int comp = newFlat.compareTo(x.getValue());
                    if(comp>0){
                        return false;
                    } else{
                        return true;
                    }
                })
                .forEach(x->newMap.put(x.getKey(),x.getValue()));
        int size = MainServer.flats.size();
        MainServer.flats = newMap;
        if(MainServer.flats.size() == size){
            return "Коллекция не изменилась";
        }else {
            return "Удалено "+(size-MainServer.flats.size())+" элементов";
        }

    }
}
