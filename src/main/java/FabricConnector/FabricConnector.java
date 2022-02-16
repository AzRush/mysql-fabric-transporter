package FabricConnector;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.impl.FileSystemWallet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class FabricConnector {
    private Gateway gateway;
    private Network network;
    private Contract contract;
    public FabricConnector(String walletPath, String networkConfigPath, String username) throws Exception{
        Path walletDirectory = Paths.get(walletPath);
        Wallet wallet = new FileSystemWallet(walletDirectory);
        //Path为fabric网络配置文件的路径
        Path networkConfigFile = Paths.get(networkConfigPath);
        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, username)
                .networkConfig(networkConfigFile);
        gateway = builder.connect();
    }

    public void setNetwork(String channelName){
        network = gateway.getNetwork(channelName);
    }
    public void setContract(String contractName){
        contract = network.getContract(contractName);
    }
    // 查询事务
    public byte[] evaluateTransaction(String s, String... strings) throws ContractException {
        return contract.evaluateTransaction(s,strings);
    }
    // 修改事务
    public byte[] submitTransaction(String s, String... strings) throws ContractException, InterruptedException, TimeoutException {
        return contract.submitTransaction(s,strings);
    }

    // 新建事务
    public Transaction createTransaction(String s){
        return contract.createTransaction(s);
    }
    public static void main(String args[]) throws Exception{
        FabricConnector fabricConnector = new FabricConnector("wallet", "connection-org1.yaml","user1");
        fabricConnector.setNetwork("mychannel");
        fabricConnector.setContract("fabcar1");
        byte[] queryAllCarsResult = fabricConnector.evaluateTransaction("queryChainCodeByNo","3336_order");
        System.out.println("query result: " + new String(queryAllCarsResult, "utf-8"));
    }
}
