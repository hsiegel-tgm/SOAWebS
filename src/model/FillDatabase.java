package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
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
        new FillDatabase().fillDatabase();
    }

    public void fillDatabase(){
        //opening Session and Transaction
        Session s = m_sessionFactory.openSession();
        System.out.println(m_sessionFactory.isClosed());
        Transaction t = s.beginTransaction();
        t.begin();


        KnowledgeBase kb = new KnowledgeBase("texttexttexttexttexttexttexttext","topictopictopic");


        ifValidationThenSave(kb, s);

        t.commit();
        s.flush();
        m_sessionFactory.close();
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
