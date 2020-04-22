package cn.dblearn.blog.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 解决localDateTime无法被jackson全局格式化
 *
 * @author: chen-zicong
 * @create: 2019-10-31 14:17
 **/
@Configuration
@Data
public class DateFormatConfig {

  /**
   * Date格式化字符串
   */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  /**
   * DateTime格式化字符串
   */
  public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  /**
   * Time格式化字符串
   */
  public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  public static final String C1_TIME_FORMAT = "HH:mm";

  /**
   * LocalDate转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  public Converter<String, LocalDate> localDateConverter() {

    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
      }
    };
  }

  /**
   * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  public Converter<String, LocalDateTime> localDateTimeConverter() {

    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
      }
    };
  }

  /**
   * LocalTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  public Converter<String, LocalTime> localTimeConverter() {

    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(String source) {
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
      }
    };
  }


  /**
   * 自定义Bean
   *
   * @return
   */
  @Bean
  @Primary
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    return builder -> builder
        .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
        .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
        .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
        .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
        .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
        .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
  }


}
