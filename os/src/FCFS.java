public class FCFS extends Scheduler{
    private Process currentProcess;
    public FCFS(){
       this.currentProcess=null;
       checksEveryCycleForNewProcesses=false;
    }
    public void addProcess(Process p)
    {
        processes.add(p);
    }
    public Process getNextProcess(){
        if(!(this.currentProcess==null)){
            if (this.currentProcess.getPCB().getPid()!=this.processes.get(0).getPCB().getPid()){
                this.currentProcess=null;
            }
            else if(this.currentProcess.getPCB().getCurrentTotalTimeRun()==this.currentProcess.getBurstTime()){
                this.currentProcess.waitInBackground();
                removeProcess(this.currentProcess);
                this.currentProcess=null;
            }
        }
        if (!processes.isEmpty()){
            this.currentProcess=processes.get(0);
            return this.currentProcess;
        }
        else {
            return null;
        }
    }
}
