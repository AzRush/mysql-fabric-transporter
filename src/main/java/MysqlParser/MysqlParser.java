package MysqlParser;

import Commands.Command;
import Commands.Delete;
import Commands.Insert;
import Commands.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class MysqlParser {

    public static boolean isString(String str){
        return (str.startsWith("'") && str.endsWith("'")) || (str.startsWith("\"") && str.endsWith("\"")) || (str.startsWith("`") && str.endsWith("`"));
    }
    public static String trim(String str){
        return str.substring(1,str.length() - 1);
    }
    public static Command parse(String query){
        Command command;
        String[] words = query.split(" ");
        String operation = words[0].toUpperCase(Locale.ROOT);
        switch (operation){
            case "UPDATE":
                command = new Update();
                command.setTableName(trim(words[1].trim()));
                HashMap<String,Object> parameterMap = new HashMap<>();
                HashMap<String,Object> whereMap = new HashMap<>();
                int pos = 3;
                while(!words[pos].equalsIgnoreCase("WHERE")){
                    String[] keyAndValue = words[pos].split("=");
                    String key = keyAndValue[0].trim();
                    key = trim(key);
                    String value = keyAndValue[1].trim();
                    if(isString(value))
                        parameterMap.put(key,value.substring(1,value.length() - 1));
                    else
                        parameterMap.put(key,Integer.parseInt(value));
                    pos++;
                }
                pos++;
                while(pos < words.length){
                    String[] keyAndValue = words[pos].split("=");
                    String key = keyAndValue[0].trim();
                    key = trim(key);
                    String value = keyAndValue[1].trim();
                    if(isString(value))
                        whereMap.put(key,value.substring(1,value.length() - 1));
                    else
                        whereMap.put(key,Integer.parseInt(value));
                    pos++;
                }
                ((Update)command).setParameterMap(parameterMap);
                ((Update)command).setWhereMap(whereMap);
                break;
            case "INSERT":
                command = new Insert();
                String keyString = words[2];
                String valueString = words[3];
                String tableName = trim(keyString.split("\\(")[0]);
                command.setTableName(tableName);
                keyString = keyString.split("\\(")[1];
                keyString = keyString.substring(0,keyString.length() - 1);
                valueString = valueString.split("\\(")[1];
                valueString = valueString.substring(0,valueString.length() - 1);
                String[] keys = keyString.split(",");
                String[] values = valueString.split(",");
                parameterMap = new HashMap<>();
                command.setTableName(tableName.trim().replaceAll("\"","").replaceAll("'",""));
                for(int i = 0; i < keys.length; i++){
                    keys[i] = trim(keys[i]);
                    if(isString(values[i]))
                        parameterMap.put(keys[i],values[i].substring(1,values[i].length() - 1));
                    else
                        parameterMap.put(keys[i],Integer.parseInt(values[i]));
                }
                ((Insert)command).setParameterMap(parameterMap);
                break;
            case "DELETE":
                command = new Delete();
                command.setTableName(trim(words[2].trim()));
                pos = 4;
                whereMap = new HashMap<>();
                while(pos < words.length){
                    String[] keyAndValue = words[pos].split("=");
                    String key = keyAndValue[0].trim();
                    key = trim(key);
                    String value = keyAndValue[1].trim();
                    if(isString(value))
                        whereMap.put(key,value.substring(1,value.length() - 1));
                    else
                        whereMap.put(key,Integer.parseInt(value));
                    pos++;
                }
                ((Delete)command).setWhereMap(whereMap);
                break;
            default:
                command = new Command();
                break;
        }
//        System.out.println(command.getClass());
        return command;
    }
    public static void main(String args[]){
        Update update = (Update) parse("update user_role set role_id=1 where user_id=4");
        System.out.println("in parameter map");
        update.getParameterMap().forEach((s, o) -> System.out.println("key : " + s + " value :" + o));
        System.out.println("in where map");
        update.getWhereMap().forEach((s, o) -> System.out.println("key : " + s + " value :" + o));

        Insert insert = (Insert) parse("insert into user(user_id,password,username) values(5,123,'Albert')");
        System.out.println("in parameter map");
        insert.getParameterMap().forEach((s, o) -> System.out.println("key : " + s + " value :" + o));

        Delete delete = (Delete) parse("delete from user where user_id=5");
        System.out.println("in where map");
        delete.getWhereMap().forEach((s, o) -> System.out.println("key : " + s + " value :" + o));
    }
}
