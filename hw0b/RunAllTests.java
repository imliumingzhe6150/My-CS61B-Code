import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;
import org.junit.platform.launcher.listeners.*;
import org.junit.platform.engine.discovery.*;

public class RunAllTests {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                DiscoverySelectors.selectClass("DessertTest"),
                DiscoverySelectors.selectClass("JavaExercisesTest"),
                DiscoverySelectors.selectClass("ListExercisesTest"),
                DiscoverySelectors.selectClass("MapExercisesTest")
            )
            .build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        System.out.println("=== Test Results ===");
        System.out.println("Tests found: " + summary.getTestsFoundCount());
        System.out.println("Tests succeeded: " + summary.getTestsSucceededCount());
        System.out.println("Tests failed: " + summary.getTestsFailedCount());

        if (summary.getTestsFailedCount() > 0) {
            summary.getFailures().forEach(failure -> {
                System.out.println("FAILED: " + failure.getTestIdentifier().getDisplayName());
                failure.getException().printStackTrace();
            });
            System.exit(1);
        } else {
            System.out.println("ALL TESTS PASSED!");
        }
    }
}
