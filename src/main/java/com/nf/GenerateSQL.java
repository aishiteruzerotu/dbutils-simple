package com.nf;

public interface GenerateSQL {
    /**
     * 给一个实体对象参数，返回该实体的SQL插入语句
     * @param t 实体对象
     * @return 返回一个SQL增加语句
     */
    public <T> String generateInsert(T t);

    /**
     * 给一个类对象 返回该对象的sql查询语句
     * @param clz 类对象
     * @return 数据库 查询语句
     */
    public String generateSelect(Class<?> clz);

    /**
     * 给一个类对象 返回该对象的sql删除语句
     * @param clz 类对象
     * @return 数据库 删除语句
     */
    public String generateDelete(Class<?> clz);
}
