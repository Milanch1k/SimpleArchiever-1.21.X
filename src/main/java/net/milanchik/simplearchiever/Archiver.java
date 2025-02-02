package net.milanchik.simplearchiever;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archiver {

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Укажите путь до файла:");
//        String fileName = scanner.nextLine();
//        System.out.println("Укажите путь куда вы хотите заархивировать файл:");
//        String zipFileName = scanner.nextLine();
//        try {
//            compressArchive(fileName, zipFileName);
//            System.out.println("Архивирование завершенно, путь до заархивированного файла: " + zipFileName);
//        } catch (IOException e) {
//            System.out.println("Ошибка: " + e.getMessage());
//        }
//    }

    public static void compressArchive(String fileName, String zipFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            File fileToZip = new File(fileName);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
        }
    }
}
