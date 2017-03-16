import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int N = 2;
        List<Work> allWorksList = new ArrayList<>();
        Work dW = new Work("dw", 0, 0, null);
        dW.jobIsDone();
        List<Work> dWList = new ArrayList<>();
        dWList.add(dW);
        Work work12 = new Work("w12", 2, 0, dWList);
        List<Work> doneWorks = new ArrayList<>();
        doneWorks.add(work12);
        Work work13 = new Work("w13", 3, 2, dWList);
        List<Work> doneWorks_1 = new ArrayList<>();
        doneWorks_1.add(work13);
        Work work24 = new Work("w24", 7, 0, doneWorks);
        Work work35 = new Work("w35", 6, 4, doneWorks_1);
        Work work34 = new Work("w34", 4, 2, doneWorks_1);
        Work work25 = new Work("w25", 4, 7, doneWorks);
        List<Work> doneWorks_2 = new ArrayList<>();
        doneWorks_2.add(work25);
        doneWorks_2.add(work35);
        Work work56 = new Work("w56", 6, 5, doneWorks_2);
        Work work57 = new Work("w57", 3, 6, doneWorks_2);
        Work work58 = new Work("w58", 4, 4, doneWorks_2);
        List<Work> doneWorks_3 = new ArrayList<>();
        doneWorks_3.add(work24);
        doneWorks_3.add(work34);
        Work work46 = new Work("w46", 9, 2, doneWorks_3);
        Work work47 = new Work("w47", 6, 3, doneWorks_3);
        Work work48 = new Work("w48", 8, 0, doneWorks_3);
        List<Work> doneWorks_4 = new ArrayList<>();
        doneWorks_4.add(work46);
        doneWorks_4.add(work56);
        Work work69 = new Work("w69", 5, 2, doneWorks_4);
        List<Work> doneWorks_5 = new ArrayList<>();
        doneWorks_5.add(work47);
        doneWorks_5.add(work57);
        Work work79 = new Work("w79", 7, 3, doneWorks_5);
        List<Work> doneWorks_6 = new ArrayList<>();
        doneWorks_6.add(work48);
        doneWorks_6.add(work58);
        Work work89 = new Work("w89", 8, 0, doneWorks_6);

        Work[] workArray = {work12, work13, work24, work25, work34, work35, work46, work47, work48, work56, work57, work58, work69, work79, work89};
        allWorksList = Arrays.asList(workArray);
        List<Workers> workers = new ArrayList<>();
        for (int i = 0; i < N; i++)
            workers.add(new Workers());

        List<Work> possibleWorks = new ArrayList<>();
        List<Work> allDoneWorks = new ArrayList<>();
        List<Work> inTimeWorks = new ArrayList<>();

        int time = 0;
        while (allDoneWorks.size() != allWorksList.size()) {
            List<Work> toDelete = new ArrayList<>();
            for (Work inTimeWork : inTimeWorks) {
                if (inTimeWork.endTime == time) {
                    allDoneWorks.add(inTimeWork);
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
            possibleWorks.sort(new Comparator<Work>() {
                @Override
                public int compare(Work o1, Work o2) {
                    if (o1.hours > o2.hours) return 1;
                    else return (o1.hours < o2.hours) ? -1 : 0;
                }
            });
            if (time == 21) {
                possibleWorks.remove(work47);
                possibleWorks.add(work47);
            }
            System.out.print("time = " + time);
            System.out.print("   possibleWorks:");
            possibleWorks.removeAll(inTimeWorks);
            for (Work posW : possibleWorks) {
                System.out.print(posW.workName + ", ");
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
                    possibleWorks.remove(possibleWorks.size()-1);
                }
            }
            possibleWorks.clear();


            inTimeWorks.removeAll(toDelete);
            System.out.print("   Done works:");
            for (Work doWo : allDoneWorks) {
                System.out.print(doWo.workName + ", ");
            }
            System.out.print("  ATM works:");
            for (Work atmWorks : inTimeWorks) {
                System.out.print(atmWorks.workName + ", ");
            }
            System.out.println();
            time ++;
        }
    }
}
