package model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * The KnowledgeBase itself
 *
 * @author Hannah Siegel
 * @version 0.1
 */
//Named Queries (select all and select by string)
@NamedQueries({@NamedQuery(
        name="searchKnowledgeBase",
        query="FROM KnowledgeBase kb WHERE kb.text LIKE :searchsting"
),
@NamedQuery(
        name="selectAllKnowledgeBase",
        query="FROM KnowledgeBase "
)})
@Entity
public class KnowledgeBase implements ManageableTable {
    @Id
    @GeneratedValue
    private Long ID;

    @Column(columnDefinition="LONGTEXT")
    @Size(min=10, max=50000)
    private String text;

    @Column
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

    /**
     * The constructor of the class, sets default values
     */
    public KnowledgeBase() {
        this.text = "texttexttexttexttexttexttexttexttexttext";
        this.topic = "topictopictopic ";
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

    public String toString(){
        return "ID:" + this.getID() + "\n     Topic: "   + this.getTopic() +"\n     Text: "+this.getText()+"\n";
    }

    @Override
    public String getAllQuery() {
        return "selectAllKnowledgeBase";
    }

    @Override
    public String getName() {
        return "KnowledgeBase";
    }
}
