package core.Extensions.repeater;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.Preconditions;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Stream.of;
import static java.util.stream.StreamSupport.stream;
import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;
import static org.junit.platform.commons.util.AnnotationUtils.isAnnotated;

@Slf4j
public class RepeaterCondition implements TestTemplateInvocationContextProvider, AfterTestExecutionCallback {

    private boolean exceptionAppear = false;
    private int totalRepeats = 0;
    private RepeatedIfExceptionsDisplayNameFormatter formatter;
    static List<Boolean> historyExceptionAppear = Collections.synchronizedList(new ArrayList<>());

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return isAnnotated(extensionContext.getTestMethod(), RepeatedIfExceptionsTest.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        Preconditions.notNull(extensionContext.getTestMethod().orElse(null), "Test method must not be null");

        RepeatedIfExceptionsTest annotationParams = extensionContext.getTestMethod()
                .flatMap(testMethods -> findAnnotation(testMethods, RepeatedIfExceptionsTest.class))
                .orElseThrow(() -> new RepeatedIfException("The extension should not be executed "
                        + "unless the test method is annotated with @RepeatedIfExceptionsTest."));

        totalRepeats = annotationParams.repeats();
        Preconditions.condition(totalRepeats > 0, "Total repeats must be higher than 0");


        String displayName = extensionContext.getDisplayName();
        String tag = extensionContext.getTags().iterator().next();
        formatter = displayNameFormatter(annotationParams, displayName, tag);

        Spliterator<TestTemplateInvocationContext> spliterator =
                spliteratorUnknownSize(new TestTemplateIterator(), Spliterator.NONNULL);
        return stream(spliterator, false);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Class<? extends Exception>[] exceptionPool = extensionContext.getTestMethod()
                .flatMap(testMethods -> findAnnotation(testMethods, RepeatedIfExceptionsTest.class))
                .orElseThrow(() -> new IllegalStateException("The extension should not be executed "))
                .exceptions();
        log.debug("Exceptions Pool in RepeatedIfExceptionsTest '{}'", exceptionPool);

        Class<? extends Throwable> exception = extensionContext.getExecutionException()
                .orElse(new RepeatedIfException("There is no exception in context")).getClass();
        log.debug("Exception in test '{}'", exception);
        boolean result = of(exceptionPool)
                .anyMatch(ex -> ex.isAssignableFrom(exception) && !RepeatedIfException.class.isAssignableFrom(exception));
        historyExceptionAppear.add(result);
        exceptionAppear = exceptionAppear || result;
    }

    private RepeatedIfExceptionsDisplayNameFormatter displayNameFormatter(RepeatedIfExceptionsTest test, String displayName, String tag) {
        String pattern = test.name().trim();
        return new RepeatedIfExceptionsDisplayNameFormatter(pattern, displayName, tag);
    }

    class TestTemplateIterator implements Iterator<TestTemplateInvocationContext> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex == 0 || exceptionAppear && currentIndex < totalRepeats;
        }

        @Override
        public TestTemplateInvocationContext next() {
            if (hasNext()) {
                exceptionAppear = false;
                currentIndex++;
                return new RepeatedIfExceptionsInvocationContext(currentIndex, totalRepeats, formatter);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}