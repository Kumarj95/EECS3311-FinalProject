package com.videoco.eecs3311.project;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

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
public class Tester {
   public static void main(String[] args)  {
       Class classobj = PhoneUser.class;
       Method[] methods = classobj.getMethods();
       for (Method method : methods) {
           Class returnParam = method.getReturnType();
           Parameter[] params= method.getParameters();
           ArrayList<String> parameters= new ArrayList<String>();
           for(Parameter param: params) {
        	   try {
        	   parameters.add(param.getType().toString().substring(param.getType().toString().lastIndexOf('.')+1));
        	   }catch(Exception e) {
        		   
        	   }
           }
           String s="";
           String a="";
           for(String p:parameters) {
        	   s+=a;
        	   a=", ";
        	   s+=p;
        	   
           }
           
           System.out.println("+" + method.getName()+"(" +s +")" + " : " +returnParam.getName().substring(returnParam.getName().lastIndexOf('.')+1));           


       }
       

   }  
}
