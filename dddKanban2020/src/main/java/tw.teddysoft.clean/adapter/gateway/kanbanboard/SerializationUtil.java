package tw.teddysoft.clean.adapter.gateway.kanbanboard;

import java.io.*;
import java.util.List;

public class SerializationUtil {

//    public static void saveToFile(String fileName, List<? extends Entity> entities){
    public static void saveToFile(String fileName, List<?> entities){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(entities);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            close(oos);
            close(fos);
        }
    }

    public static List<?> loadFromFile(String fileName) {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            List<?> results = null;

            try {
                fis = new FileInputStream(fileName);
                ois = new ObjectInputStream(fis);
                results = (List<?>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                close(ois);
                close(fis);
            }
            return results;
    }


//    public static List<? extends Entity> loadFromFile(String fileName){
//        public static List<? extends Entity> loadFromFile(String fileName){
//        ObjectInputStream ois = null;
//        FileInputStream fis = null;
//        List<? extends Entity> results = null;
//
//        try {
//            fis = new FileInputStream(fileName);
//            ois = new ObjectInputStream(fis);
//            results = (List<? extends  Entity>)ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        finally {
//            close(ois);
//            close(fis);
//        }
//        return results;
//    }

    public static boolean storedFileExists(String fileName){
        return new File(fileName).exists();
    }


    private static void close(Closeable arg) {
        try {
            if(null != arg)
                arg.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
