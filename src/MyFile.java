/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 
import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class MyFile implements Serializable, Comparable<MyFile> {
    
    //contains information of a File
    
    private String name;
    public long size;
    private String fullPath;

    public MyFile() {
    }

    public MyFile(String name, long size, String fullPath) {
        this.name = name;
        this.size = size;
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public String toString() {
        return String.format("%-20s%-10d", name, size);
    }

    @Override
    public int compareTo(MyFile myfile) { 
        return this.name.compareTo(myfile.name);
    }
    
    /*
    public int compare(MyFile file1,MyFile file2){
        if(file1.size == file2.size){
            return 0;
        }
        else if(file1.size > file2.size){
            return 1;
        }
        else 
            return -1;
    }
    */
}
