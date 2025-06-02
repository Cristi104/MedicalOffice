package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private FileWriter csv;

    public AuditService(String filename){
        try {
            csv = new FileWriter(filename, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String action, String data){
        try {
            csv.write(LocalDateTime.now() + "," + action + "," + data + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            csv.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
