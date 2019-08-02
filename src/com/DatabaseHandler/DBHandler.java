package com.DatabaseHandler;

import java.io.File;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
 
import com.dailybagel.article.resources.Article;
import com.dailybagel.user.resources.User;

public class DBHandler
{
    private static SessionFactory sessionFactory;
    
    static {
    	try { resetConnection(); } 
    	
    	catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory resetConnection() {
		Properties prop= getProps();
         return sessionFactory = new AnnotationConfiguration()
        				.addPackage("com.dailybagel.user.resources")
        				.addPackage("com.dailybagel.article.resources")
        				.addAnnotatedClass(User.class)
        				.addAnnotatedClass(Article.class)
                        .addProperties(prop)                  
                        .buildSessionFactory();

    }
    
    private static Properties getProps() {
    	Properties prop = new Properties();
    	prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_d80dc7ab129a7ce?reconnect=true");
        prop.setProperty("hibernate.connection.username", "baaa95d23db726");
        prop.setProperty("hibernate.connection.password", "de7ce257");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        prop.setProperty("connection.autoReconnect", "true");
        prop.setProperty("connection.autoReconnectForPools", "true");
        prop.setProperty("connection.is-connection-validation-required","true");
        prop.setProperty("hibernate.c3p0.min_size", "5");
        prop.setProperty("hibernate.c3p0.max_size", "50");
        prop.setProperty("hibernate.c3p0.timeout", "1800");
        //prop.setProperty("hibernate.c3p0.max_statements","5");
        prop.setProperty("hibernate.c3p0.idle_test_period","100");
        return prop;
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void testSession() {
    	if(sessionFactory.isClosed()) {
    		resetConnection();
    	}
    }
    
    public static void shutdown() {
        getSessionFactory().close();
    }
}