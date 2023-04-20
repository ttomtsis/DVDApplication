
package com.retail.dvdapplication;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.hateoas.Link;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
* Contains Runtime Hints required by GraalVM in order to
* function as a native image. Imported at main class.
* */
public class DvdApplicationHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        /* This is the only required hint and is due to the following exception
        * that occured on the get endpoints that returned a list of domain objects:
        * com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
        *  No serializer found for class org.springframework.hateoas.mediatype.hal.forms.HalFormsAffordanceModel
        *  and no properties discovered to create BeanSerializer */
        for (Constructor<?> constructor : Link.class.getDeclaredConstructors()) {
            hints.reflection().registerConstructor(constructor, ExecutableMode.INVOKE);
        }
        for (Method method : Link.class.getDeclaredMethods()) {
            hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
        }
        for (Field field : Link.class.getDeclaredFields()) {
            hints.reflection().registerField(field);
        }
    }

}

