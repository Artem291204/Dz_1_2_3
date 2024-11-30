import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress player1 = new GameProgress(100, 2, 20, 453.43);
        GameProgress player2 = new GameProgress(69, 2, 34, 400.12);
        GameProgress player3 = new GameProgress(43, 3, 54, 342.67);
        StringBuilder str = new StringBuilder();

        List<String> firstGen = Arrays.asList("D:\\Games\\src", "D:\\Games\\res", "D:\\Games\\temp");
        List<String> secondGenSrc = Arrays.asList("D:\\Games\\src\\main", "D:\\Games\\src\\test");
        List<String> thirdGenMain = Arrays.asList("D:\\Games\\src\\main\\Main.java", "D:\\Games\\src\\main\\Utils.java");
        List<String> secondGenRes = Arrays.asList("D:\\Games\\res\\drawables", "D:\\Games\\res\\vectors", "D:\\Games\\res\\icons");
        List<String> secondGenTemp = Arrays.asList("D:\\Games\\temp\\temp.txt");


        for (int i = 0; i < firstGen.size(); i++) {
            File file = creatorFiles(firstGen.get(i));
            str.append(checkerDirectory(file));
        }
        File savegames = creatorFiles("D:\\Games\\savegames");
        str.append(checkerDirectory(savegames));

        for (int i = 0; i < secondGenSrc.size(); i++) {
            File file = creatorFiles(secondGenSrc.get(i));
            str.append(checkerDirectory(file));
        }

        for (int i = 0; i < thirdGenMain.size(); i++) {
            File file = creatorFiles(thirdGenMain.get(i));
            str.append(checkerFile(file));
        }

        for (int i = 0; i < secondGenRes.size(); i++) {
            File file = creatorFiles(secondGenRes.get(i));
            str.append(checkerDirectory(file));
        }

        for (int i = 0; i < secondGenTemp.size(); i++) {
            File file = creatorFiles(secondGenTemp.get(i));
            str.append(checkerFile(file));
        }

        try (FileWriter tempFile = new FileWriter(secondGenTemp.get(0), false)) {
            tempFile.write(str.toString());
            tempFile.flush();
        } catch (IOException ex) {
            ex.getMessage();
        }


        saveGame("D:\\Games\\savegames\\save1.dat", player1);
        saveGame("D:\\Games\\savegames\\save2.dat", player2);
        saveGame("D:\\Games\\savegames\\save3.dat", player3);

        //тут все

        List<String> liste = new ArrayList<>();
//        for (File fileer : file3.listFiles()) {
//            liste.add(fileer.getPath());
//        }

        for (File fileer : savegames.listFiles()) {
            liste.add(fileer.getPath());
        }
        zipFiles("D:\\Games\\savegames\\zip1.zip", liste);
        openZip("D:\\Games\\savegames\\zip1.zip", savegames.getPath());
        System.out.println(openProgress("D:\\Games\\savegames\\save1.dat"));


    }

    public static void saveGame(String path, GameProgress players) {
        try (FileOutputStream saver = new FileOutputStream(path);
             ObjectOutputStream saverStream = new ObjectOutputStream(saver)) {
            saverStream.writeObject(players);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZip, List<String> files) {
        try (ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(pathZip))) {


            for (String file : files) {
                String[] b = file.split("\\\\");
                String fileNice = b[3];
                try (FileInputStream filesSave = new FileInputStream(file)) {
                    ZipEntry runner = new ZipEntry(fileNice);

                    zipFile.putNextEntry(runner);

                    byte[] buffer = new byte[filesSave.available()];

                    filesSave.read(buffer);

                    zipFile.write(buffer);
                    zipFile.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                File deleter = new File(file);
                deleter.delete();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openZip(String zipFile, String pathFile) {
        try (ZipInputStream zipFiles = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipFiles.getNextEntry()) != null) {

                FileOutputStream fileIn = new FileOutputStream(pathFile + "\\" + entry.getName());
                for (int c = zipFiles.read(); c != -1; c = zipFiles.read()) {
                    fileIn.write(c);
                }

                fileIn.flush();
                zipFiles.closeEntry();
                fileIn.close();

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static GameProgress openProgress(String playerSave) {
        GameProgress player = null;
        try (FileInputStream fileRead = new FileInputStream(playerSave);
             ObjectInputStream outputer = new ObjectInputStream(fileRead)) {
            player = (GameProgress) outputer.readObject();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return player;
    }

    public static File creatorFiles(String sourceFile) {
        File file = new File(sourceFile);
        return file;

    }

    public static String checkerFile(File file) {
        try {
            if (file.createNewFile()) {
                return file.getName() + " создался\n";
            } else {
                return file.getName() + "Main.java не создался\n";
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "Error";

    }

    public static String checkerDirectory(File file) {
        if (file.mkdir()) {
            return file.getName() + " создался\n";
        } else {
            return file.getName() + " не создался\n";
        }
    }
}


//        File file1 = new File("D:\\Games", "src");
//        File file2 = new File("D:\\Games", "res");
//        File file3 = new File("D:\\Games", "savegames");
//        File file4 = new File("D:\\Games", "temp");
//
//
//
//        if (file1.mkdir()) {
//            str.append("src создался\n");
//        } else {
//            str.append("src не создался\n");
//        }
//        if (file2.mkdir()) {
//            str.append("res создался\n");
//        } else {
//            str.append("res не создался\n");
//        }
//        if (file3.mkdir()) {
//            str.append("savegames создался\n");
//        } else {
//            str.append("savegames не создался\n");
//        }
//        if (file4.mkdir()) {
//            str.append("temp создался\n");
//        } else {
//            str.append("temp не создался\n");
//        }
//
//        File file1_1 = new File(file1, "main");
//        File file1_2 = new File(file1, "test");
//
//        if (file1_1.mkdir()) {
//            str.append("main создался\n");
//        } else {
//            str.append("main не создался\n");
//        }
//        if (file1_2.mkdir()) {
//            str.append("test создался\n");
//        } else {
//            str.append("test не создался\n");
//        }
//
//        File file1_1_1 = new File(file1_1, "Main.java");
//        File file1_1_2 = new File(file1_1, "Utils.java");
//
//        try {
//            if (file1_1_1.createNewFile()) {
//                str.append("Main.java создался\n");
//            } else {
//                str.append("Main.java не создался\n");
//            }
//
//            if (file1_1_2.createNewFile()) {
//                str.append("Utils.java создался\n");
//            } else {
//                str.append("Utils.java не создался\n");
//            }
//
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        File file2_1 = new File(file2, "drawables");
//        File file2_2 = new File(file2, "vectors");
//        File file2_3 = new File(file2, "icons");
//
//        if (file2_1.mkdir()) {
//            str.append("drawables создался\n");
//        } else {
//            str.append("drawables не создался\n");
//        }
//        if (file2_2.mkdir()) {
//            str.append("vectors создался\n");
//        } else {
//            str.append("vectors не создался\n");
//        }
//        if (file2_3.mkdir()) {
//            str.append("icons создался\n");
//        } else {
//            str.append("icons не создался\n");
//        }
//
//        File file4_1 = new File(file4, "temp.txt");
//        try {
//            if (file4_1.createNewFile()) {
//                str.append("temp.txt создался\n");
//            } else {
//                str.append("temp.txt не создался\n");
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//        try (FileWriter tempInput = new FileWriter(file4_1, false)) {
//            tempInput.write(str.toString());
//            tempInput.flush();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }