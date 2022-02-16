package Commands;

import lombok.Data;

import java.util.HashMap;

@Data
public class Update extends Command{
    HashMap<String,Object> whereMap;
    HashMap<String,Object> parameterMap;    
}
