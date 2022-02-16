package BinlogReader;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.File;
import java.io.IOException;

public class BinaryLogReader {
    BinaryLogFileReader binaryLogFileReader;
    public BinaryLogReader(String filePath) throws IOException{
        File binlogFile = new File(filePath);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setChecksumType(ChecksumType.CRC32);
        binaryLogFileReader = new BinaryLogFileReader(binlogFile, eventDeserializer);
    }
    public String readSql() throws IOException {
        Event event = binaryLogFileReader.readEvent();
        if(event == null)return null;
        EventData data = event.getData();
        if(data instanceof QueryEventData)
            return ((QueryEventData)data).getSql();
        else
            return "";
    }

    public void close() throws IOException{
        binaryLogFileReader.close();
    }
    public static void main(String[] args) throws IOException {
//        String filePath="C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\DESKTOP-IQANN7S-bin.000025";
//        File binlogFile = new File(filePath);
//        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setChecksumType(ChecksumType.CRC32);
//        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
//
//        try {
//            for (Event event; (event = reader.readEvent()) != null; ) {
//
//                try {
//                    QueryEventData queryEventData = event.getData();
//                    System.out.println(queryEventData.getSql());
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }catch (Exception e){
//           e.printStackTrace();
//        }finally{
//            reader.close();
//        }
        BinaryLogReader binaryLogReader = new BinaryLogReader("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\DESKTOP-IQANN7S-bin.000025");
        try {
            int cnt = 0;
            for(String sql; (sql = binaryLogReader.readSql()) != null;){
                System.out.println(++cnt + sql);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
