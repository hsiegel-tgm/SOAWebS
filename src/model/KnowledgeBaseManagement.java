package model;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class KnowledgeBaseManagement extends PerformActionOnDatabase{

    public KnowledgeBaseManagement(){
        super();
    }

    public void delete(){
        // deleting all
        Tag tag = new Tag();
        this.deleteAll(tag);
        KnowledgeBase kb = new KnowledgeBase();
        this.deleteAll(kb);
    }

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

    public void onlyTags(int tags, String keyname){
        this.generateTags(tags,keyname);
        System.out.println("Generated Tags...");
    }

    public void onlyEntries(int inserts){
        this.generateEntries(inserts);
        System.out.println("Generated Entries...");
    }

    public void onlyKKb(){
        this.generateTagKnowledgeBase();
        System.out.println("Generated Relationships...");
    }

    private void generateTags(int number, String prefix){
        //opening Session and Transaction
        Session s = super.m_sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        Tag tag;

        for(int i = 0; i < number; ++i) {
            tag = new Tag(prefix+(i+1));
            s.saveOrUpdate(tag);
        }

        tx.commit();
        s.flush();
        s.close();
    }

    private void generateEntries(int number){
        String[] words = {"Hannah","play","Transaction","Diplomaproject","Integration","blablabla","Length","English","Vennesa","Matura","A-levels","juhu","great","Preparation","Word","Hibernate","SOA","is","great","Martin","programming","important","Blackbord","School", "bag","Schoe","Headset"};
        this.generateEntries(number,3,20,words);
    }

    private void generateTagKnowledgeBase(){
        Session s = super.m_sessionFactory.openSession();
        Transaction tx = null;
        tx = s.beginTransaction();

        // create query
        org.hibernate.Query q = s.getNamedQuery(new KnowledgeBase().getAllQuery());

        // run query and fetch reslut
        List<KnowledgeBase> res = q.list();

        q = s.getNamedQuery(new Tag().getAllQuery());
        List<Tag> tags = q.list();

        Iterator<Tag> iterator = tags.listIterator();

        for(KnowledgeBase kb : res){
            if(!iterator.hasNext()){
                iterator = tags.listIterator();
            }
            iterator.next().addKnowledgeBase(kb);
        }

        tx.commit();
        s.flush();
        s.close();
    }

    private void generateEntries(int number, int length1, int length2, String[]words) {
        //opening Session and Transaction
        Session s = super.m_sessionFactory.openSession();

        Transaction tx = null;
        tx = s.beginTransaction();

        KnowledgeBase kb;

        StringBuilder topic;
        StringBuilder text;

        Random random = new Random();



        for(int i = 0; i < number; ++i){
            topic = new StringBuilder();

            for (int j = 0; j < length1; j++) {
                topic.append(words[random.nextInt(words.length)]+ " ");
            }
            // topic.append(i);

            text = new StringBuilder();

            for (int j = 0; j < length2; j++) {
                text.append(words[random.nextInt(words.length)]+ " ");
            }

            kb = new KnowledgeBase(topic.toString(),text.toString());


            s.saveOrUpdate(kb);
        }

        tx.commit();
        s.flush();

        s.close();
    }
}
