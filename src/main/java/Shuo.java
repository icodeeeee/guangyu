import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Shuo {
    public static Map<String, Integer[]> key = new HashMap<String, Integer[]>() {{
        put("1Key0", new Integer[]{800, 200});
        put("1Key1", new Integer[]{1000, 200});
        put("1Key2", new Integer[]{1200, 200});
        put("1Key3", new Integer[]{1400, 200});
        put("1Key4", new Integer[]{1600, 200});

        put("1Key5", new Integer[]{800, 400});
        put("1Key6", new Integer[]{1000, 400});
        put("1Key7", new Integer[]{1200, 400});
        put("1Key8", new Integer[]{1400, 400});
        put("1Key9", new Integer[]{1600, 400});

        put("1Key10", new Integer[]{800, 600});
        put("1Key11", new Integer[]{1000, 600});
        put("1Key12", new Integer[]{1200, 600});
        put("1Key13", new Integer[]{1400, 600});
        put("1Key14", new Integer[]{1600, 600});
    }};


    public static void main(String[] args) throws IOException {

        //原始乐谱名称
        String fileName = "夜的第七章(1).txt";

        String sb = readFile(fileName, "yp").replaceAll("2K", "1K");
        String template = readFile("TEMPLATE.js", "yp");

        List<YPBean> ypBeans = JSON.parseArray(sb, YPBean.class);
        if (ypBeans == null || ypBeans.size() == 0) return;
        StringBuilder sbOut = new StringBuilder("[");
        LinkedList<SongNotesDTO> songNotesTemp = ypBeans.get(0).getSongNotes();
        LinkedHashMap<Integer, List<SongNotesDTO>> collect = new LinkedHashMap<Integer, List<SongNotesDTO>>() {{
            Map<Integer, List<SongNotesDTO>> collect1 = songNotesTemp.stream().collect(Collectors.groupingBy(songNotesDTO -> songNotesDTO.getTime()));
            List<Integer> integers = collect1.keySet().stream().sorted().collect(Collectors.toList());
            for (int i = 0; i < integers.size(); i++) {
                put(integers.get(i), collect1.get(integers.get(i)));
            }
        }};

        LinkedList<List<SongNotesDTO>> songNotes = new LinkedList<>(collect.values());

        for (int i = 0; i < songNotes.size(); i++) {
            List<SongNotesDTO> items = songNotes.get(i);
            if (items.size() == 1) {
                //单键
                SongNotesDTO item = items.get(0);
                String val = "[0,51," + JSON.toJSONString(key.get(item.getKey())) + "],";
                sbOut.append("[");
                sbOut.append(val);
                sbOut.append("],");
            } else {
                sbOut.append("[");
                //多键
                for (int i1 = 0; i1 < items.size(); i1++) {
                    SongNotesDTO item = items.get(i1);
                    String val = "[0,51," + JSON.toJSONString(key.get(item.getKey())) + "],";
                    sbOut.append(val);
                }
                sbOut.append("],");
            }
        }
        sbOut.append("]");

        LinkedList<Integer> sl = new LinkedList<>(collect.keySet());
        LinkedList<Integer> slEnd = new LinkedList<Integer>() {{
            for (int i = 0; i < sl.size(); i++) {
                Integer item = sl.get(i);
                if (i == 0) {
                    add(item);
                } else {
                    add(item - sl.get(i - 1));
                }
            }
        }};
        template = template.replaceAll("\\[key\\]", sbOut.toString())
                .replaceAll("\\[sleep\\]", JSON.toJSONString(slEnd));
        writeStringToFile(template, fileName.split("\\.")[0], "output");
        System.out.println("操作完成");
    }

    public static String readFile(String fileName, String pdir) throws IOException {
        String basePath = "/Users/satan/Documents/pft-project/imot-project/miot_project/ihive-miot-webserver/branches/guangyu/src/main/java/" + pdir + "/";
        //名称
        File file = new File(basePath + fileName);
        if (!file.exists()) return null;

        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public static String writeStringToFile(String data, String fileName, String pdir) throws IOException {
        String basePath = "/Users/satan/Documents/pft-project/imot-project/miot_project/ihive-miot-webserver/branches/guangyu/src/main/java/" + pdir + "/";
        //名称
        File file = new File(basePath + fileName + ".js");
        if (!file.exists()) file.createNewFile();

        StringBuilder sb = new StringBuilder();

        BufferedWriter br = new BufferedWriter(new FileWriter(file));
        String s = null;
        br.write(data);
        br.close();
        return sb.toString();
    }
}
