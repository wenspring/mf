package meifeng.mobile.kevin.com.meifeng.ext.bindview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kevin.w on 2018/4/13.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface BindView {

    int id();
    
    boolean click() default false;

}
