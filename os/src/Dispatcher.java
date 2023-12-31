import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    public static int clock=0;
    private Scheduler scheduler;
    private MemoryManagmentUnit mmu;
    private  Process[] processes;
    private int currentProcess;



    List<Process> userProcessList = new ArrayList<>();
    List<Process> realTimeProcessList = new ArrayList<>();

    private ArrayList<Process>processList;

    public Dispatcher(Scheduler scheduler,MemoryManagmentUnit mmu,Process[]processes)
    {
        this.scheduler=scheduler;
        this.mmu=mmu;
        this.processes=processes;

    }
    public void run()
    {
        this.processList=new ArrayList<>();

        for (Process aProcess:processes)
        {
            if (fitsAtMemory(aProcess)){
                processList.add(aProcess);
            }
        }

        sortProcesses(processList);
        Process previousProcess=null;
        Process currentProcess=null;

        if (scheduler.getChecksEveryCycleForNewProcesses()){
            do{
                if (currentProcess!=null&&currentProcess.getBurstTime()==currentProcess.getPCB().getCurrentTotalTimeRun()){
                    currentProcess.waitInBackground();
                }
                loadProcessesIntoMemory();
                currentProcess=scheduler.getNextProcess();

                if (currentProcess==null){
                    if (previousProcess!=null)
                    {
                        previousProcess=null;
                    }
                    tick();
                    continue;
                }
                if (previousProcess==null)
                {
                    tick();
                    tick();
                    kaynakAzalt(currentProcess);
                    currentProcess.run();

                }
                else if (previousProcess.getPCB().getPid()!=currentProcess.getPCB().getPid()){
                    previousProcess.waitInBackground();
                    tick();
                    tick();
                    kaynakAzalt(currentProcess);
                    currentProcess.run();
                }
                previousProcess=currentProcess;
                incrementTimeRun(currentProcess);
                tick();



            }while(!(currentProcess==null&&processList.isEmpty()));
        }
        else{
            do {
                if (currentProcess!=null&&currentProcess.getBurstTime()==currentProcess.getPCB().getCurrentTotalTimeRun()){
                    currentProcess.waitInBackground();
                }
                loadProcessesIntoMemory();
                currentProcess=scheduler.getNextProcess();
                if (previousProcess!=null)
                {
                    previousProcess.waitInBackground();
                    previousProcess=null;
                }
                if (currentProcess==null){
                    tick();
                    continue;
                }
                previousProcess=currentProcess;
                tick();
                tick();
                kaynakAzalt(currentProcess);
                currentProcess.run();

                while(currentProcess!=null&&(previousProcess.getPCB().getPid()==currentProcess.getPCB().getPid())){
                    incrementTimeRun(currentProcess);
                    tick();
                    currentProcess=scheduler.getNextProcess();
                }
            }while(!(currentProcess==null&&processList.isEmpty()));
        }
    }
    private  void incrementTimeRun(Process p)
    {
        p.getPCB().setCurrentTotalTimeRun(p.getPCB().getCurrentTotalTimeRun()+1);
    }
    private void loadProcessesIntoMemory()
    {
        for (int i=0;i<processList.size();){
            Process aProcess=processList.get(i);
            if (aProcess.getArrivalTime()<=clock){

                if (mmu.loadProcessIntoRAM(aProcess)){
                    processList.remove(i);
                    if(KaynakKısıt(aProcess)){
                        scheduler.addProcess(aProcess);
                        aProcess.getPCB().setState(ProcessState.READY,clock);
                        aProcess.error="bitti";
                        tick();
                    }else{
                        aProcess.error="HATA - Proses cok sayida kaynak talep ediyor - proses silindi";
                    }



                    continue;
                }
            }
            i++;
        }

    }

    private boolean KaynakKısıt(Process process)
    {
        if(process.modemRequirements <= Globals.amountOfModem  && process.cdRequirements <= Globals.amountOfCd  &&  process.printerRequirements <= Globals.amountOfPrinter && process.tarayiciRequirements<=Globals.amountOfTarayici)
        {

            return true;
        }
        return false;

    }


    private void sortProcesses(ArrayList<Process>processesList)
    {
        for (int i=0;i<processesList.size()-1;i++)
        {
            int min=i;
            for (int j=i+1;j<processesList.size();j++){
                if (processesList.get(j).getArrivalTime()<processesList.get(min).getArrivalTime()){
                    min=j;
                }
                Process temp=processesList.get(min);
                processesList.set(min,processesList.get(i));
                processesList.set(i,temp);
            }
        }
    }
    private boolean fitsAtMemory(Process aProcess){
        int[]availableBlockSize=mmu.getAvailableBlockSizes();
            if(aProcess.priority==0){
                if (aProcess.getMemoryRequirements()<=availableBlockSize[0]){
                    return true;
                }
                aProcess.error="HATA - Gerçek-zamanlı proses (64MB) tan daha fazla bellek talep ediyor - proses silindi";
                return false;
            }else{
                for (int i=1; i<availableBlockSize.length;i++){
                    if (aProcess.getMemoryRequirements()<=availableBlockSize[i]){
                        return true;
                    }

                }
                aProcess.error="HATA - Proses (960 MB) tan daha fazla bellek talep ediyor – proses silindi ";

                return false;
            }

    }
    public void tick()
    {
        for (int i=0;i<processList.size();i++){
            Process aProcess=processList.get(i);
            if(clock-aProcess.getArrivalTime()>20) {
                aProcess.error+="----------zaman kisiti";
            }
        }
        clock+=1;
    }
    public void kaynakAzalt(Process process){
        Globals.amountOfModem -= process.modemRequirements;
         Globals.amountOfPrinter -= process.printerRequirements;
        Globals.amountOfCd -= process.cdRequirements;
        Globals.amountOfTarayici -= process.tarayiciRequirements;
    }
}
