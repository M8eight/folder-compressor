package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String thisPath = new File("").getAbsolutePath();

        System.out.println(thisPath);

        File f = new File("compressed");
        if (f.mkdir()) {
            System.out.println("Dir create");
        }

        List<String> allFiles = new ArrayList<>();
        File[] files = new File(System.getProperty("user.dir")).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                if (getExtensionByStringHandling(file.getName()).orElseThrow().equals("mp4")) {
                    allFiles.add(file.getName());
                }
            }
        }

        System.out.println("Find this files:");

        allFiles.forEach(System.out::println);

        for (String file : allFiles) {
            StringBuilder request = new StringBuilder();
            request.append("cmd /c ffmpeg -i ");
            request.append("\"" + file + "\" ");
            request.append("-vcodec libx265 -crf 28 ");
            request.append("\"" + thisPath + "\\" + "compressed" + "\\" + file + "\"");

            System.out.println(request);
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(request.toString());
        }

        System.out.println("Complete!");
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}

