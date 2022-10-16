package mio68.lab.spring.webapp.secureapp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("mio68.lab.spring.webapp.common")
public class SecureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureApplication.class, args);
	}

	// После запуска приложения Spring выполним некоторые действия
	// В данном примере печатаем имена всех beans.
	@Component
	public static class ApplicationContextReporter implements ApplicationRunner {

		private final ApplicationContext applicationContext;

		public ApplicationContextReporter(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		@Override
		public void run(ApplicationArguments args) {
			System.out.println("Bean definitions count: " +
					applicationContext.getBeanDefinitionCount());
			System.out.println("Bean definition names: ");
			for (String beanName : applicationContext.getBeanDefinitionNames()) {
				System.out.println(beanName);
			}
		}
	}

}
