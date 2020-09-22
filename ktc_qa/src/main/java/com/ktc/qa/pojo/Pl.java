package com.ktc.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author XSS
 * @date 2020/8/19
 * @desc
 */
@Entity
@Table(name = "tb_pl")
@IdClass(Pl.class) // 联合主键所在类
public class Pl implements Serializable {

    @Id
    private String problemid;

    @Id
    private String labelid;


    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }

}
