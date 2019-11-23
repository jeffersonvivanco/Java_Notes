package models;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PersonBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor p1 = new PropertyDescriptor("name", Person.class);
            PropertyDescriptor p2 = new PropertyDescriptor("age", Person.class);
            PropertyDescriptor descriptors[] = {p1, p2};
            return descriptors;
        } catch (IntrospectionException e){
            System.out.println("Error when creating property descriptor");
        }
        return null;
    }
}
