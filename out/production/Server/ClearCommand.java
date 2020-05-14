public class ClearCommand extends ClearCommandUnex implements ExecuteCommand {

    public String execute(){
        MainServer.flats.clear();
        return "Коллекция очистилась";
    }

}
