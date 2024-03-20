package mio68.lab.spring.profiling.service.perf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceA {

    private final ServiceB serviceB;
    private final ServiceC serviceC;

    public String getReport() {
        for (long i = 0; i < 300_000L; i++) {

        }
        return serviceB.getReport() + " " + serviceC.getReport();
    }

}
