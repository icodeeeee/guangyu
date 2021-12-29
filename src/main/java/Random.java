import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Random {
    public static void main(String[] args) {
        String baseDir = "/Users/satan/Documents/pft-project/imot-project/miot_project/ihive-miot-webserver/branches/guangyu/src/main/java/";
        File file = new File(baseDir+"output");
        LinkedList<String> ggs = new LinkedList<>();
        LinkedList<String> vvs = new LinkedList<>();
        Arrays.stream(file.listFiles()).forEach(item -> {
            try {
                String s = Shuo.readFile(item);
                String[] s3 = s.replaceAll(" ", "").replaceAll("\n", "")
                        .split("var");
                ggs.add(s3[1].split("=")[1]);
                vvs.add(s3[2].split("=")[1].split("]")[0] + "]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            String template = Shuo.readFile("TEMPLATE_MORE.js", baseDir+"yp/");

            template = template.replaceAll("\\[keys\\]", "["+ggs.stream().collect(Collectors.joining(","))+"]")
                    .replaceAll("\\[sleeps\\]", "["+vvs.stream().collect(Collectors.joining(","))+"]");
            Shuo.writeStringToFile(template, "A随机", baseDir+"output/");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
