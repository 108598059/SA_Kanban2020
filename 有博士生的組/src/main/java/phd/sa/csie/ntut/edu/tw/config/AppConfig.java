package phd.sa.csie.ntut.edu.tw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.Arrays;

@Configuration
public class AppConfig {
    @Bean
    public CardRepository getCardRepository() {
        return new MysqlCardRepository();
    }

    @Bean
    public BoardRepository getBoardRepository() {
        return new MysqlBoardRepository();
    }

    @Bean
    public DomainEventBus getDomainEventBus() {
        return new DomainEventBus();
    }

    @Bean
    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(getBoardRepository());
    }

    @Bean
    public CreateCardUseCase getCreateCardUseCase() {
        return new CreateCardUseCase(getDomainEventBus());
    }

    @Bean
    public CommitCardUseCase getCommitUsecase() {
        DomainEventBus eventBus = getDomainEventBus();
        CommitCardUseCase commitCardUsecase = new CommitCardUseCase(getCardRepository(), getBoardRepository());
        eventBus.register(commitCardUsecase);
        return commitCardUsecase;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        //允許跨網域請求的來源
        config.addAllowedOrigin("*");

        //允許跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
        config.setAllowCredentials(true);

        //允許使用那些請求方式
        config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST","DELETE"));

        //允許哪些Header
        config.addAllowedHeader("*");

        //可獲取哪些Header（因為跨網域預設不能取得全部Header資訊）
        config.addExposedHeader("/*");

        //映射路徑
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //return一個的CorsFilter.
        return new CorsFilter(configSource);
    }
}
