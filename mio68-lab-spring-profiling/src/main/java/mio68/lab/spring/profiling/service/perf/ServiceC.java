package mio68.lab.spring.profiling.service.perf;

import org.springframework.stereotype.Service;

@Service
public class ServiceC {

    public String getReport() {
        for (long i = 0; i < 100_000L; i++) {

        }
        return "Report C";
    }



}
