package experiments;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CopyProperties {
    public static <S, T>  void copyProperties(S source, T target){
        try {
            if (source == null || target == null){
                throw new RuntimeException("source or target cannot be null");
            }
            BeanInfo targetBeanInfo = Introspector.getBeanInfo(target.getClass());
            BeanInfo sourceBeanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor targetPds[] = targetBeanInfo.getPropertyDescriptors();
            PropertyDescriptor sourcePds[] = sourceBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor targetPd: targetPds){

                Method tPdWriteMethod = targetPd.getWriteMethod();
                if (tPdWriteMethod == null)
                    continue;

                PropertyDescriptor sourcePd = searchPropertyDescriptor(sourcePds, targetPd.getName());
                if (sourcePd == null)
                    continue;

                Method sPdReadMethod = sourcePd.getReadMethod();
                if (sPdReadMethod == null)
                    continue;

                if (targetPd.getPropertyType() != sourcePd.getPropertyType())
                    continue;

                Object sourceVal = sPdReadMethod.invoke(source);
                tPdWriteMethod.invoke(target, sourceVal);
            }
        } catch (IntrospectionException e){
            System.err.printf("Introspection error: %s", e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e2){
            System.err.printf("Introspection error: %s", e2.getMessage());
        }
    }

    private static PropertyDescriptor searchPropertyDescriptor(PropertyDescriptor pds[], String pdName){
        for (PropertyDescriptor pd: pds){
            if (pd.getName().equals(pdName)){
                return pd;
            }
        }
        return null;
    }
}
