import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static  void main(String[] args) {

        // Dosyadan okunan process bilgilerini tutmak için bir liste oluştur
        List<Process> processList = new ArrayList<>();

        if (args.length < 1) {
            System.exit(1);
        }
        
        String girisDosyasiAdi = args[0];
        // 'giris.txt' dosyasını oku
        try (BufferedReader br = new BufferedReader(new FileReader(girisDosyasiAdi))) {
            String line;

            // Her satırı oku
            while ((line = br.readLine()) != null) {
                // Satırdaki değerleri virgülle ayır
                String[] values = line.split(",");

                // Değerleri parçalara ayır ve yeni bir Process nesnesi oluştur
                int arrivalTime = Integer.parseInt(values[0].trim());
                int priority = Integer.parseInt(values[1].trim());
                int burstTime = Integer.parseInt(values[2].trim());
                int memoryRequirements = Integer.parseInt(values[3].trim());
                int printer = Integer.parseInt(values[4].trim());
                int tarayici = Integer.parseInt(values[5].trim());
                int modem = Integer.parseInt(values[6].trim());
                int cd = Integer.parseInt(values[7].trim());

                Process process = new Process(arrivalTime,burstTime,memoryRequirements,priority,printer,tarayici,modem,cd);
                processList.add(process);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process dizisini ve diğer bileşenleri kullanarak devam et
        Process[] processes = processList.toArray(new Process[0]);
         // sizes in kB
        MemoryAllocationAlgorithm algorithm = new FirstFit(Globals.availableBlockSizes);
        MemoryManagmentUnit mmu = new MemoryManagmentUnit(Globals.availableBlockSizes, algorithm);
        Scheduler scheduler = new FCFS();
        Dispatcher cpu = new Dispatcher(scheduler, mmu, processes);
        System.out.println(".");
        cpu.run();
        System.out.println(".");


        //Process process0 = processList.stream().findFirst().get();

        // Sonuçları yazdır
        for (Process process : processes) {
            if(process.error=="bitti"){
                System.out.println(process.getPCB().getPid()+"id'li process tamamlandi. |Varis zamani -> " + process.getArrivalTime() + " |Oncelik -> " + process.priority + " |Bellek kullanimi -> " + process.getMemoryRequirements() + " |printer -> "+ process.printerRequirements +" |modem -> "+ process.modemRequirements +" |tarayici -> "+ process.tarayiciRequirements +" |cd -> "+ process.cdRequirements  );
                System.out.println();
            }else {
                System.out.println(process.getPCB().getPid() + "id'li process su sebepten dolayi bitti: ");
                System.out.println(process.error);
                System.out.println();
            }
        }
    }
}
