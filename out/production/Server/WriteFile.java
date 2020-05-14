import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

public class WriteFile {
    JSONObject jsonFlat;

    public void writeFile() throws ParseException, IOException {
        System.out.println("Введите название файла или путь файла, откуда считывать значения");
        MainServer.pathname = new Scanner(System.in).nextLine();
        if(!MainServer.pathname.endsWith(".json")){
            System.out.println("Работа ведётся только с json файлами. Отказ");
            return;
        }
        JSONParser jsonParser = new JSONParser();
        File file = new File(MainServer.pathname);
        Scanner scanner = new Scanner(file);
        String jsonStr = scanner.nextLine();
        JSONObject jsonAll = (JSONObject) jsonParser.parse(jsonStr);
        JSONArray jsonFLats = (JSONArray) jsonAll.get("FLATS");
        for (int i = 0; i < jsonFLats.size(); i++) {
            jsonFlat = (JSONObject) jsonFLats.get(i);
            JsonDecoder jsonDecoder = new JsonDecoder(jsonFlat.toJSONString());
            Integer key = jsonDecoder.getDecodeKey();
            Flat value = jsonDecoder.getDecodeValue();
            value.setId(MainServer.GenerateId());
            MainServer.flats.put(key,value);
        }
        if (MainServer.flats.size()==0){
            System.out.println("Пустой json");
            return;
        }
        System.out.println("Значения успешно переданы и готовы к работе");
    }
}
