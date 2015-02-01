package com.pbj.teststat;

/**
 * Created by cwang on 2/1/2015.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {
    private String inputFileName = null;
    private String outputFileName = null;
    private FileInputStream fileInput;
    private FileOutputStream fileOutput;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;

    public SerializationUtil() {
    }


    // serialize the given object and save it to file
    public void startDeserialize(String inputFileName) {
        try {
            fileInput = new FileInputStream(inputFileName);
            objectInput = new ObjectInputStream(fileInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // deserialize to Object from given file
    public Object deserialize() throws IOException, ClassNotFoundException {
        Object obj = objectInput.readObject();
        return obj;
    }

    public void deserializeFinish() {
        try {
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // serialize the given object and save it to file
    public void startSerialize(String outputFileName) {
        try {
            fileOutput = new FileOutputStream(outputFileName);
            objectOutput = new ObjectOutputStream(fileOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void serialize(Object obj) throws IOException {
        objectOutput.writeObject(obj);
    }

    public void serializeFinish() {
        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
