package mio68.lab.spring.profiling.service.perf;

import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    public String getReport() {
        for (long i = 0; i < 1_000_000L; i++) {

        }
        return "Report B";
    }

}
