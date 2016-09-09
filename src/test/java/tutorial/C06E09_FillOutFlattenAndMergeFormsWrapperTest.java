package tutorial;

import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.RunnerSearchConfig;
import com.itextpdf.test.WrappedSamplesRunner;
import com.itextpdf.test.annotations.type.SampleTest;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.Parameterized;

/**
 * C06E09_FillOutFlattenAndMergeForms sample has irregular DEST files: it has two of them,
 * so we need process it in a specific way.
 */
@Category(SampleTest.class)
public class C06E09_FillOutFlattenAndMergeFormsWrapperTest extends WrappedSamplesRunner {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        RunnerSearchConfig searchConfig = new RunnerSearchConfig();
        searchConfig.addClassToRunnerSearchPath("tutorial.chapter06.C06E09_FillOutFlattenAndMergeForms");
        return generateTestsList(searchConfig);
    }

    @Test(timeout = 60000)
    public void test() throws Exception {
        runSamples();
    }

    @Override
    protected void comparePdf(String outPath, String dest, String cmp) throws Exception {
        CompareTool compareTool = new CompareTool();
        addError(compareTool.compareByContent(dest, cmp, outPath, "diff_"));
        addError(compareTool.compareDocumentInfo(dest, cmp));

        // only DEST1 pdf will be checked, check additionally DEST2
        dest = getStringField(sampleClass, "DEST2");
        cmp = getCmpPdf(dest);

        addError(compareTool.compareByContent(dest, cmp, outPath, "diff_"));
        addError(compareTool.compareDocumentInfo(dest, cmp));
    }

    @Override
    protected String getDest() {
        return getStringField(sampleClass, "DEST1");
    }
}
