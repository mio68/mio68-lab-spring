package mio68.lab.spring.jpa.timefield;

import mio68.lab.spring.jpa.timefield.entity.Account2;
import mio68.lab.spring.jpa.timefield.repository.Account2Repository;
import mio68.lab.spring.jpa.timefield.repository.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TimeFieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeFieldApplication.class, args);
    }

    @Component
    public static class TimeFieldApplicationDemo implements ApplicationRunner {

        private final AccountRepository accountRepository;
        private final Account2Repository accountRepository2;

        public TimeFieldApplicationDemo(
                AccountRepository accountRepository,
                Account2Repository accountRepository2) {

            this.accountRepository = accountRepository;
            this.accountRepository2 = accountRepository2;
        }


        @Override
        public void run(ApplicationArguments args) throws Exception {
//            Account account = new Account();
//            accountRepository.save(account);
//            System.out.println("saved account: " + account);
//            Account accountFound = accountRepository.findById(account.getId()).get();
//            System.out.println("found by id: " + accountFound);
//
//            accountFound = accountRepository.findById(78L).get();
//            System.out.println("found by id: " + accountFound);

            Account2 account2= accountRepository2.findById(78L).get();
            System.out.println("found by id: " + account2);

        }
    }

}
