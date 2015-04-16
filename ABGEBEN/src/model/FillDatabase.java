package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.QueryImpl;

import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by hsiegel on 2015-04-06.
 */
public class FillDatabase {
    private SessionFactory m_sessionFactory;

    public FillDatabase(){
        m_sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static void main(String arg[]){
        long startTime = System.currentTimeMillis();
        System.out.println("Starting ...");
        new FillDatabase().fillDatabase("KnowledgeBase",100,3,25);
        //SessionFactory m_sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        //new FillDatabase().fillDatabase(m_sessionFactory,"KnowledgeBase",100,2,30);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("it took: "+estimatedTime +" milliseconds");
        System.out.println("it took: "+estimatedTime/1000 +" seconds");
        System.out.println("it took: "+(estimatedTime/1000)/60 +" minutes");


    }


    public boolean fillDatabase(SessionFactory sf,String table,int number,int length1, int length2){
        boolean ret = true;
        m_sessionFactory = sf;

        System.out.println("Starting ...... ");

        if(deleteAllEntries(table))
            ret = generateEntries(number,table,length1,length2);
        else
            ret = false;

        // m_sessionFactory.close();

        return ret;
    }


    public boolean fillDatabase(/*SessionFactory sf,*/String table,int number,int length1, int length2){
        boolean ret = true;
        //m_sessionFactory = sf;

        System.out.println("Starting ...... ");

        if(deleteAllEntries(table))
            ret = generateEntries(number,table,length1,length2);
            //ret = true;
        else
            ret = false;

        m_sessionFactory.close();

        return ret;
    }




    private boolean deleteAllEntries(String table){
        System.out.println("Starting .......... ");

        boolean ret = true;
        try {
            //opening Session and Transaction
            Session s = m_sessionFactory.openSession();
            // System.out.println(m_sessionFactory.isClosed());
            Transaction t = s.beginTransaction();
            t.begin();
            String hql = String.format("delete from %s", table);
            org.hibernate.Query query = s.createQuery(hql);
            System.out.println("Starting ............. ");

            query.executeUpdate();
            t.commit();
            s.flush();
            //t.commit();

        }catch(Exception e){
            //TODO basic logging
            ret = false;
        }

        System.out.println("done deleting...");

        return ret;

    }
    private boolean generateEntries(int number,String table, int length1, int length2) {
        boolean ret = true;

        //opening Session and Transaction
        Session s = m_sessionFactory.openSession();

        Transaction tx = null;
        tx = s.beginTransaction();




        // Transaction t = s.beginTransaction();
        // t.begin();
        KnowledgeBase kb;

        String[] words = {"Hannah","spielen","Transaktion",".",",","!","Diplomproject","Integration","blablabla","Laenge","Englisch","Vennesa","Matura","juhu","tolle","Vorbereitung","Wort","Hibernate","SOA","ist","super","Martin","programmieren","wichtig","Tafel","Schule", "Tasche","Schuh","Kopfhoerer"};

        StringBuilder topic;
        StringBuilder text;

        Random random = new Random();

        for(int i = 0; i < number; ++i){
            topic = new StringBuilder();

            for (int j = 0; j < length1; j++) {
                topic.append(words[random.nextInt(words.length)]+ " ");
            }
            topic.append(i);

            text = new StringBuilder();

            for (int j = 0; j < length2; j++) {
                text.append(words[random.nextInt(words.length)]+ " ");
            }

            kb = new KnowledgeBase(topic.toString(),text.toString());
            // ifValidationThenSave(kb, s);
            s.saveOrUpdate(kb);
        }

        // t.commit();
        tx.commit();
        s.flush();
        //tx.commit();

        s.close();

        return ret;
    }




    /**
     * The method ifValidationThenSave validates an Object to its Validation
     * Beans. If the Object has any validation constraints, they will be written
     * onto the screen. Otherwise the Object is going to be saved.
     *
     * @param validationObject
     * @param session
     */
    public void ifValidationThenSave(Object validationObject, Session session) {
        // new Validator Object
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // getting the constraint Violations
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(validationObject);

        // if the validation was negative
        if (constraintViolations.size() != 0) {
            // writing constraint violation message(s)
            System.out.println("There was a Constraint Violation:");
            Iterator<ConstraintViolation<Object>> iterator = constraintViolations
                    .iterator();
            for (int i = 0; i < constraintViolations.size(); ++i) {
                System.out.println(iterator.next().getMessage());
            }
        }
        // if everything went right
        else {
            // saving object
            session.save(validationObject);
        }
    }
}
