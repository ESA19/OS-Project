public class RoundRobin extends Scheduler{
    private int quantum;
    private Process runningProcess;

    private int remainingQuantumTime;

    private int lastSeenTime;

    public RoundRobin(){
        this.quantum=1;
        runningProcess=null;
        remainingQuantumTime=0;
        checksEveryCycleForNewProcesses=true;
        lastSeenTime=0;
    }
    public RoundRobin(int quantum)
    {
        this();
        this.quantum=quantum;
    }
    public void addProcess(Process p)
    {
        processes.add(p);
    }
    public Process getNextProcess()
    {
        if (runningProcess!=null)
        {
            if (processes.isEmpty()||runningProcess.getPCB().getPid()!=processes.get(0).getPCB().getPid()){
                runningProcess=null;
                remainingQuantumTime=quantum;
            }
            else{
                if (lastSeenTime!=runningProcess.getPCB().getCurrentTotalTimeRun())
                {
                    remainingQuantumTime--;
                }
                if (runningProcess.getBurstTime()==runningProcess.getPCB().getCurrentTotalTimeRun())
                {
                    runningProcess.waitInBackground();
                    processes.remove(0);
                    runningProcess=null;
                    remainingQuantumTime=quantum;
                }
                else if (remainingQuantumTime==0)
                {
                    processes.remove(0);
                    processes.add(runningProcess);

                    runningProcess=null;
                    remainingQuantumTime=quantum;

                }

            }

        }
        else {
            remainingQuantumTime=quantum;
        }
        if (runningProcess!=null)
        {
            lastSeenTime=runningProcess.getPCB().getCurrentTotalTimeRun();
            return runningProcess;
        }
        else{
            if (processes.isEmpty()){
                lastSeenTime=0;
                return null;
            }
            else{
                runningProcess=processes.get(0);
                lastSeenTime=runningProcess.getPCB().getCurrentTotalTimeRun();
                return runningProcess;
            }
        }


    }
}
