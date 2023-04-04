#   1、资源清理工具

​	ResourceCleanerUtils.java 

#	2、创建SqlExecutor执行工具

主要完成两个方法

1. int update	增删改操作  返回被修改的行
2. T query		 查询操作	 返回一个泛型对象 T
3. int insertObject 通过对象增加一行对应的数据 返回插入的行数
4. T queryBean 查询操作     返回一个泛型对象 T //该方法只能返回一个实体类
5. List<T> queryBeanList   查询操作 返回泛型对象序列  //该方法只返回泛型对象序列



#	3、支持DataSource

​	调用者自己生成的数据库连接应该由调用者自己来关闭，我们生成的数据库连接应该由我们自己关闭

##	3.1改造Connection获取逻辑

​	可以让用户能扩展获取连接的功能

##	3.2添加父类 AbstractSqlExecutor

因为getDataSource，prepareConnection这2个方法，源码中都说要留着给子类重写用，基于这个逻辑，就决定创建父类出来

这样，框架的用户在不满意我们框架的功能的时候，他可以选择重写SqlExecutor或者重写AbstractSqlExecutor

#	4、实现接口ResultSetHandler

​	此接口方法用于处理数据库查询结果集

##	4.1ArrayHandler

​	该方法返回一个 Object数组

##	4.2ArrayListHandler

​	该方法返回一个List序列的Object数组集合对象

##	4.3ScalarHandler

​	返回一行一列的数据

## 4.4Map<String,Object>

​	MapHandler ：返回以一个Map对象

​	MapListHandler ：返回一个List序列的Map合集对象

##	4.5Bean

​	BeanHandler ：返回以一个Bean对象

​	BeanListHandler ：返回一个List序列的Bean合集对象









