import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;



public class SaveAndLoad {
    public static void writeSave(save save, String filename) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename, false);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(save);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }

    public static save fetchSave(String filename) {
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            save save = (save) objectInputStream.readObject();
            System.out.println("Data loaded successfully!");
            return save;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading data: " + e.getMessage());
        }
        return null;
    }
}


 class save implements Serializable {
     String DB_URL;
     String DB_USER;
     String DB_PASSWORD;

     public save(String DB_URL, String DB_USER, String DB_PASSWORD) {
         this.DB_URL = DB_URL;
         this.DB_USER = DB_USER;
         this.DB_PASSWORD = DB_PASSWORD;
     }

     @Override
     public String toString() {
         return "DB_URL: " + DB_URL + ", DB_USER: " + DB_USER + ", DB_PASSWORD: " + DB_PASSWORD;
     }

}