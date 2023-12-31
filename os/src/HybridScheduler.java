import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HybridScheduler extends Scheduler {

    private final FCFS fcfsScheduler = new FCFS();
    private final MultilevelFeedbackQueueExample mlfqScheduler = new MultilevelFeedbackQueueExample();

    public HybridScheduler() {
        // ... (additional initialization if needed)
    }

    @Override
    public void addProcess(Process p) {
        if (p.priority == 0) {
            fcfsScheduler.addProcess(p);
        } else {
            mlfqScheduler.addProcess(p);
        }
    }

    @Override
    public Process getNextProcess() {
        //fcfs first



        if (findFirstProcess(fcfsScheduler.processes).getArrivalTime() <= mlfqScheduler.firstQueues.peek().getArrivalTime()
                && findFirstProcess(fcfsScheduler.processes).getArrivalTime() <= mlfqScheduler.secondQueues.peek().getArrivalTime()
                && findFirstProcess(fcfsScheduler.processes).getArrivalTime() <= mlfqScheduler.thirdQueues.peek().getArrivalTime()
        ) {
            return fcfsScheduler.getNextProcess();
        } else if(mlfqScheduler.firstQueues.bosmu() &&mlfqScheduler.secondQueues.bosmu() &&mlfqScheduler.thirdQueues.bosmu()){
            return mlfqScheduler.getNextProcess();
        }
        else{

            return null;
        }

    }

    public Process findFirstProcess(ArrayList<Process> pList){
        for(Process p:pList){
            if(p.getPCB().getState()!=ProcessState.TERMINATED)
            {

                return p;
            }
        }
        return null;
    }

    // ... (other methods as needed)
}
