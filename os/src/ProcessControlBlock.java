import java.util.ArrayList;
import java.util.Random;
public class ProcessControlBlock {
    private final int pid;
    private ProcessState state;
    private static  int pidTotal=0;
    private ArrayList<Integer>startTimes;
    private ArrayList<Integer>stopTimes;
    private int currentTotalTimeRun;
    public ProcessControlBlock()
    {
        this.state=ProcessState.NEW;
        this.startTimes=new ArrayList<Integer>();
        this.stopTimes=new ArrayList<Integer>();
        pidTotal++;
        this.pid=pidTotal;
        currentTotalTimeRun=0;

    }
    public ProcessState getState()
    {
        return this.state;
    }
    public void setState(ProcessState state,int currentClockTime) {

        if ((this.state.equals(ProcessState.NEW))&&(state.equals(ProcessState.READY)))
        {
            this.state=ProcessState.READY;
        }
        if ((this.state.equals(ProcessState.READY))&&(state.equals(ProcessState.RUNNING)))
        {
            this.state=ProcessState.RUNNING;
            this.startTimes.add(currentClockTime);
        }
        if ((this.state.equals(ProcessState.RUNNING))&&(state.equals(ProcessState.READY)))
        {
            this.state=ProcessState.READY;
            this.stopTimes.add(currentClockTime);
        }
        if ((this.state.equals(ProcessState.RUNNING))&&(state.equals(ProcessState.TERMINATED)))
        {
            this.state=ProcessState.TERMINATED;
            this.stopTimes.add(currentClockTime);
        }

    }
    public int getCurrentTotalTimeRun()
    {
        return currentTotalTimeRun;
    }
    public void setCurrentTotalTimeRun(int currentTotalTimeRun){
        this.currentTotalTimeRun=currentTotalTimeRun;
    }
    public int getPid()
    {
        return this.pid;
    }
    public ArrayList<Integer> getStartTimes() {
        return startTimes;
    }
    public ArrayList<Integer> getStopTimes() {
        return stopTimes;
    }




}


