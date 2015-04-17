package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The abstract class PerformActionOnDatabase is holding all general Database stuff
 *
 * @author Hannah Siegel
 * @version 0.2
 */
public abstract class PerformActionOnDatabase {
    protected SessionFactory m_sessionFactory;

    /**
     * Constructor. Generates Session Factory
     */
    public PerformActionOnDatabase(){
        m_sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    /**
     * Delte all from one ManageableTable.
     *
     * @param table - Table object
     */
    public void deleteAll(ManageableTable table){
        Session s = null;
        Transaction t = null;
        try {
            //opening Session and Transaction
            s = m_sessionFactory.openSession();
            t = s.beginTransaction();
            t.begin();

            // create query
            org.hibernate.Query q = s.getNamedQuery(table.getAllQuery());

            // run query and fetch reslut
            List<?> res = q.list();

            //delete all
            for(Object entry: res){
                s.delete(entry);
            }
        }catch(Exception e){
            System.out.println("There was a problem when deleting "+table.getName());
        }finally{
            // commit
            t.commit();
            s.flush();
            s.close();
        }
    }

    /**
     * The method ifValidationThenSave validates an Object to its Validation
     * Beans. If the Object has any validation constraints, they will be written
     * onto the screen. Otherwise the Object is going to be saved.
     *
     * @param validationObject
     * @param session
     */
    protected void ifValidationThenSave(Object validationObject, Session session) {
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
