import java.security.cert.CertPath;
import java.util.ArrayList;

public class Process {
    private ProcessControlBlock pcb;
    String error;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    public int printerRequirements;
    public int modemRequirements;
    public int tarayiciRequirements;
    public int cdRequirements;
    public int priority;
    public ProcessControlBlock getPCB()
    {
        return this.pcb;
    }
    public Process(int arrivalTime, int burstTime,int memoryRequirements,int priority,int printerRequirements, int tarayiciRequirements,int modemRequirements,int cdRequirements)
    {
        this.arrivalTime=arrivalTime;
        this.burstTime=burstTime;
        this.priority=priority;
        this.memoryRequirements=memoryRequirements;
        this.printerRequirements=printerRequirements;
        this.tarayiciRequirements=tarayiciRequirements;
        this.cdRequirements=cdRequirements;
        this.modemRequirements=modemRequirements;
        this.pcb=new ProcessControlBlock();
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getMemoryRequirements()
    {
        return this.memoryRequirements;
    }
    public int getBurstTime(){
        return burstTime;
    }
    public void run()
    {
        if (this.pcb.getState().equals(ProcessState.READY))
        {
            this.pcb.setState(ProcessState.RUNNING,Dispatcher.clock);
        }
    }
    public void waitInBackground()
    {
        if (this.pcb.getState().equals(ProcessState.RUNNING))
        {
            if (this.pcb.getCurrentTotalTimeRun()==this.burstTime)
            {
                this.pcb.setState(ProcessState.TERMINATED,Dispatcher.clock);
            }
            else{
                this.pcb.setState(ProcessState.READY, Dispatcher.clock);
            }
        }
    }
    public double getWaitingTime()
    {
        if (getPCB().getState().equals(ProcessState.TERMINATED)){
            return getTurnAroundTime()-this.burstTime;
        }
        return -1;
    }
    public double getResponseTime()
    {
        if(getPCB().getState().equals(ProcessState.TERMINATED))
        {
            return getPCB().getStartTimes().get(0)-this.arrivalTime;
        }
        return -1;
    }
    public double getTurnAroundTime()
    {
       double turnAroundTime=-1;
       if (getPCB().getState().equals(ProcessState.TERMINATED)){
           turnAroundTime=getPCB().getStopTimes().get(getPCB().getStopTimes().size()-1)-this.arrivalTime;
       }
       return turnAroundTime;
    }

}
