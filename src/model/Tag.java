package model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The Tag model
 *
 * @author Hannah Siegel
 * @version 0.3
 */
//Named Queries (select all and select by tagname)
@Entity
@NamedQueries({@NamedQuery(
        name="selectTags",
        query="FROM Tag"
),
        @NamedQuery(
                name="searchTag",
                query="FROM Tag WHERE tagname=:searchstring"
        )})
public class Tag implements ManageableTable{
    @Id
    @GeneratedValue
    private Long ID;

    @Column
    @Size(min=9, max=11)
    private String tagname;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    List<KnowledgeBase> knowledgebases;

    public Tag(){
    }

    public Tag(String tagname){
        this.tagname = tagname;
        this.knowledgebases = new ArrayList<>();
    }

    public void addKnowledgeBase(KnowledgeBase kb){
        this.knowledgebases.add(kb);
    }

    public List<KnowledgeBase> getKnowledgebases() {
        return knowledgebases;
    }

    public void setKnowledgebases(List<KnowledgeBase> knowledgebases) {
        this.knowledgebases = knowledgebases;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public String getAllQuery() {
        return "selectTags";
    }

    @Override
    public String getName() {
        return "Tag";
    }
}
