package phd.sa.csie.ntut.edu.tw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

@Configuration
public class AppConfig {
    @Bean
    public CardRepository getCardRepository(){
        return new MemoryCardRepository();
    }

    @Bean
    public DomainEventBus getDomainEventBus(){
        return new DomainEventBus();
    }

    @Bean
    public CardDTOConverter getCardDTOConverter(){
        return new CardDTOConverter();
    }
}
