import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class JsonParserGson {
    Gson gson = new Gson();

    public List<Map<String, String>> parse(String json) {

        return  gson.fromJson(json, new TypeToken<List<Map<String, String>>>(){}.getType());
    }
}
