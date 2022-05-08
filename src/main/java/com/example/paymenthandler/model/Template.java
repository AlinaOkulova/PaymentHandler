package com.example.paymenthandler.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "communal_payments", name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "template_name")
    private String name;

    @Column(name = "iban")
    private String iban;

    @Column(name = "purpose_of_payment")
    private String purposeOfPayment;
}
