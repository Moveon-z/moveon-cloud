package com.moveon.cloud.processor;

import com.moveon.cloud.annotation.table.Table;
import com.moveon.cloud.entity.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TableFieldAnnotationProcessor
 * @Description TODO
 * @Author huangzh
 * @Date 2025/9/2 15:16
 * @Version 1.0
 */
@Component
public class TableFieldAnnotationProcessor implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        // 获取所有带有@Table注解的类
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Table.class));

        // 扫描指定包下带有@Table注解的类
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("com.moveon.cloud.entity");

        // 连接数据库获取元数据
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Class<?> entityClass = Class.forName(beanDefinition.getBeanClassName());

                // 检查是否有Table注解
                if (entityClass.isAnnotationPresent(Table.class)) {
                    Table annotation = entityClass.getAnnotation(Table.class);
                    String tableName = annotation.value();

                    // 获取数据库表字段
                    Map<String, Class> dbColumns = new HashMap<>();
                    ResultSet columns = metaData.getColumns(null, null, tableName.toUpperCase(), null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
                        if ("VARCHAR".equals(columnType)) {
                            dbColumns.put(columnName.toLowerCase(), String.class);
                        } else if ("INT".equals(columnType)) {
                            dbColumns.put(columnName.toLowerCase(), Long.class);
                        } else if ("DATETIME".equals(columnType)) {
                            dbColumns.put(columnName.toLowerCase(), Date.class);
                        }
                    }
                    Field field = entityClass.getDeclaredField("tableFieldMap");
                    field.setAccessible(true);
                    field.set(null, dbColumns);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
