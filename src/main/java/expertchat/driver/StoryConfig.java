package expertchat.driver;

// Created by Kishor on 1/24/2017.

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.bdd.E2ETestCase;
import expertchat.util.ExpertChatUtility;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Before;
import java.util.List;

public class StoryConfig extends JUnitStories {

        private ExtentReports extent;

        @Before
        public void setReports() {

            extent = new ExtentReports(ExpertChatUtility.getValue("report")
                    + "/"+ExpertChatUtility.directoryName() + "/"
                    + "Report.html",
                    false);
        }

        public ExtentReports getReport() {

            return extent;
        }

        @Override
        public Embedder configuredEmbedder() {

            final EmbedderControls embedderControls = new EmbedderControls();
            embedderControls.ignoreFailureInStories();
            embedderControls.doVerboseFailures(false);
            embedderControls.ignoreFailureInView();
            embedderControls.useThreads(1);

            final Embedder embedder = new Embedder();
            final Configuration configuration = configuration();
            embedder.useConfiguration(configuration);
            embedder.useStepsFactory(stepsFactory());

            return embedder;
        }


        @Override
        public Configuration configuration() {

            return new MostUsefulConfiguration()
                    .useStoryLoader(new LoadFromClasspath(this.getClass()));
        }

        @Override
        protected List<String> storyPaths() {

            List<String> s = new StoryFinder().
                    findPaths(CodeLocations.codeLocationFromClass(this.getClass()),
                            "**/*.story", null);
            return s;
        }


        @Override
        public InjectableStepsFactory stepsFactory() {

            return new InstanceStepsFactory(configuration(),

                    new E2ETestCase(getReport(), " a complete E2E scenario")
//                    new Social(getReport(),"Social Links")

            );
        }
    }
