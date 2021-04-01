package me.giraffetree.java.boomjava.life.company;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GiraffeTree
 * @date 2021-03-19
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String path = "E:\\data\\2021\\03\\simid\\simid.log";
        List<String> lines = FileUtils.readLines(new File(path), Charset.defaultCharset());
        // mac, simId
        HashMap<String, String> map = new HashMap<>();
        for (String line : lines) {
            JSONObject jsonObject = JSON.parseObject(line);
            String log = jsonObject.getString("log");
            int simIndex = StringUtils.indexOf(log, "simId:");
            int macIndex = StringUtils.indexOf(log, "get ecg base status - mac");
            if (simIndex >= 0 && macIndex >= 0) {
                String s1 = log.substring(macIndex + 26, macIndex + 39);
                String s2 = log.substring(simIndex, simIndex + 28);
                map.put(s1, s2);
            }
        }
        File file = new File("E:\\data\\2021\\03\\simid\\out3.txt");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s = entry.getKey() + " " + entry.getValue()+"\n";
            FileUtils.write(file, s, Charset.defaultCharset(), true);
        }

    }
}
