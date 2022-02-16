package Commands;

import lombok.Data;

import java.util.HashMap;

@Data
public class Delete extends Command{
    HashMap<String,Object> whereMap;
}
