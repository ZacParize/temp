package ru.mycompany;

import java.io.*;

/**
 * Created by pdv on 09.07.2015.
 */

public class Serializator {

    public static <T extends Object & Serializable> void serialize(T object) {
        try (FileOutputStream fileOut = new FileOutputStream("/object.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut); ) {
            out.writeObject(object);
            System.out.printf("Serialized data is saved in /object.ser\n");
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static <T extends Object & Serializable> T deserialize() {
        T result = null;
        try (FileInputStream fileIn = new FileInputStream("/object.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn);)  {
            result = (T) in.readObject();
        } catch(IOException i) {
            i.printStackTrace();
        } catch(ClassNotFoundException c) {
            c.printStackTrace();
        } finally {
            return result;
        }
    }

}
