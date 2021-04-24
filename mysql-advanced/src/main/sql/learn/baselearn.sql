-- like关键字
SELECT * FROM table1 WHERE `name` LIKE 'hom%';
SELECT * FROM table1 WHERE `name` NOT LIKE '%om%';
-- in关键字
SELECT * FROM table1 WHERE id IN (1,2);
SELECT * FROM table1 WHERE name IN ('Home');
-- BETWEEN 操作符在 WHERE 子句中使用，作用是选取介于两个值之间的数据范围。
SELECT * FROM table1 WHERE id BETWEEN 1 AND 3;
-- 通过使用 SQL，可以为列名称和表名称指定别名（Alias）。使用AS，AS可以省略
SELECT t.name FROM table1 t;
-- SQL join 用于根据两个或多个表中的列之间的关系，从这些表中查询数据。
-- 引用两个表
SELECT t.name tname,s.name sname FROM table1 t,student s WHERE t.id = s.table_id;
-- SQL JOIN - 使用 Join
SELECT t.name tname,s.name sname FROM table1 t INNER JOIN student s ON t.id = s.table_id ORDER BY t.id;
-- JOIN: 如果表中有至少一个匹配，则返回行;LEFT JOIN: 即使右表中没有匹配，也从左表返回所有的行;RIGHT JOIN: 即使左表中没有匹配，也从右表返回所有的行;FULL JOIN: 只要其中一个表中存在匹配，就返回行(Mysql不支持)
SELECT t.name tname,s.name sname FROM table1 t LEFT JOIN student s ON t.id = s.table_id ;
SELECT t.name tname,s.name sname FROM table1 t RIGHT JOIN student s ON t.id = s.table_id ;
-- SQL UNION 操作符
-- UNION 操作符用于合并两个或多个 SELECT 语句的结果集。请注意，UNION 内部的 SELECT 语句必须拥有相同数量的列。列也必须拥有相似的数据类型。同时，每条 SELECT 语句中的列的顺序必须相同。
SELECT name,id FROM table1
UNION
SELECT name,table_id FROM student;


-- SQL 约束
-- 约束用于限制加入表的数据的类型。可以在创建表时规定约束（通过 CREATE TABLE 语句），或者在表创建之后也可以（通过 ALTER TABLE 语句）。
-- SQL NOT NULL 约束:NOT NULL 约束强制列不接受 NULL 值。
-- SQL UNIQUE 约束:UNIQUE 约束唯一标识数据库表中的每条记录,为列或列集合提供了唯一性的保证。每个表可以有多个 UNIQUE 约束，但是每个表只能有一个 PRIMARY KEY 约束。
ALTER TABLE student ADD UNIQUE (table_id);
ALTER TABLE student DROP INDEX table_id;
-- SQL PRIMARY KEY 约束:PRIMARY KEY 约束唯一标识数据库表中的每条记录。主键必须包含唯一的值,主键列不能包含 NULL 值,每个表都应该有一个主键，并且每个表只能有一个主键
-- SQL FOREIGN KEY 约束:外键,一个表中的 FOREIGN KEY 指向另一个表中的 PRIMARY KEY。
-- SQL CHECK 约束:CHECK 约束用于限制列中的值的范围。如果对单个列定义 CHECK 约束，那么该列只允许特定的值;如果对一个表定义 CHECK 约束，那么此约束会在特定的列中对值进行限制。（Mysql数据库有暂有问题）
ALTER TABLE student ADD CHECK (table_id>0);
ALTER TABLE Persons DROP CHECK table_id;
-- SQL DEFAULT 约束：DEFAULT 约束用于向列中插入默认值。如果没有规定其他的值，那么会将默认值添加到所有的新记录。
-- SQL CREATE INDEX 语句：CREATE INDEX 语句用于在表中创建索引。在不读取整个表的情况下，索引使数据库应用程序可以更快地查找数据。
-- SQL CREATE INDEX:在表上创建一个简单的索引,允许使用重复的值
CREATE INDEX index_name ON student (name);
-- SQL CREATE UNIQUE INDEX:在表上创建一个唯一的索引。唯一的索引意味着两个行不能拥有相同的索引值。
CREATE UNIQUE INDEX index_tid ON student (table_id);
-- SQL 撤销索引、表以及数据库:通过使用 DROP 语句，可以轻松地删除索引、表和数据库。
-- SQL DROP INDEX 语句
ALTER TABLE student DROP INDEX index_tid;
-- SQL DROP TABLE 语句
-- SQL DROP DATABASE 语句
-- SQL TRUNCATE TABLE 语句:仅仅需要除去表内的数据，但并不删除表本身
-- SQL ALTER TABLE 语句:ALTER TABLE 语句用于在已有的表中添加、修改或删除列。
-- SQL AUTO INCREMENT 字段:Auto-increment 会在新记录插入表中时生成一个唯一的数字。
-- SQL Date 函数
-- SQL NULL 值:NULL 值是遗漏的未知数据,默认地，表的列可以存放 NULL 值。
-- SQL IS NULL：选取某列中带有 NULL 值的记录
-- SQL IS NOT NULL：选取某列中不带有 NULL 值的记录



-- SQL函数：SQL 拥有很多可用于计数和计算的内建函数。
-- SQL AVG 函数：AVG 函数返回数值列的平均值。NULL 值不包括在计算中。
SELECT AVG(age) AS ageAverage FROM student;
-- SQL COUNT() 函数：COUNT() 函数返回匹配指定条件的行数。
SELECT COUNT(*) FROM student;
-- SQL MAX() 函数：MAX 函数返回一列中的最大值。NULL 值不包括在计算中。
SELECT MAX(age) FROM student;
SELECT MIN(age) FROM student;
-- SQL SUM() 函数:SUM 函数返回数值列的总数（总额）。
SELECT SUM(age) FROM student;
-- SQL GROUP BY 语句:合计函数 (比如 SUM) 常常需要添加 GROUP BY 语句。
SELECT table_id,SUM(age) FROM student GROUP BY table_id;
-- SQL HAVING 子句:在 SQL 中增加 HAVING 子句原因是，WHERE 关键字无法与合计函数一起使用。
SELECT table_id,SUM(age) FROM student GROUP BY table_id HAVING SUM(age)<30;
-- SQL UCASE() 函数:UCASE 函数把字段的值转换为大写。
SELECT UCASE(name) FROM student;
-- SQL LCASE() 函数:LCASE 函数把字段的值转换为小写。
SELECT LCASE(name) FROM student;
-- SQL MID() 函数:MID 函数用于从文本字段中提取字符。
SELECT MID(name,1,3) as smallName FROM student;
-- SQL LENGTH() 函数:LENGTH 函数返回文本字段中值的长度。
SELECT LENGTH(name) as LengthOfName FROM student;
-- SQL ROUND() 函数:ROUND 函数用于把数值字段舍入为指定的小数位数。
SELECT name, ROUND(age,0) as age FROM student;
-- SQL NOW() 函数:NOW 函数返回当前的日期和时间。
SELECT NOW() time,name,age FROM student;
-- SQL FORMAT() 函数:FORMAT 函数用于对字段的显示进行格式化。
SELECT name, age, FORMAT(Now(),'YYYY/MM/DD hh:mm:ss') as time FROM student;



