import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int N = 2;
        final int Z = 0;
        List<Work> allWorksList = new ArrayList<>();
        Work fW = new Work("w", 0, 0, null);
        fW.jobIsDone();
        allWorksList.add(fW);
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("C:\\Users\\809279\\IdeaProjects\\hellow\\src\\input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            String[] splittedString = scanner.nextLine().split(" ");
            List<String> splittedWorks = Arrays.asList(splittedString[3].split(","));
            List<Work> prevWorks = new ArrayList<>();
            for (String sW : splittedWorks) {
                for (Work aW : allWorksList) {
                    if (sW.equals(aW.workName)) {
                        prevWorks.add(aW);
                    }
                }
            }
            allWorksList.add(new Work(splittedString[0], Integer.parseInt(splittedString[1]), Integer.parseInt(splittedString[2]), prevWorks));
        }
        allWorksList.remove(fW);

        List<Workers> workers = new ArrayList<>();
        for (int i = 0; i < N; i++)
            workers.add(new Workers());

        List<Work> possibleWorks = new ArrayList<>();
        List<Work> allDoneWorks = new ArrayList<>();
        List<Work> inTimeWorks = new ArrayList<>();
        List<Work> currentlyDoneWorks = new ArrayList<>();

        int time = 0;
        while (allDoneWorks.size() != allWorksList.size()) {
            List<Work> toDelete = new ArrayList<>();
            for (Work inTimeWork : inTimeWorks) {
                if (inTimeWork.endTime == time) {
                    allDoneWorks.add(inTimeWork);
                    currentlyDoneWorks.add(inTimeWork);
                    inTimeWork.jobIsDone();
                    toDelete.add(inTimeWork);
                    for (Workers worker : workers) {
                        if (worker.endTime == time) {
                            worker.isWorking = false;
                        }
                    }
                }
            }
            for (Work work : allWorksList) {
                if (!work.isDone) {
                    int count = 0;
                    for (Work prevWork : work.previousWorks) {
                        if (prevWork.isDone) {
                            count++;
                        }
                    }
                    if (count == work.previousWorks.size() && !allDoneWorks.contains(work)) {
                        possibleWorks.add(work);
                    }
                }
            }
            switch (Z) {
                case 0: {
                    possibleWorks.sort(new Comparator<Work>() {
                        @Override
                        public int compare(Work o1, Work o2) {
                            if (o1.hours > o2.hours) return 1;
                            else return (o1.hours < o2.hours) ? -1 : 0;
                        }
                    });
                    break;
                }
                case 1: {
                    possibleWorks.sort(new Comparator<Work>() {
                        @Override
                        public int compare(Work o1, Work o2) {
                            if (o1.hours < o2.hours) return 1;
                            else return (o1.hours > o2.hours) ? -1 : 0;
                        }
                    });
                    break;
                }
                case 2: {
                    possibleWorks.sort(new Comparator<Work>() {
                        @Override
                        public int compare(Work o1, Work o2) {
                            if (o1.additionalHours > o2.additionalHours) return 1;
                            else return (o1.additionalHours < o2.additionalHours) ? -1 : 0;
                        }
                    });
                    break;
                }
                case 3: {
                    possibleWorks.sort(new Comparator<Work>() {
                        @Override
                        public int compare(Work o1, Work o2) {
                            if (o1.additionalHours < o2.additionalHours) return 1;
                            else return (o1.additionalHours > o2.additionalHours) ? -1 : 0;
                        }
                    });
                    break;
                }
            }

            System.out.println("time = " + time);
            System.out.print("possibleWorks:");
            possibleWorks.removeAll(inTimeWorks);
            for (Work posW : possibleWorks) {
                System.out.print(posW.workName + "-" + posW.hours + "h, ");
            }
            System.out.println();
            for (Workers workers1 : workers) {
                if (!workers1.isWorking)
                    System.out.println("worker" + workers.indexOf(workers1) + " isn't working atm");
                else
                    System.out.println("worker" + workers.indexOf(workers1) + " ends at time = " + workers1.endTime);
            }
            for (Workers worker : workers) {
                if (!worker.isWorking && possibleWorks.size() != 0) {
                    worker.isWorking = true;
                    worker.endTime = time + possibleWorks.get((possibleWorks.size() - 1)).hours;
                    inTimeWorks.add(possibleWorks.get((possibleWorks.size() - 1)));
                    for (Work works : allWorksList) {
                        if (possibleWorks.get((possibleWorks.size() - 1)).workName.equals(works.workName)) {
                            works.endTime = time + works.hours;
                        }
                    }
                    possibleWorks.remove(possibleWorks.size() - 1);
                }
            }
            possibleWorks.clear();


            inTimeWorks.removeAll(toDelete);
            System.out.print("Done works:");
            for (Work doWo : currentlyDoneWorks) {
                System.out.print(doWo.workName + ", ");
            }
            System.out.println();
            System.out.print("ATM works:");
            for (Work atmWorks : inTimeWorks) {
                System.out.print(atmWorks.workName + " ends at time = " + atmWorks.endTime + ", ");
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------");
            currentlyDoneWorks.clear();
            time++;
        }
    }
}
