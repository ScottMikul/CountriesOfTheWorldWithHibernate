package com.teamtreehouse.countrydata.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by scott on 5/3/2017.
 */
@Entity
public class Country {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Id
    private String code;

    @Column
    private String name;
    @Column
    private BigDecimal internetUsers;
    @Column
    private BigDecimal adultLiteracyRate;

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", internetUsers=" + internetUsers +
                ", adultLiteracyRate=" + adultLiteracyRate +
                '}';
    }

    public Country(){}
    public Country(String name, BigDecimal internetUsers, BigDecimal adultLiteracyRate) {
        this.name = name;
        this.internetUsers = internetUsers;
        this.adultLiteracyRate = adultLiteracyRate;
    }

    public Country(CountryBuilder builder){
        this.code = builder.code;
        this.name= builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(BigDecimal internetUsers) {
        this.internetUsers = internetUsers;
    }

    public BigDecimal getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(BigDecimal adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    public void setCountry(Country newCountry) {
        this.name = newCountry.name;
        this.internetUsers = newCountry.internetUsers;
        this.adultLiteracyRate = newCountry.adultLiteracyRate;
    }

    public static class CountryBuilder{
        private String code;
        private String name;
        private BigDecimal internetUsers;
        private BigDecimal adultLiteracyRate;

        public CountryBuilder(){}

        public CountryBuilder withName(String name){
            this.name = name;
            return this;
        }
        public CountryBuilder withInternetUsers(BigDecimal internetUsers){
            this.internetUsers = internetUsers;
            return this;
        }
        public CountryBuilder withAdultLiteracyRate(BigDecimal adultLiteracyRate){
            this.adultLiteracyRate = adultLiteracyRate;
            return this;
        }

        public CountryBuilder withCode(String code){
            this.code = code;
            return this;
        }
        public Country build(){
            return new Country(this);
        }

    }
}
