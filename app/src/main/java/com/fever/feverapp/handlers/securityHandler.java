
package com.fever.feverapp.handlers;

import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class securityHandler {

    private static String key = "i44ddaa9y";
    private static String SAVEFILEPATH = "C:\\Users\\omiso\\Desktop\\FeverApp\\app\\src\\main\\java\\com\\fever\\feverapp\\tmp\\";

    public static String getKey() {
        return key;
    }

    public static String getSAVEFILEPATH() {
        return SAVEFILEPATH;
    }

    public static boolean saveuserList(Admin admin){
        if(admin.verify()){
            try{
                File f = new File(SAVEFILEPATH + "userList.ser");
                if(f.exists() &&  f.isFile()){
                    f.delete();
                }
                FileOutputStream fileOut = new FileOutputStream(SAVEFILEPATH + "userList.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(User.getUserList());
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in file path /userList.ser");
                return true;
            } catch (IOException i) {
                i.printStackTrace();
            }
        }return false;
    }

    public static boolean readuserList(Admin admin){
        if(admin.verify()){
            try {
                FileInputStream fileIn = new FileInputStream(SAVEFILEPATH +"userList.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                User.setUserList((LinkedBag<User>) in.readObject());
                in.close();
                fileIn.close();
                return true;
            }catch (IOException i) {
                i.printStackTrace();
                return false;
            } catch (ClassNotFoundException c) {
                System.out.println("UserList class not found");
                c.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean saveSongList(Admin admin){
        if(admin.verify()){
            try{
                File f = new File(SAVEFILEPATH +"songList.ser");
                if(f.exists() &&  f.isFile()){
                    f.delete();
                }
                FileOutputStream fileOut = new FileOutputStream(SAVEFILEPATH + "songList.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(User.getUserList());
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in /tmp/songList.ser");
                return true;
            } catch (IOException i) {
                i.printStackTrace();
            }
        }return false;
    }

}
