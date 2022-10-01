package mio68.lab.spring.app;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Mio68LabSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mio68LabSpringApplication.class, args);
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
