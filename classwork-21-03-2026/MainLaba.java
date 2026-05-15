import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainLaba {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://185.221.160.131:81/files");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String result = "";
        String line;
        while ((line = br.readLine()) != null) {
            result += line + "\n";
        }
        br.close();
        isr.close();
        inputStream.close();
//        System.out.println(result);

        List<FileForLaba> files = new ArrayList<>();
        result = result.replaceAll("\\[\\{]", "");
        result = result.replaceAll("\\[\\{", "");
        result = result.replaceAll("}]", "");
        result = result.replaceAll("\n", "");


        String [] str = result.split("},\\{");

        for(String element : str){
            FileForLaba file = new FileForLaba();
            String [] strParts = element.split(",");

            for (String part : strParts){
                String correctedPart = part.replaceAll("\"", "");
                String[] elements = correctedPart.split(":");
                if ("id".equals(elements[0])){
                    file.setId(elements[1]);
                } else if ("fileName".equals(elements[0])) {
                    file.setFileName(elements[1]);
                }else if ("filePath".equals(elements[0])) {
                    file.setFilePath(elements[1]);
                }
                files.add(file);

            }
        }

        System.out.println(Arrays.toString(str));

        for(FileForLaba f : files) {
            String id = f.getId();
            url = new URL("http://185.221.160.131:81/files/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String codeByid = "";
            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeByid += lineOfCode + "\n";
            }
            br.close();
            isr.close();
            inputStream.close();
            System.out.println(codeByid);
            codeByid = codeByid.replaceAll("<br/>", "\n");
            String path = f.getFilePath();


            String absPath = new File(path).getAbsolutePath();
            if(absPath.equals("\\\\")) {
                absPath= "";
            }
            absPath = absPath.replaceFirst("\\\\", "");

            File folders = new File("C:\\Users\\user\\Desktop\\Cw_21-03-2026\\" + absPath);
            folders.mkdirs();
            System.out.println(absPath);
            try (FileWriter writer = new FileWriter("C:\\Users\\user\\Desktop\\Cw_21-03-2026\\" + absPath + f.getFileName())) {
                writer.write(codeByid);
            }
        }




    }
}