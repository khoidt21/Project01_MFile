
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class MyFileSortSize implements Comparator<MyFile>{
    
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
}
