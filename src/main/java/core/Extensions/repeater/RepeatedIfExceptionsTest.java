package core.Extensions.repeater;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(RepeaterCondition.class)
public @interface RepeatedIfExceptionsTest {

    String DISPLAY_NAME_PLACEHOLDER = "{displayName}";
    String TAG_PLACEHOLDER = "{tag}";
    String CURRENT_REPETITION_PLACEHOLDER = "{currentRepetition}";
    String TOTAL_REPETITIONS_PLACEHOLDER = "{totalRepetitions}";
    String SHORT_DISPLAY_NAME = DISPLAY_NAME_PLACEHOLDER + " #Repetition №: " + CURRENT_REPETITION_PLACEHOLDER + " of " + TOTAL_REPETITIONS_PLACEHOLDER;


    Class<? extends Exception>[] exceptions() default Exception.class;

    int repeats();
    String name() default SHORT_DISPLAY_NAME;
}