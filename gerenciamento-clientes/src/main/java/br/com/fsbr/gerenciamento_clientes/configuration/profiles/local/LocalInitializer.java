package br.com.fsbr.gerenciamento_clientes.configuration.profiles.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LocalInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        logger.info("- - - - - Initialize Local Profile - - - - - ");

    }

}
