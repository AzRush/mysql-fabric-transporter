# mysql 日志解析传输系统
用于解析mysql binlog 并传输到fabric。
## 设计
* mysql command: insert update delete 实体类
* mysql phaser: mysql command 工厂
* 链码层: insert, delete 以及 支持增量的update方法
* java 链码调用
