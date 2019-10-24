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
import java.util.List;

/**
 *
 * @author TrongDuyDao
 */
public class Main {

    //contains a list of MyFile
    private MyFile[] files;

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
        for (MyFile f : files) {
            if (searchFile(f, keyword)) {
                listFiles.add(f);
            }
        }
        MyFile[] foundFiles = listFiles.stream().toArray(MyFile[]::new);
        list(foundFiles);
    }

    public static void main(String[] args) {

    }
}
