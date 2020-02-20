package eu.treative.flightsintospace;

import eu.treative.flightsintospace.model.Flight;
import eu.treative.flightsintospace.model.Gender;
import eu.treative.flightsintospace.model.Tourist;
import eu.treative.flightsintospace.repository.FlightRepository;
import eu.treative.flightsintospace.repository.TouristRepository;
import eu.treative.flightsintospace.service.FlightService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class FlightsIntoSpaceApplication {

    private static FlightRepository flightRepository;
    private static TouristRepository touristRepository;

    public FlightsIntoSpaceApplication(FlightRepository flightRepository, TouristRepository touristRepository) {
        FlightsIntoSpaceApplication.flightRepository = flightRepository;
        FlightsIntoSpaceApplication.touristRepository = touristRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlightsIntoSpaceApplication.class, args);

        List<Tourist> tourists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Tourist tourist = new Tourist("Jan", "Kowalski", Gender.MEN, "Poland", "Love space", randomBirth(), genPseudoPesel());
            tourists.add(tourist);
            touristRepository.save(tourist);
        }

        for (int i = 0; i < 1000; i++) {
            List<Tourist> flightTourists = new ArrayList<>();
            int touristNum = touristNum();
            for (int j = 0; j < touristNum; j++) {
                flightTourists.add(tourists.get(new Random().nextInt(100)));
            }
            Flight flight = new Flight(random(), random().plusDays(1), 10, touristNum, "100", randomCode(), flightTourists);
            flightRepository.save(flight);
        }
    }

    public static String genPseudoPesel() {
        Random random = new Random();
        char[] digits = new char[12];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < 12; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

    static public LocalDateTime random() {
        Random random = new Random();
        LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        LocalDate randomDate = start.plusDays(random.nextInt((int) days + 1));
        return LocalDateTime.of(randomDate,
                LocalTime.of(random.nextInt(24), random.nextInt(60),
                        random.nextInt(60), random.nextInt(999999999 + 1)));
    }

    public static LocalDate randomBirth(){
        LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        return start.plusDays(new Random().nextInt((int) days + 1));
    }

    static public Integer touristNum() {
        Random random = new Random();
        Integer touristNumber;
        touristNumber = random.nextInt(10);
        return touristNumber;
    }

    static public String randomCode() {
        byte[] array = new byte[5];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
