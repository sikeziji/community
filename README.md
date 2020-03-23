# community


`创建User表的sql语句`
```sql
CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN VARCHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
);
);
```

`数据库名称更改为 communities`



```bash
mvn flyway:migrate
// 您可以使用标准Maven命令行属性将参数传递给目标
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
