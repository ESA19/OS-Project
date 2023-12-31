import java.util.ArrayList;
public abstract class MemoryAllocationAlgorithm {
    protected final int[] availableBlockSizes;
    public MemoryAllocationAlgorithm(int[] availableBlockSizes){
        this.availableBlockSizes=availableBlockSizes;
    }
    public abstract int fitProcess(Process p, ArrayList<MemorySlot>currentlyUsedMemorySlots);
    protected ArrayList<MemorySlot>mapAvailableMemory(ArrayList<MemorySlot> currentlyUsedMemorySlots){

        ArrayList<MemorySlot> availableMemorySlots=new ArrayList<>();
        for (int currentMemPosition=0,b_i=0;b_i<availableBlockSizes.length;b_i++)
        {
            availableMemorySlots.add(new MemorySlot(currentMemPosition,currentMemPosition+availableBlockSizes[b_i]-1,currentMemPosition,currentMemPosition+availableBlockSizes[b_i]-1));

            currentMemPosition+=availableBlockSizes[b_i];
        }

        for (MemorySlot cu: currentlyUsedMemorySlots)
        {
            for (int i=0;i<availableMemorySlots.size();i++){
                MemorySlot currentMemSlot=availableMemorySlots.get(i);
                if (cu.getStart()>=currentMemSlot.getStart()&&cu.getEnd()<=currentMemSlot.getEnd()){
                    int leftSlotStart,leftSlotEnd;
                     int rightSlotStart,rightSlotEnd;

                     if (cu.getStart()==currentMemSlot.getStart()){
                         leftSlotStart=leftSlotEnd=-1;
                     }else{
                         leftSlotStart=currentMemSlot.getStart();
                         leftSlotEnd=cu.getStart()-1;
                     }
                     if (cu.getEnd()==currentMemSlot.getEnd()){
                         rightSlotStart=rightSlotEnd=-1;
                     }else{
                         rightSlotStart=cu.getEnd()+1;
                         rightSlotEnd=currentMemSlot.getEnd();
                     }
                     availableMemorySlots.remove(i);

                     if (leftSlotStart!=-1&&leftSlotEnd!=-1){
                         availableMemorySlots.add(i,new MemorySlot(leftSlotStart,leftSlotEnd,currentMemSlot.getBlockStart(),currentMemSlot.getBlockEnd()));
                         i++;
                     }
                     if (rightSlotStart!=-1&&rightSlotEnd!=-1)
                     {
                         availableMemorySlots.add(i,new MemorySlot(rightSlotStart,rightSlotEnd,currentMemSlot.getBlockStart(),currentMemSlot.getBlockEnd()));
                     }
                     break;

                }
            }
        }
        return availableMemorySlots;

    }

}
