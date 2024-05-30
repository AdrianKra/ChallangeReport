package blossom.reports_service;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
    /**
     * The following bean declaration is required to make micrometer tracing
     * work with feign clients. This may change in the future.
     * See <a href=
     * "https://www.appsdeveloperblog.com/micrometer-and-zipkin-in-spring-boot/">...</a>
     */
    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
