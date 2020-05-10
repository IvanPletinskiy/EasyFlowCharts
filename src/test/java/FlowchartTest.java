public class FlowchartTest {
/*
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
            try{
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

    private static void testIf() {
        if(condition) {
            int a = 123;
        }
        else {
            int b = 321;
        }
    }

    private static void testIfLeft() {
        if(condition) {
            int a = 123;
        }
        else {
            int b = 321;
            String c = "abc";
        }
    }

    private static void testIfRight() {
        if(condition) {
            int a = 123;
            String c = "abc";
        }
        else {
            int b = 321;
        }
    }

    private static void testIfNoElse() {
        int a = 123;
        if(condition) {
            doSmth();
        }
        int b = 321;
    }

    private static void testInnerIf() {
        if(condition) {
            int a = 123;
            if(condition2) {
                int b = 321;
            }
        }
        else {
            int c = 432;
        }
        int e = 543;
    }

    private static void readInput() {
        boolean isReaded = false;
        while(!isReaded) {
            System.out.println("Выберите способ вводить данные. 0 - ввод с клавиатуры, 1 - ввод с файла \"input.txt\"");
            String line = reader.readLine().trim();
            if(Integer.parseInt(line) == 0) {
                try {
                    readFromKeyboard();
                }
                catch(IOException e) {
                    e.printStackTrace();
                    System.out.println("Произошла ошибка, повторите ввод");
                }
            }
            else {
                if(Integer.parseInt(line) == 1) {
                    try {
                        readFromFile();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                        System.out.println("Произошла ошибка, повторите ввод");
                    }
                }
            }
            isReaded = checkIsInputCorrect();
        }
    }

    private static boolean checkIsInputCorrect() {
        boolean isCorrect =  !input.isEmpty();
        for(char c : input.toCharArray()) {
            if(c != ' ' && !Character.isDigit(c)) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    private static void solve() {
        int count = 0;
        boolean isCorrect = true;
        char[] chars = input.toCharArray();
        for(char c : chars) {
            if(c != '(') {
                if(c == ')') {
                    count--;
                }
            }
            else {
                count++;
            }
            if(count < 0) {
                isCorrect = false;
            }
        }
        if(isCorrect && count == 0) {
            result = "Сбалансированная \nпоследовательность";
        }
        else {
            result = "Несбалансированная последовательность";
        }
    }

    private static void printResult() {
        System.out.println(result);
    }

    private static void readFromFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("C:/Projects/Block3/src/com/handen/input.txt"));
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream));
        input = fileReader.readLine();
    }

    private static void readFromKeyboard() throws IOException {
        System.out.println("Введите последовательность в одну строку:");
        input = reader.readLine();
    }
     */
}
