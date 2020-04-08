package com.rikotsev.fin.grform.bus.bean;

/**
 * POJO
 *
 * References the properties of a row in the <code>STOCK_EXCHANGE</code> table
 */
public class StockExchange implements Bean {

    private int id;
    private String name;
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}