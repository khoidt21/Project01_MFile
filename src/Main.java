/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.*;

/**
 *
 * @author ADMIN
 */
public class Main{

    //contains a list of MyFile
    private static MyFile[] files;

    //ctor
    public Main() {
        files = null;
    }

    //get information of all text files under given folder name
    public void loadFiles(String folder) {
        List<MyFile> listFiles = new ArrayList<>();
        loadFiles(folder, listFiles);
        files = listFiles.stream().toArray(MyFile[]::new);
    }

    public void loadFiles(String folder, List<MyFile> listFiles) {
        /*insert the code for listing all text files under given folder here*/
        File file = new File(folder);
        for (final File fileEntry : file.listFiles()) {
            if (fileEntry.isDirectory()) {
                loadFiles(fileEntry.getAbsolutePath(), listFiles);
            } else {
                listFiles.add(new MyFile(fileEntry.getName(), fileEntry.length(), fileEntry.getAbsolutePath()));
            }
        }
    }

    //list information of all loaded files
    public void list(MyFile[] files) {
        if (files != null && files.length > 0) {
            //output heading
            System.out.println(String.format("%-20s%-10s", "Name", "Size(in byte)"));
            for (MyFile f : files) {
                System.out.println(f);
            }
        } else {
            System.out.println("List of files is empty...");
        }
    }

