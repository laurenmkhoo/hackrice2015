package com.pbj.teststat;

/**
 * Created by cwang on 2/1/2015.
 */
import java.io.FileInputStream;
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

    public SerializationUtil(String inputFileName, String outputFileName) {
        inputFileName = inputFileName;
        outputFileName = outputFileName;

        try {
            fileInput = new FileInputStream(inputFileName);
            fileOutput = new FileOutputStream(outputFileName);

            objectInput = new ObjectInputStream(fileInput);
            objectOutput = new ObjectOutputStream(fileOutput);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // deserialize to Object from given file
    public Object deserialize() throws IOException, ClassNotFoundException {
        Object obj = objectInput.readObject();
        return obj;
    }

    // serialize the given object and save it to file
    public void serialize(Object obj) throws IOException {
        objectOutput.writeObject(obj);
    }
    public void inputFinish() {
        try {
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputFinish() {
        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
