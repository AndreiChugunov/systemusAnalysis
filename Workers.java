import java.util.ArrayList;
import java.util.List;

/**
 * Created by 809279 on 16.03.2017.
 */
public class Workers {
    public boolean isWorking = false;
    public int endTime;
    public List<String> hold = new ArrayList<>();
    public String currentWork = "";
    public Workers() {
        this.endTime = 0;
    }
}
