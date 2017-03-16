import java.util.ArrayList;
import java.util.List;


public class Work {
    public boolean isDone = false;
    public List<Work> previousWorks = new ArrayList<>();
    public int hours;
    public int additionalHours;
    public String workName;
    public int endTime;

    public Work(String workName, int hours, int additionalHours, List<Work> prevWorks) {
            this.workName = workName;
            this.hours = hours;
            this.additionalHours = additionalHours;
            this.previousWorks = prevWorks;
    }

    public void jobIsDone() {
        this.isDone = true;
    }

    @Override
    public boolean equals(Object obj) {
        Work work = (Work) obj;
        return (this.workName.equals(work.workName));
    }
}
