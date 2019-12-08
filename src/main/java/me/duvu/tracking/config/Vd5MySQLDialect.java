package me.duvu.tracking.config;

/**
 * @author beou on 9/5/18 15:04
 */
public class Vd5MySQLDialect extends org.hibernate.dialect.MySQL8Dialect {

    public Vd5MySQLDialect() {
        super();
        //https://www.objectdb.com/forum/822
    }
}
