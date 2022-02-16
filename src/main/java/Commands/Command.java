package Commands;

import lombok.Data;

@Data
public class Command {
    String tableName;
    void execute(){}
}
