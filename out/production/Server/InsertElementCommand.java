

public class InsertElementCommand extends InsertElementCommandUnex implements ExecuteCommand {
    public InsertElementCommand(InsertElementCommandUnex commandUnex){
        this.key = commandUnex.key;
        this.newFlat = commandUnex.newFlat;
    }
    @Override
    public String execute() {
        int size = MainServer.flats.size();

        newFlat.setCreationDate();
        long id = MainServer.GenerateId();
        newFlat.setId(id);
        MainServer.flats.put(key, newFlat);
        String msg = "Элемент под номером "+key.toString();
        if (MainServer.flats.size()==size){
            return msg+" заменён и добавлен";
        } else{
            return msg+" добавлен";
        }
    }
}
