package com.earny1996.moneytracker.persistencecontext.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import javassist.bytecode.analysis.Analyzer;

public abstract class AbstractBean {

    public Long generateId() {
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
        if (idValue.length() > 19) {
            idValue = idValue.substring(0, 19);
        }

        return Long.valueOf(idValue);
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append(this.getClass().getSimpleName());
        toStringBuilder.append("={");

        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            boolean mappedByAnnotation = false;
            for(Annotation annotation : declaredAnnotations){
                
                if(annotation instanceof OneToMany){
                    mappedByAnnotation = true;
                    break;
                }
            }

            if(field.getName().equals("serialVersionUID")){
                continue;
            }

            if(!mappedByAnnotation){
                if(i > 0){
                    toStringBuilder.append(", ");
                }
                toStringBuilder.append(field.getName());
                toStringBuilder.append("=");
                try {
                    boolean accessSave = field.canAccess(this);
                    if(!accessSave){
                        field.setAccessible(true);
                    }
                    toStringBuilder.append(field.get(this));
                    field.setAccessible(accessSave);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.out.println("ERRRRRROOOOORRRR");
                    e.printStackTrace();
                }
                
                
            }
        }
        toStringBuilder.append("}");
        return toStringBuilder.toString();
    }
    
}
