//package com.videoco.eecs3311.project;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.channels.FileLock;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//
//import org.json.JSONArray;
//import org.json.simple.parser.JSONParser;
//public class Tester {
//   public static void main(String[] args) throws IOException {
//		JSONParser jsonParser = new JSONParser();
//		File file = new File("/Users/kvjha/Documents/EECS3311/FinalProject/src/main/java/org/openjfx/javafx/data/phoneOrders.json");
//		FileChannel channel= new RandomAccessFile(file,"rw").getChannel();
//        FileLock lock = channel.lock();
//        JSONArray array= jsonParser.parse((channel);
//
//		channel.close();
//   }  
//}
