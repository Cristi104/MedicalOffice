import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        try {
            service.menu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}