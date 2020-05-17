public class FlowchartTest {
    /*
     public List<MethodNodeGroup> parseFile() {
        List<String> lines = readFile();
        addAllMethodNames(lines);

        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if(isLineValid(line)) {
                if(line.equals("}")) {
                    boolean isLastLine = i != lines.size() - 1;
                    if(!isLastLine) {
                        String nextLine = lines.get(i + 1);
                        handleEndBracket(nextLine);
                    }
                }
                else {
                    addNode(line);
                }
            }
        }
        return methodAbstractNodes;
    }

    private void handleEndBracket(String nextLine) {
        if(openedNodeGroups.peek() instanceof TwoBranchNodeGroup) {
            TwoBranchNodeGroup twoBranchNodeGroup = (TwoBranchNodeGroup) openedNodeGroups.peek();
            if(isBracketClosingBranch(nextLine) && twoBranchNodeGroup.isFirstBranch()) {
                twoBranchNodeGroup.setIsFirstBranch(false);
            }
            else {
                closeNodeGroup();
            }
        }
        else {
            closeNodeGroup();
        }
    }

    private void addNode(String line) {
        Node node = lineParser.nextNode(line);
        if(node instanceof NodeGroup) {
            if(!openedNodeGroups.isEmpty()) {
                openedNodeGroups.peek().addNode(node);
            }
            openedNodeGroups.push((NodeGroup) node);
        }
        else {
            openedNodeGroups.peek().addNode(node);
        }
    }

    private List<String> readLines() throws IOException {
        FileReader fileReader = null;
        List<String> lines = new LinkedList<>();
        try {
            fileReader = new FileReader(file);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fileReader != null) {
            BufferedReader reader = new BufferedReader(fileReader);
            String s;
            while((s = reader.readLine()) != null) {
                lines.add(s);
            }
        }
        return lines;
    }

    private List<String> readFile() {
        List<String> lines = Collections.emptyList();
        try {
            lines = readLines();
        }
        catch(IOException e) {
            System.err.println("Cannot read from file: " + file.toString());
            e.printStackTrace();
        }

        return lines;
    }

    private void addAllMethodNames(List<String> lines) {
        for(String line : lines) {
            line = line.trim();
            if(isLineValid(line) && !line.contains("}")) {
                Node node = lineParser.nextNode(line);
                if(node instanceof MethodNodeGroup) {
                    lineParser.addMethodName(node.getText());
                }
            }
        }
    }

    private boolean isBracketClosingBranch(String nextLine) {
        return (nextLine.contains("else ") || nextLine.contains("catch"));
    }

    private void closeNodeGroup() {
        NodeGroup endedNodeGroup = openedNodeGroups.pop();
        if(openedNodeGroups.isEmpty()) {
            methodAbstractNodes.add((MethodNodeGroup) endedNodeGroup);
        }
    }

    private boolean isLineValid(String line) {

    }

    private static void testIfElseIf(int a, int b) {
        int result = 0;
        if(a > b) {
            result = 1;
        }
        else {
            if(a < b) {
                result = -1;
            }
            else {
                result = 0;
            }
        }
        return a;
    }

    private static void testWhile() {
        int a  = 123;
        while(a == 123) {
            String s = "abc";
            int b = 234;
        }
        int c = 345;
    }

    private static final void testFor() {
        int a  = 123;
        for(int i = 0; i < 10; i ++) {
            String s = "abc";
            if(i < 5) {
                System.out.println("Less than five");
            }
            else {
                System.out.println("Greater than five");
            }
        }
        int b = 234;
    }

    private static void testForEach() {
        String s = "abc";
        for(char c: s.toCharArray()) {
            c += 15;
            System.out.println(c);
        }
        int a = 123;
    }

    private static void testLongMethod() {
        int a = 123;
        if(a == 123) {
            for(int i = 0; i < a; i++) {
                if(i % 2 == 0) {
                    System.out.println(i);
                }
            }
        }
        if(condition1) {
            try {
                suspiciousOperation();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        while(a == 123) {
            System.out.println("Inside loop");
            a--;
        }
        if(condition2) {
            if(condition3) {
                operation1();
                operation2();
                operation3();
            }
        }
        else {
            if(condition4) {
                operation5();
            }
        }
    }

    private static void testLongBlock() {
        String aVeryLongIndentificator = "A very long string";

    }

    private static int testReturn() {
        String a = "abc";
        if(true) {
            a = "bcd";
        }
        else {
            a = "123";
        }
        return a;
    }

    private static void testIfElseIf() {
        int a = 123;
        if(condition1) {
            a = 234;
        }
        else {
            if(condition2) {
                a =  345;
            }
            else {
                a = 456;
            }
        }
        String b = "abc";
    }

    private static void testTry() {
        int a = 123;
        try{
            suspiciousOperation();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка, повторите ввод");
        }
        int b = 321;
    }

    private static void testIfElseIf() {
        if(condition1) {
            int a = 123;
        }
        else {
            if(condition2) {
                int b = 321;
            }
        }
    }

    private static void testTryInIf() {
        int a = 123;
        if(condition) {
            int c = 321;
            try {
                suspiciousOperation();
            }
            catch(IOException e) {
                e.printStackTrace();
                System.out.println("Произошла ошибка, повторите ввод");
            }
        }
        else {
            int e = 543;
        }
        int b = 321;
    }
     */
}
