package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Vendor {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    public Vendor() {
    }

    public Vendor(String name) {
        this.name = name;
    }

    public Vendor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}