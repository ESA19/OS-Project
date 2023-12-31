import java.util.ArrayList;
public class MemoryManagmentUnit {
    private final int[]availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot>currentlyUsedMemorySlots;

    public MemoryManagmentUnit(int[]availableBlockSizes,MemoryAllocationAlgorithm algorithm)
    {
        this.availableBlockSizes=availableBlockSizes;
        this.algorithm=algorithm;
        this.currentlyUsedMemorySlots=new ArrayList<MemorySlot>();
    }
    public boolean loadProcessIntoRAM(Process p)
    {
        boolean fit=false;
        cleanUp();

        int newProcessAddress=algorithm.fitProcess(p,currentlyUsedMemorySlots);

        if (newProcessAddress!=-1)
        {
            for (int currentMemPosition=0,b_i=0;b_i< availableBlockSizes.length;b_i++){
                if (newProcessAddress >= currentMemPosition  &&  newProcessAddress  <= currentMemPosition+availableBlockSizes[b_i]-1){
                    MemorySlot newSlot=new MemorySlot(newProcessAddress,newProcessAddress+p.getMemoryRequirements()-1,currentMemPosition,currentMemPosition+availableBlockSizes[b_i]-1);
                    newSlot.setProcessAssociated(p);
                    currentlyUsedMemorySlots.add(newSlot);
                    fit=true;
                    break;
                }
                currentMemPosition+=availableBlockSizes[b_i];
            }
        }
        p.error="bellek yok";
        return  fit;
    }
    public int[]getAvailableBlockSizes(){
        return availableBlockSizes;
    }
    private void cleanUp()
    {
        int i=0;
        while(i<currentlyUsedMemorySlots.size()){
            if (currentlyUsedMemorySlots.get(i).getProcessAssociated().getPCB().getState()==ProcessState.TERMINATED){
                Globals.amountOfCd+=currentlyUsedMemorySlots.get(i).getProcessAssociated().cdRequirements;
                Globals.amountOfModem+=currentlyUsedMemorySlots.get(i).getProcessAssociated().modemRequirements;
                Globals.amountOfTarayici+=currentlyUsedMemorySlots.get(i).getProcessAssociated().tarayiciRequirements;
                Globals.amountOfPrinter+=currentlyUsedMemorySlots.get(i).getProcessAssociated().printerRequirements;
           //     if(currentlyUsedMemorySlots.get(i).getProcessAssociated().error.isEmpty())
             //       currentlyUsedMemorySlots.get(i).getProcessAssociated().error="bitti";
                currentlyUsedMemorySlots.remove(i);
                continue;
            }
            i++;
        }
    }
}
