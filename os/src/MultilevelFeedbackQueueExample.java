import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MultilevelFeedbackQueueExample extends Scheduler {

    public Kuyruk firstQueues ;
    public Kuyruk secondQueues;
    public Kuyruk thirdQueues;

    public MultilevelFeedbackQueueExample() {
        firstQueues=new Kuyruk();
        secondQueues=new Kuyruk();
        thirdQueues=new Kuyruk();
    }

    @Override
    public void addProcess(Process p) {
        int priority = p.priority;
        System.out.println(priority+"-"+p.getPCB().getPid());
        switch (priority) {
            case 1:
                firstQueues.ekle(p);
                System.out.println(priority+"*"+p.getPCB().getPid());
                break;
            case 2:
                secondQueues.ekle(p);
                System.out.println(priority+"*"+p.getPCB().getPid());
                break;
            case 3:
                thirdQueues.ekle(p);
                System.out.println(priority+"*"+p.getPCB().getPid());
                break;
        }
    }

    @Override
    public Process getNextProcess() {
      if (!(firstQueues.bosmu()|| secondQueues.bosmu()|| thirdQueues.bosmu()))
      {
          if(
                  firstQueues.peek().getArrivalTime()<=secondQueues.peek().getArrivalTime()
                          && firstQueues.peek().getArrivalTime()<=thirdQueues.peek().getArrivalTime()
          ){
              return firstQueues.peek();
          } else if (
                  secondQueues.peek().getArrivalTime() < firstQueues.peek().getArrivalTime()
                          && secondQueues.peek().getArrivalTime() <= thirdQueues.peek().getArrivalTime()
          ) {
              return secondQueues.peek();
          }else if(
                  thirdQueues.peek().getArrivalTime()<secondQueues.peek().getArrivalTime()
                          && thirdQueues.peek().getArrivalTime()<firstQueues.peek().getArrivalTime()
          ){
              return thirdQueues.peek();
          }
      }

        return null;
    }

    // ... (other methods as needed)
}
