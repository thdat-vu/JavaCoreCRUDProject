/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business_object;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author LENOVO
 */
public class Flower implements Comparable<Flower>{
    private String id;
    private String description;
    private LocalDate importDate;
    private int unitPrice;
    private String category;
    
    public Flower(){
    }

    public Flower(String id, String description, LocalDate importDate, int unitPrice, String category) {
        this.id = id;
        this.description = description;
        this.importDate = importDate;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s",this.getId(), this.getDescription(),this.getImportDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),this.getUnitPrice(), this.getCategory());
    }

    @Override
    public int compareTo(Flower t) {
        return this.id.compareTo(t.getId());
    }
}

