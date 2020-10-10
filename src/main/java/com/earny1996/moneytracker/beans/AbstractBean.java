package com.earny1996.moneytracker.beans;

import java.time.LocalDate;

public abstract class AbstractBean {

    public Long generateId(){
        Long systemMillis = System.currentTimeMillis();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append(month);
        stringBuilder.append(day);
        stringBuilder.append(systemMillis);

        String idValue = stringBuilder.toString();
        if(idValue.length() > 19){
            idValue = idValue.substring(0,19);
        }

        return Long.valueOf(idValue);
    }
    
}