    //sort the list of files ascending by size (use selection sort)
    public void selectionSort() {
        /*You should insert code for sorting here, you are going to sort the list of
         loaded files named "files" ascending by file size.*/

        for (int i = 0; i < files.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < files.length; j++) {
                if (files[j].getSize() < files[index].getSize()) {
                    index = j;
                }
            }
            if (i != index) {
                MyFile temp = files[i];
                files[i] = files[index];
                files[index] = temp;
            }
        }
    }
    
    //sort the list of files ascending by size (use insertion sort)
    public void insertionSort() {
        //do nothing if list of files is empty
        /*You should insert code for sorting here, you are going to sort the list of
         loaded files named "files" ascending by file size.*/
        
        if(files !=null){
            int holePosition;
            MyFile valueTonInsert;
            for(int i=1;i < files.length;i++){
                valueTonInsert = files[i];
                holePosition = i;
                while(holePosition > 0 && files[holePosition - 1].getSize() > valueTonInsert.getSize()){
                    files[holePosition] = files[holePosition -1];
                    holePosition = holePosition - 1;
                }
                files[holePosition] = valueTonInsert;
            }
        }
    }
    
    //sort and output sorted list of text files
    public void sort(SortType st) {
        if (st == SortType.INSERTTIONSORT) {
            insertionSort();
        } else if (st == SortType.SELECTIONSORT) {
            selectionSort();
        }
        else if(st == SortType.QUICKSORT){
            if(files !=null)
            quickSort(files,0,files.length - 1);
        }
        List<MyFile> tempList = new ArrayList<>();
        MyFile[] tempFiles = null;
        if(files !=null){
                for(MyFile myFile : files){
                    if(myFile.getName().toLowerCase().endsWith(".txt")) tempList.add(myFile);
                }
                tempFiles = tempList.stream().toArray(MyFile[]::new);
        }
        //output result after sorting
        //list(files);
        list(tempFiles);
    }

    //return true if given MyFile contains given keyword, otherwise return false
    public boolean searchFile(MyFile mf, String keyword) {
        if (!mf.getName().toLowerCase().endsWith(".txt")) { // chi search file .txt 
            return false;
        }
        BufferedReader bufferedReader;
        String currentLine;
        Reader reader;
        try{
            reader = new FileReader(mf.getFullPath());
            bufferedReader = new BufferedReader(reader);
            while((currentLine = bufferedReader.readLine())!=null){
                if(currentLine.contains(keyword)){
                    bufferedReader.close();
                    return true;
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    //output information of all files which content has given keyword
    public void searchFile(String keyword) {
        //save all files which matched given keyword to the list and output the list
        List<MyFile> listFiles = new ArrayList<>();
        if(files !=null)
        for (MyFile f : files) {
            if (searchFile(f, keyword)) {
                listFiles.add(f);
            }
        }
        MyFile[] foundFiles = listFiles.stream().toArray(MyFile[]::new);
        list(foundFiles);
        
    }
    public static void createMainMenu(){
        Scanner scanner = new Scanner(System.in);
        Main mainClass = new Main();
        boolean keepRunning = true;
        while(keepRunning){
            System.out.println("Menu");
            System.out.println("1. Load files");
            System.out.println("2. Soft files");
            System.out.println("3. Search files");
            System.out.println("4. Sort file by name (descending)");
            System.out.println("5. Sort file by size");
            System.out.println("6. Read file");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            int choice = inputInt(scanner,0,5);
            switch(choice){
                case 1 : 
                    mainClass.loadFiles(inputPath(scanner));
                    mainClass.list(files);
                    break;
                case 2 :
                    System.out.println("Sort the list of file by using");
                    System.out.println("1. Selection sort");
                    System.out.println("2. Insertion sort");
                    System.out.println("3. Quick sort");
                    System.out.println("Your choice: ");
                    choice = inputInt(scanner,1,3);
                    switch(choice){
                        case 1:
                            mainClass.sort(SortType.SELECTIONSORT);
                            break;
                        case 2:
                            mainClass.sort(SortType.INSERTTIONSORT);
                            break;
                        case 3:
                            mainClass.sort(SortType.QUICKSORT);
                            break;
                        default:
                            break;
                    }
                    break;
                case 3 : 
                    System.out.println("Enter any keywords to search: ");
                    mainClass.searchFile(scanner.nextLine());
                    break;
                case 4 : 
                    mainClass.sortByName();
                    break;
                case 5 : 
                    mainClass.sortBySize();
                    break;
                
                case 0 :
                    keepRunning = false;
                    break;
                default:
                    break;
            }
        
        }
    }
    // validate input Integer 
    public static int inputInt(Scanner scanner,int a,int b){
        try{
            int min,max;
            if(a < b){
                min = a;
                max = b;
            }else{
                max = a;
                min = b;
            }
            int result = Integer.parseInt(scanner.nextLine());
            if(result < min || result > max){
                System.out.println("You have to input integer from " + a + " to " + b + ". Retry");
                return inputInt(scanner, a, b);
            }
            else return result;
            
        }catch(Exception ex){
             System.out.println("You have to input integer from " + a + " to " + b + ". Retry");
             return inputInt(scanner, a, b);
        }
    }
    // validate Path
    public static String inputPath(Scanner scanner){
        System.out.println("Enter a folder: ");
        String input = scanner.nextLine();
        File file = new File(input);
        if(!file.exists() || !file.isDirectory()){
            System.err.println("Incorrect path");
            return inputPath(scanner);
        }
        return input;
    }
    // Quick sort
    private int partition(MyFile[] files,int begin,int end){
        long pivot = files[end].getSize();
        int i = (begin - 1);
        for(int j = begin; j < end;j++){
            if(files[j].getSize() <= pivot){
                i++;
                MyFile tmp = files[i];
                files[i] = files[j];
                files[j] = tmp;
            }
        }
        MyFile tmp = files[i+1];
        files[i+1] = files[end];
        files[end] = tmp;
        return i+1;
    }
    public void quickSort(MyFile[] files,int begin,int end){
        if(begin < end){
            int partitionIndex = partition(files, begin, end);
            quickSort(files, begin,partitionIndex -1);
            quickSort(files, partitionIndex + 1, end);
        }
    }
    
    // sort by name 
    public void sortByName(){
        List<MyFile> tempList = new ArrayList<>();
        MyFile[] tempFiles = null;
        if(files !=null){
            for(MyFile myFile : files){
                if(myFile.getName().toLowerCase().endsWith(".txt")) tempList.add(myFile);
                
            }
            Collections.sort(tempList); 
            tempFiles = tempList.stream().toArray(MyFile[]::new);         
        }
        list(tempFiles);
    }
    // sort by size
     public void sortBySize(){
        List<MyFile> tempList = new ArrayList<>();
        MyFile[] tempFiles = null;
        if(files !=null){
            for(MyFile myFile : files){
                if(myFile.getName().toLowerCase().endsWith(".txt")) tempList.add(myFile);
                
            }
            Collections.sort(tempList,new MyFileSortSize());
            tempFiles = tempList.stream().toArray(MyFile[]::new);         
        }
        list(tempFiles);
    }
     
    public static void main(String[] args) {
       createMainMenu();
    }
}
