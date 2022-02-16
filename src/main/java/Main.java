import BinlogReader.BinaryLogReader;
import Commands.Command;
import Commands.Delete;
import Commands.Insert;
import Commands.Update;
import FabricConnector.FabricConnector;
import MysqlParser.MysqlParser;

public class Main {
    public static void main(String args[]) throws Exception{
        FabricConnector fabricConnector = new FabricConnector("wallet", "connection-org1.yaml","user1");
        fabricConnector.setNetwork("mychannel");
        fabricConnector.setContract("fabcar1");
        BinaryLogReader binaryLogReader = new BinaryLogReader("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\DESKTOP-IQANN7S-bin.000028");
        byte[] queryAllCarsResult;
        for(String sql; (sql = binaryLogReader.readSql()) != null;){
            Command command = MysqlParser.parse(sql);
            if(command instanceof Update){
                System.out.println("update");
                queryAllCarsResult = fabricConnector.submitTransaction("createCar","update","1","2","3","4");
                System.out.println("query result: " + new String(queryAllCarsResult, "utf-8"));
            }else if(command instanceof Insert){
                System.out.println("insert");
                queryAllCarsResult = fabricConnector.submitTransaction("createCar","insert","1","2","3","4");
                System.out.println("query result: " + new String(queryAllCarsResult, "utf-8"));
            }else if(command instanceof Delete){
                System.out.println("delete");
                queryAllCarsResult = fabricConnector.submitTransaction("createCar","delete","1","2","3","4");
                System.out.println("query result: " + new String(queryAllCarsResult, "utf-8"));
            }
        }
    }
}
