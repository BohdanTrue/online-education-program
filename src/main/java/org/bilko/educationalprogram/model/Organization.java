package org.bilko.educationalprogram.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE organizations SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
//    private List<Program> programs;

    @Column(nullable = false)
    private boolean isDeleted = false;
}
