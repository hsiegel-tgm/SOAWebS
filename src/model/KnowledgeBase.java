package model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Clob;

/**
 * The KnowledgeBase itself
 */

@NamedQuery(
        name="searchKnowledgeBase",
        query="FROM KnowledgeBase kb WHERE kb.text LIKE :searchsting"
)
@Entity
public class KnowledgeBase {
    @Id
    @GeneratedValue
    private Long ID;

    @Column(columnDefinition="LONGTEXT")
    @Size(min=10, max=50000)
    private String text;

    @Column(unique=true)
    @Size(min=10, max=50)
    private String topic;

    /**
     * The constructor of the class, sets the topic and the text
     *
     * @param text
     * @param topic
     */
    public KnowledgeBase(String topic,String text) {
        this.text = text;
        this.topic = topic;
    }

    public KnowledgeBase() {
        this.text = "bla";
        this.topic = "blablabla";
    }

    /**
     * generated Getter and Setter
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getID() {
        return ID;
    }

}
