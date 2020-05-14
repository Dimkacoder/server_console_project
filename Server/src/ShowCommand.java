
public class ShowCommand extends ShowCommandUnex implements ExecuteCommand {
    @Override
    public String execute() {
        String[] all = {""};
        MainServer.flats.entrySet().stream()
                .forEach(x -> all[0] += "Ключ "+x.getKey()+" "+((Flat)x.getValue()).toString());
        return all[0];
    }
}
