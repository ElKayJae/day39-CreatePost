package day39.server.configs;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfig {

    private Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Value ("${SPACES_ACCESS}")
    private String spacesAccess;

    @Value ("${SPACES_SECRET}")
    private String spacesSecret;

    @Bean
    public AmazonS3 createS3Client(){
        BasicAWSCredentials cred = new BasicAWSCredentials(spacesAccess, spacesSecret);
        EndpointConfiguration epConfig = new EndpointConfiguration ("sgp1.digitaloceanspaces.com", "sgp1");
        return AmazonS3ClientBuilder.standard().withEndpointConfiguration(epConfig).withCredentials(new AWSStaticCredentialsProvider(cred)).build();
    }

    public static final String REDIS_LIKE = "redis-like";

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.user}")
    private String redisUser;

    @Value("${spring.redis.password}")
    private String redisPassword;

    
    @Bean(name = REDIS_LIKE )
    @Scope("singleton")
    public RedisTemplate<String, String> redisTemplate() {
        //set up the configuration into RedisStandaloneConfiguration object
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setPassword(redisPassword);
        config.setUsername(redisUser);
        config.setDatabase(redisDatabase);
        
        //Jedis is java client for redis, create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.log(Level.INFO, "redis host port > %s %d".formatted(redisHost, redisPort) );
        
        //create template
        final RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(jedisFac);

        //all the serializers
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}
