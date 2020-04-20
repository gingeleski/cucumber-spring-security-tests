package junit.security.annotations;

import javax.annotation.security.PermitAll;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestUtilities
{
    /**
     * @param clazz Class containing the method whose annotation to test
     * @param methodName Method containing the annotation to test
     * @param parameterTypes Parameters to the method. All parameters must be passed to understand which method we're looking for, since methods can be overloaded
     * @return PreAuthorize is present with isAuthenticated()
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static boolean evaluatePreAuthorizeIsAuthenticated(Class<?> clazz, String methodName, Class<?>... parameterTypes)
                                            throws NoSuchMethodException, SecurityException, IllegalAccessException,
                                                   IllegalArgumentException, InvocationTargetException
    {
        // Iterate through all annotations on the class.method passed in
        for (Annotation annotation : clazz.getMethod(methodName, parameterTypes).getAnnotations())
        {
            Class<? extends Annotation> annotationType = annotation.annotationType();

            // If the annotation we found is of the type we're looking for (PreAuthorize, in this case)
            if(annotationType.equals(org.springframework.security.access.prepost.PreAuthorize.class))
            {
                // Use reflection to see the method invoked by this annotation - expecting isAuthenticated
                for(Method method: annotationType.getDeclaredMethods())
                {
                    Object value = method.invoke(annotation,  (Object[]) null);
                    String methodNameOutput = value.toString();

                    // Confirm that the method called by @PreAuthorize = isAuthenticated
                    return methodNameOutput.contains("isAuthenticated");
                }
            }
        }

        return false;
    }

    /**
     * @param clazz Class containing the method whose annotation to test
     * @param methodName Method containing the annotation to test
     * @param parameterTypes Parameters to the method. All parameters must be passed to understand which method we're looking for, since methods can be overloaded
     * @return PermitAll is present
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static boolean evaluatePermitAll(Class<?> clazz, String methodName, Class<?>... parameterTypes)
                                                        throws NoSuchMethodException, SecurityException,
                                                               IllegalAccessException, IllegalArgumentException,
                                                               InvocationTargetException
    {
        // Iterate through all annotations on the class.method passed in
        for (Annotation annotation : clazz.getMethod(methodName, parameterTypes).getAnnotations())
        {
            Class<? extends Annotation> annotationType = annotation.annotationType();

            // If the annotation we found is of the type we're looking for (PermitAll, in this case)
            if(annotationType.equals(PermitAll.class))
            {
                return true;
            }
        }

        return false;
    }
}
