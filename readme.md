# mysql to fabric 日志解析传输工具
用于解析mysql binlog 并传输到fabric。
## 各个类的功能
* mysql command: insert update delete 实体类
* mysql parser: mysql command 工厂,解析并生成三种类型的command，现在只能解析单一语法
* fabric connector: 链码调用接口
* 链码层: insert, delete 以及支持增量的update方法
## todo
* command类toString,getWhereMap, getParameterMap类的实现
* fabric connector 三种方法包装。
## 使用
* 开启mysql binlog,格式为statement。 vim my.ini ->在[mysqld]下添加 log-bin=mysql-bin binlog-format="STATEMENT"
* 在同步之前连接mysql,执行 flush logs;
* 修改Main中binlog路径,执行main函数


