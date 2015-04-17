package model;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The class KnowledgeBaseManagement is inserting the data
 *
 * @author Hannah Siegel
 * @version 0.2
 */
public class KnowledgeBaseManagement extends PerformActionOnDatabase{
    /**
     * Constrcutor, calls super constructor who opens session
     */
    public KnowledgeBaseManagement(){
        super();
    }

    /**
     * delete method for Tag and KnowledgeBase
     */
    public void delete(){
        // deleting all
        Tag tag = new Tag();
        this.deleteAll(tag);
        KnowledgeBase kb = new KnowledgeBase();
        this.deleteAll(kb);
    }

    /**
     * The method calls the right methods for a complete setup:
     * deletes old entries
     * Generates n (defined by tags) Tags with the prefix of the keyname parameter
     * Generates n (defined by inserts) inserts
     * Generates Random Tags for inserts
     *
     * @param inserts - number of entries
     * @param tags - number of tags
     * @param keyname - prefix of Tag
     */
    public void all(int inserts, int tags, String keyname) {
        this.delete();
        System.out.println("Deleted Entries...");
        this.generateTags(tags,keyname);
        System.out.println("Generated Tags...");
        this.generateEntries(inserts);
        System.out.println("Generated Entries...");
        this.generateTagKnowledgeBase();
        System.out.println("Generated Relationships...");
    }

    /**
     * The method onlyTags the right methods for a Tag generation
     * Generates n (defined by tags) Tags with the prefix of the keyname parameter
     *
     * @param tags - number of tags
     * @param keyname - prefix of Tag
     */
    public void onlyTags(int tags, String keyname){
        this.generateTags(tags,keyname);
        System.out.println("Generated Tags...");
    }

    /**
     * The method onlyTags the right methods for a Entry generation
     * Generates n (defined by inserts) inserts
     *
     * @param inserts - number of entries
     */
    public void onlyEntries(int inserts){
        this.generateEntries(inserts);
        System.out.println("Generated Entries...");
    }

    /**
     * The method calls the right methods for random Tags for entries
     */
    public void onlyKKb(){
        this.generateTagKnowledgeBase();
        System.out.println("Generated Relationships...");
    }

    /**
     * The method generateTags is only saving Tags
     *
     * @param number - number of tags
     * @param prefix - prefix of the tag
     */
    private void generateTags(int number, String prefix){
        //opening Session and Transaction
        Session s = super.m_sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        Tag tag;

        // Generate and save Tags
        for(int i = 0; i < number; ++i) {
            tag = new Tag(prefix+(i+1));
            s.saveOrUpdate(tag);
        }

        // commit and close
        tx.commit();
        s.flush();
        s.close();
    }

    /**
     * The method generateTagKnowledgeBase is generating 1 random Tag for every KnowledgeBase
     */
    private void generateTagKnowledgeBase(){
        //open Session and Transaction
        Session s = super.m_sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        // create query for all KnowledgeBase objects
        org.hibernate.Query q = s.getNamedQuery(new KnowledgeBase().getAllQuery());

        // run query and fetch reslut
        List<KnowledgeBase> res = q.list();

        // create and run query for all Tag objects
        q = s.getNamedQuery(new Tag().getAllQuery());
        List<Tag> tags = q.list();
        Iterator<Tag> iterator = tags.listIterator();

        // matching KnowledgeBase and Tag
        for(KnowledgeBase kb : res){
            if(!iterator.hasNext()){
                iterator = tags.listIterator();
            }
            iterator.next().addKnowledgeBase(kb);
        }

        // commit and save
        tx.commit();
        s.flush();
        s.close();
    }

    /**
     * The method generateEntries abstracts the use of the more complicated one.
     * @param number - number of entries to be generated
     */
    private void generateEntries(int number){
        // Words
        String[] words = {"Hannah","play","Transaction","Diplomaproject","Integration","blablabla","Length","English","Vennesa","Matura","A-levels","juhu","great","Preparation","Word","Hibernate","SOA","is","great","Martin","programming","important","Blackbord","School", "bag","Schoe","Headset"};
        // number Entries are generated with 3 Words as Topic and 20 words as base. the words from the words array are used
        this.generateEntries(number,3,20,words);
    }

    /**
     * The method generateEntries is generating KnowledgeBase entries
     *
     * @param number - number of entries
     * @param length1 - topic length
     * @param length2 - text length
     * @param words - words that can be used
     */
    private void generateEntries(int number, int length1, int length2, String[]words) {
        //opening Session and Transaction
        Session s = super.m_sessionFactory.openSession();
        Transaction tx = null;
        tx = s.beginTransaction();

        KnowledgeBase kb;

        StringBuilder topic;
        StringBuilder text;

        Random random = new Random();

        // looping and generating
        for(int i = 0; i < number; ++i){
            // build Topic
            topic = new StringBuilder();
            for (int j = 0; j < length1; j++) {
                topic.append(words[random.nextInt(words.length)]+ " ");
            }

            // build text
            text = new StringBuilder();
            for (int j = 0; j < length2; j++) {
                text.append(words[random.nextInt(words.length)]+ " ");
            }

            // generate KnowledgeBase
            kb = new KnowledgeBase(topic.toString(),text.toString());

            // save KnowledgeBase
            s.saveOrUpdate(kb);
        }

        // commit and flush
        tx.commit();
        s.flush();
        s.close();
    }
}
