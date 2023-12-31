public class MemorySlot {
    private int start;
    private int end;
    private final int blockStart;
    private final int blockEnd;

    private Process processAssociated;
    public MemorySlot(int start,int end,int blockStart,int blockEnd){
        if ((start<blockStart)||(end>blockEnd))
        {
            throw new java.lang.RuntimeException("Memory Access out of bounds");

        }
        this.start=start;
        this.end=end;
        this.blockStart=blockStart;
        this.blockEnd=blockEnd;
    }
    public int getBlockStart()
    {
        return blockStart;
    }
    public int getBlockEnd()
    {
        return blockEnd;
    }
    public int getStart()
    {
        return start;
    }
    public void setStart(int start)
    {
        this.start=start;
    }
    public int getEnd()
    {
        return end;
    }
    public void setEnd(int end)
    {
        this.end=end;
    }
    public Process getProcessAssociated(){
        return processAssociated;
    }
    public void setProcessAssociated(Process processAssociated)
    {
        this.processAssociated=processAssociated;
    }

}
