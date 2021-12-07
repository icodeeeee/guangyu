import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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
        String sb = readFile("shuo.json");
        List<YPBean> ypBeans = JSON.parseArray(sb, YPBean.class);
        System.out.println(JSON.toJSONString(ypBeans));
        if (ypBeans == null || ypBeans.size() == 0) return;
        AtomicReference<Integer> lastTime = new AtomicReference<>(ypBeans.get(0).getSongNotes().get(0).getTime());
        StringBuilder sbOut = new StringBuilder("[");
        LinkedList<String> z = (new LinkedList<>());
        List<SongNotesDTO> songNotes = ypBeans.get(0).getSongNotes();
        for (int i = 0; i < songNotes.size(); i++) {
            SongNotesDTO item = songNotes.get(i);
            String val = "[0,51," + JSON.toJSONString(key.get(item.getKey())) + "],";
            if (lastTime.get() !=item.getTime()) {
                if (z.size() > 0) {
                    //处理队内数据
                    sbOut.append("[");
                    for (int i1 = 0; i1 < z.size(); i1++) {
                        sbOut.append(z.get(i1));
                    }
                    sbOut.append("],");
                }
                z = new LinkedList<>();
                sbOut.append(val);
            } else {
                z.add(val);
            }
            System.out.println(lastTime);
            System.out.println(item.getTime());
            lastTime.set(item.getTime());
        }
        sbOut.append("]");

        System.out.println("键位->"+sbOut.toString());
        List<Integer> collect = songNotes.stream().map(SongNotesDTO::getTime).collect(Collectors.toList());
        System.out.println("休眠->"+collect.toString());

    }

    public static String readFile(String fileName) throws IOException {
        String basePath = "/Users/satan/Documents/pft-project/imot-project/miot_project/ihive-miot-webserver/branches/guangyu/src/main/java/yp/";
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
}
