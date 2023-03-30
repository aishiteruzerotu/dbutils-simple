#   1、资源清理工具

​	ResourceCleanerUtils.java 

#	2、创建SqlExecutor执行工具

主要完成两个方法

		1. int update	增删改操作  返回被修改的行
  		2. T query		 查询操作	 返回一个泛型对象 T



#	3、支持DataSource

​	调用者自己生成的数据库连接应该由调用者自己来关闭，我们生成的数据库连接应该由我们自己关闭

##	3.1改造Connection获取逻辑

​	可以让用户能扩展获取连接的功能

##	3.2添加父类 AbstractSqlExecutor