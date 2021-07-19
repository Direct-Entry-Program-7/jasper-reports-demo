package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubjectMarks implements Serializable {
    private String subject;
    private BigDecimal marks;

    public SubjectMarks() {
    }

    public SubjectMarks(String subject, BigDecimal marks) {
        this.subject = subject;
        this.marks = marks;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "SubjectMarks{" +
                "subject='" + subject + '\'' +
                ", marks=" + marks +
                '}';
    }
}
