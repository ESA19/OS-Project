import java.util.ArrayList;
public class FirstFit extends MemoryAllocationAlgorithm{
    public FirstFit(int[]availableBlockSizes){
        super(availableBlockSizes);
    }
    public int fitProcess(Process p,ArrayList<MemorySlot> currentlyUsedMemorySlots)
    {
        boolean fit=false;
        int address=-1;
        ArrayList<MemorySlot>availableSlots=this.mapAvailableMemory(currentlyUsedMemorySlots);

        for (MemorySlot memorySlot:availableSlots)
        {
            int sizeOfAvailableBlockOfSlot=memorySlot.getBlockEnd()-memorySlot.getStart()+1;
            if ((sizeOfAvailableBlockOfSlot>=p.getMemoryRequirements()))
            {
                fit=true;
                address=memorySlot.getStart();
                break;
            }
        }
        return address;
    }
}
