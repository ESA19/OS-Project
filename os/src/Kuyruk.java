import java.util.LinkedList;
import java.util.Queue;

public class Kuyruk {
    private Queue<Process> prosesler;

    public Kuyruk(){
        prosesler = new LinkedList<>();
    }
    public Process ilkElemaniGetir()
    {
        return prosesler.poll();
    }
    public void ekle(Process proses)
    {
        prosesler.add(proses);
    }

    public void sil(Process proses)
    {
        prosesler.remove(proses);
    }

    public boolean bosmu()
    {
        return prosesler.isEmpty();
    }

    public Queue<Process> getAll()
    {
        return prosesler;
    }

    public Process peek()
    {
        return prosesler.peek();
    }

    public boolean prosesVarmi(Process proses)
    {
        return getAll().stream().anyMatch(x->x.getPCB().getPid()==proses.getPCB().getPid());
    }
}