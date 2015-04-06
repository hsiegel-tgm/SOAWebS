package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * The KnowledgeBase itself
 */
@Entity
public class KnowledgeBase {
    @Id
    @GeneratedValue
    private Long ID;

    @Column(unique=true)
    @Size(min=10, max=500)
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
    public KnowledgeBase(String text, String topic) {
        this.text = text;
        this.topic = topic;
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
}
