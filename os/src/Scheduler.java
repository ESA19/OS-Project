import java.util.ArrayList;
public abstract class Scheduler {
    protected  ArrayList<Process>processes;

    protected boolean checksEveryCycleForNewProcesses;

    public Scheduler(){
        this.processes=new ArrayList<Process>();
    }
    public abstract void addProcess(Process p);

    public void removeProcess(Process p)
    {
        for (int i=0;i<processes.size();i++)
        {
            Process currentProcess=processes.get(i);
            if (currentProcess.getPCB().getPid()==p.getPCB().getPid()){
                processes.remove(i);
                break;
            }
        }

    }
    public abstract Process getNextProcess();

    public boolean getChecksEveryCycleForNewProcesses() {
        return checksEveryCycleForNewProcesses;
    }

}
