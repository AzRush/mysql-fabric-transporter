package Commands;

import lombok.Data;

import java.util.HashMap;

@Data
public class Insert extends Command{
    HashMap<String,Object> parameterMap;
}
