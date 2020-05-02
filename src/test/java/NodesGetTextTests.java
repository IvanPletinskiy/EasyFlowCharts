import com.handen.easyFlowCharts.Nodes.MethodCallNode;
import com.handen.easyFlowCharts.Nodes.StatementNode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NodesGetTextTests {

    @Test
    public void testStatement() {
        String line = "String line = reader.readLine().trim();";

        StatementNode node = new StatementNode(line);
        String result = node.getText();

        String correct = "line = reader.readLine().trim()";
        Assert.assertEquals(result, correct);
    }

    @Test
    public void testMethodCall() {
        String line = "readFromKeyboard();";

        MethodCallNode node = new MethodCallNode(line);
        String result = node.getText();

        String correct = "readFromKeyboard()";
        Assert.assertEquals(result, correct);
    }
}