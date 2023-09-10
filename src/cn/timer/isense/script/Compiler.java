package cn.timer.isense.script;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public class Compiler {
    private static final Map<Character, Class<?>> baseMap = new HashMap<>();

    static {
        baseMap.put('V', void.class);
        baseMap.put('S', short.class);
        baseMap.put('I', int.class);
        baseMap.put('J', long.class);
        baseMap.put('F', float.class);
        baseMap.put('D', double.class);
        baseMap.put('Z', boolean.class);
        baseMap.put('C', char.class);
        baseMap.put('B', byte.class);
    }

    private static Class<?> parseType(String name) throws ClassNotFoundException {
        if (name.equals("V")) return null;
        if (!name.contains("[") && name.length() == 1 && baseMap.containsKey(name.charAt(0)))
            return baseMap.get(name.charAt(0));
        if (name.contains("[")) {
            StringBuilder sb = new StringBuilder();
            for (char c : name.toCharArray()) {
                if (c == '[') sb.append('[');
            }
            boolean isBase = name.replace("[", "").length() == 1 && baseMap.containsKey(name.replace("[", "").charAt(0));
            if (!isBase) sb.append('L');
            sb.append(name.replace("[", "").replace("/", "."));
            if (!isBase) sb.append(';');
            return Class.forName(sb.toString());
        } else {
            return Class.forName(name.replace("/", "."));
        }
    }

    private static Class<?>[] parseTypes(String str) throws ClassNotFoundException {
        int argLength = str.contains(",") ? str.split(",").length : 1;
        Class<?>[] types = new Class<?>[argLength];
        if (argLength == 1 && !str.equals("V")) {
            types[0] = parseType(str);
            return types;
        }
        String[] typeNames = str.split(",");
        int i = 0;
        for (String typeName : typeNames) {
            Class<?> type = parseType(typeName);
            if (type != null) {
                types[i] = type;
                i++;
            }
        }
        return types;
    }

    private static Number getNumber(String str) {
        String str1 = str.replace("-", "");
        if (pattern.matcher(str1).matches()) {
            return Integer.parseInt(str);
        } else if (pattern.matcher(str1.replace("L", "")).matches()) {
            return Long.parseLong(str.replace("L", ""));
        } else if (pattern.matcher(str1.replace("f", "").replace("F", "").replace(".", "")).matches()) {
            return Float.parseFloat(str);
        } else if (pattern.matcher(str1.replace("d", "").replace("D", "").replace(".", "")).matches()) {
            return Double.parseDouble(str);
        }
        return null;
    }

    private static final Map<Class<?>, Integer> accuracyMap = new HashMap<>();

    static {
        accuracyMap.put(Integer.class, 0);
        accuracyMap.put(Long.class, 1);
        accuracyMap.put(Float.class, 2);
        accuracyMap.put(Double.class, 3);
    }

    interface Operation<T extends Number> {
        T ope(Number n1, Number n2);
    }

    private static <T extends Number> Number calcNumber(Number n1, Number n2, Operation<T> operation) {
        Class<?> type1 = getType(n1);
        Class<?> type2 = getType(n2);
        Class<?> type = accuracyMap.get(type1) >= accuracyMap.get(type2) ? type1 : type2;
        if (type == Integer.class) {
            return Integer.parseInt(String.valueOf(operation.ope(n1.intValue(), n2.intValue()).intValue()));
        } else if (type == Long.class) {
            return Long.parseLong(String.valueOf(operation.ope(n1.longValue(), n2.longValue()).longValue()));
        } else if (type == Float.class) {
            return Float.parseFloat(String.valueOf(operation.ope(n1.floatValue(), n2.floatValue()).floatValue()));
        } else if (type == Double.class) {
            return Double.parseDouble(String.valueOf(operation.ope(n1.doubleValue(), n2.doubleValue()).doubleValue()));
        }
        return null;
    }

    private static Class<?> getType(Number number) {
        Class<?> type = null;
        if (number instanceof Integer) {
            type = Integer.class;
        } else if (number instanceof Long) {
            type = Long.class;
        } else if (number instanceof Float) {
            type = Float.class;
        } else if (number instanceof Double) {
            type = Double.class;
        }
        return type;
    }

    private static Object parseObj(Map<String, Object> objectsPool, String str) {
        if (str.equals("null")) return null;
        if (str.equals("true")) return Boolean.TRUE;
        if (str.equals("false")) return Boolean.FALSE;
        if (str.contains("\"")) {
            str = str.substring(1);
            return str.substring(0, str.length() - 1);
        }
        if (str.contains("'") && str.length() == 3) return str.replace("'", "").charAt(0);
        Number number = getNumber(str);
        if (number != null) return number;
        return objectsPool.get(str);
    }

    private static Object[] parseArgs(Map<String, Object> objectsPool, String str) {
        int argLength = str.contains(",") ? str.split(",").length : 1;
        Object[] args = new Object[argLength];
        String[] argName = str.split(",");
        if (argLength == 1 && !str.equals("V")) {
            args[0] = parseObj(objectsPool, str);
            return args;
        }
        for (int i1 = 0; i1 < argName.length; i1++) {
            args[i1] = parseObj(objectsPool, argName[i1]);
        }
        return args;
    }


    private static final Pattern pattern = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");


    /*
     * var 创建变量 并赋值:
     * VAR value1 => 1;
     * VAR value2 => "666";
     *
     * new 实例化类 并储存变量
     * NEW instance : Main$Obj - java/lang/String <---------- "1919810";
     *     ^变量名     ^类名       ^构造函数的参数(没有则用'V'代替)  ^传参(没有则用'V'代替)
     *
     * visit field 访问动态字段 并储存变量:
     * VIF obj => instance : Main$Obj -> str;
     *    ^变量名 ^字段所属示例^实例所属类名  ^字段名
     *
     * visit static field 访问静态字段 并储存变量:
     * VIS out => java/lang/System -> out;
     *     ^变量名 ^类名                ^字段名
     *
     * invoke 调用动态方法:
     * INV out : java/io/PrintStream -> println - java/lang/String <- text;
     *        ^实例  ^实例所属类              ^方法名    ^参数(没有则用'V'代替) ^传参(没有则用'V'代替)
     *
     * invoke static 调用静态方法:
     * INS Main -> main - I,java/lang/String <- value1,value2;
     *              ^类名    ^方法名 ^参数(没有则用'V'代替)   ^传参(没有则用'V'代替)
     *
     * del 删除变量(即赋值空指针)
     * DEL out;
     *     ^变量名
     */

    public static final String space =
            "弯 輊?K                 ?H堽?x蒧F 鋔? 轷r     java/lang/System     ??8笀?JI笺                   垡??`豩€\\?M\\";
    public static final String splitter =
            "?婡x媝?婡                    t媥?婡p媂?婡l婡塃    java/lang/Boolean ??? java/lang/String           ?婡h婡塃鼖婡d夽圗鴭婡`婡塃魦婡" +
            "\\禓塃饗婡X禓塃鞁婡                T婡塃鑻婡P婡                          塃鋴婡L婡塃鄫婡H婡塃軏" +
            "婡D婡塃貗                 婡@婡塃詪婡<婡";
    public static final char RLO = '\u202E';
    private static String stackIn = "Main";
    private static String blockIn = null;

    /**
     * @param stacks      堆栈
     * @param source      源码
     * @param objectsPool 对象池
     * @throws ClassNotFoundException Exception
     * @throws NoSuchFieldException   Exception
     * @throws NoSuchMethodException  Exception
     */
    @SuppressWarnings("DuplicatedCode")
    public static void compile(CopyOnWriteArrayList<Runnable> stacks, Map<String, CopyOnWriteArrayList<Runnable>> codeBlocks, String source, Map<String, Object> objectsPool, boolean debug) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        long lineNum = 0;
        for (String line : Util.split(source, debug ? ";" : splitter)) {
            lineNum++;
            Iterator<String> iterator = Arrays.asList(Util.split(line, debug ? " " : space)).iterator();
            while (iterator.hasNext()) {
                String keyword = iterator.next();
                switch (keyword) {
                    case "INS": {//invoke static 调用静态方法
                        String className = iterator.next();
                        iterator.next();
                        String methodName = iterator.next();
                        iterator.next();
                        String typeNames = iterator.next();
                        iterator.next();
                        String argNames = iterator.next();

                        Class<?> clazz = Class.forName(className.replace("/", "."));
                        Class<?>[] types = parseTypes(typeNames);
                        Method method = typeNames.equals("V") ? clazz.getDeclaredMethod(methodName) : clazz.getDeclaredMethod(methodName, types);
                        method.setAccessible(true);

                        long finalLineNum = lineNum;
                        Runnable stack = () -> {
                            try {
                                if (!typeNames.equals("V")) {
                                    objectsPool.put("_result_", method.invoke(null, parseArgs(objectsPool, argNames)));
                                } else objectsPool.put("_result_", method.invoke(null));
                            } catch (Exception e) {
                                System.err.println("line: " + finalLineNum + ":" + line.replace(RLO, '-').replace(space, " ").replace(splitter, ";"));
                                e.printStackTrace();
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "INV": {//invoke 调用动态方法
                        String objName = iterator.next();
                        iterator.next();
                        String className = iterator.next();
                        iterator.next();
                        String methodName = iterator.next();
                        iterator.next();
                        String typeNames = iterator.next();
                        iterator.next();
                        String argNames = iterator.next();

                        Class<?> clazz = Class.forName(className.replace("/", "."));
                        Class<?>[] types = parseTypes(typeNames);
                        Method method = typeNames.equals("V") ? clazz.getDeclaredMethod(methodName) : clazz.getDeclaredMethod(methodName, types);
                        method.setAccessible(true);

                        long finalLineNum = lineNum;
                        Runnable stack = () -> {
                            try {
                                Object object = objectsPool.get(objName);
                                if (!typeNames.equals("V")) {
                                    objectsPool.put("_result_", method.invoke(object, parseArgs(objectsPool, argNames)));
                                } else objectsPool.put("_result_", method.invoke(object));
                            } catch (Exception e) {
                                System.err.println("line: " + finalLineNum + ":" + line.replace(RLO, '-').replace(space, " ").replace(splitter, ";"));
                                e.printStackTrace();
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "VAR": {//var 创建变量 并赋值
                        String name = iterator.next();
                        iterator.next();
                        String objName = iterator.next();

                        Runnable stack = () -> objectsPool.put(name, parseObj(objectsPool, objName));
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "NEW": {//new 实例化类 并储存变量
                        String name = iterator.next();
                        iterator.next();
                        String className = iterator.next();
                        iterator.next();
                        String typeNames = iterator.next();
                        iterator.next();
                        String argNames = iterator.next();

                        Class<?> clazz = Class.forName(className.replace("/", "."));
                        Class<?>[] types = parseTypes(typeNames);
                        Constructor<?> constructor = typeNames.equals("V") ? clazz.getDeclaredConstructor() : clazz.getDeclaredConstructor(types);
                        constructor.setAccessible(true);

                        long finalLineNum = lineNum;
                        Runnable stack = () -> {
                            try {
                                objectsPool.put(name, typeNames.equals("V") ? constructor.newInstance() : constructor.newInstance(parseArgs(objectsPool, argNames)));
                            } catch (Exception e) {
                                System.err.println("line: " + finalLineNum + ":" + line.replace(RLO, '-').replace(space, " ").replace(splitter, ";"));
                                e.printStackTrace();
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "VIS": {//visit static field 访问静态字段 并储存变量
                        String name = iterator.next();
                        iterator.next();
                        String className = iterator.next();
                        iterator.next();
                        String fieldName = iterator.next();

                        Class<?> clazz = Class.forName(className.replace("/", "."));
                        Field field = clazz.getDeclaredField(fieldName);

                        long finalLineNum = lineNum;
                        Runnable stack = () -> {
                            try {
                                objectsPool.put(name, field.get(null));
                            } catch (Exception e) {
                                System.err.println("line: " + finalLineNum + ":" + line.replace(RLO, '-').replace(space, " ").replace(splitter, ";"));
                                e.printStackTrace();
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "VIF": {//visit field 访问动态字段 并储存变量
                        String name = iterator.next();
                        iterator.next();
                        String instanceName = iterator.next();
                        iterator.next();
                        String className = iterator.next();
                        iterator.next();
                        String fieldName = iterator.next();

                        Class<?> clazz = Class.forName(className.replace("/", "."));
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);

                        long finalLineNum = lineNum;
                        Runnable stack = () -> {
                            try {
                                objectsPool.put(name, field.get(objectsPool.get(instanceName)));
                            } catch (Exception e) {
                                System.err.println("line: " + finalLineNum + ":" + line.replace(RLO, '-').replace(space, " ").replace(splitter, ";"));
                                e.printStackTrace();
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "DEL": {//del 删除变量
                        String name = iterator.next();

                        Runnable stack = () -> objectsPool.put(name, null);
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "CAL": {//CAL var => 1 + 1;
                        String name = iterator.next();
                        iterator.next();
                        String obj1 = iterator.next();
                        String ope = iterator.next();
                        String obj2 = iterator.next();

                        Runnable stack = () -> {
                            Object object1 = parseObj(objectsPool, obj1),
                                    object2 = parseObj(objectsPool, obj2);
                            assert object1 != null;
                            assert object2 != null;
                            Number number1 = getNumber(object1.toString()),
                                    number2 = getNumber(object2.toString());
                            if (object1 instanceof Number && object2 instanceof Number) {
                                switch (ope) {
                                    case "+": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.doubleValue() + n2.doubleValue())));
                                        break;
                                    }
                                    case "-": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.doubleValue() - n2.doubleValue())));
                                        break;
                                    }
                                    case "*": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.doubleValue() * n2.doubleValue())));
                                        break;
                                    }
                                    case "/": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.doubleValue() / n2.doubleValue())));
                                        break;
                                    }
                                    case "&": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.intValue() & n2.intValue())));
                                        break;
                                    }
                                    case "|": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.intValue() | n2.intValue())));
                                        break;
                                    }
                                    case "^": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.intValue() ^ n2.intValue())));
                                        break;
                                    }
                                    case "<<": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.intValue() << n2.intValue())));
                                        break;
                                    }
                                    case ">>": {
                                        objectsPool.put(name, calcNumber(number1, number2, ((n1, n2) -> n1.intValue() >> n2.intValue())));
                                        break;
                                    }
                                }
                            } else if (object1 instanceof String && object2 instanceof String && ope.equals("+")) {
                                objectsPool.put(name, object1 + ((String) object2));
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "BLO": {//start block
                        String blockName = iterator.next();

                        blockIn = blockName;
                        codeBlocks.put(blockName, new CopyOnWriteArrayList<>());
                        stackIn = "Block";
                        break;
                    }
                    case "END": {//end block
                        String blockName = iterator.next();

                        stackIn = "Main";
                        if (blockName.equals(blockIn)) blockIn = null;
                        break;
                    }
                    case "GTO": {//goto
                        String blockName = iterator.next();

                        Runnable stack = () -> {
                            if (codeBlocks.get(blockName) != null) codeBlocks.get(blockName).forEach(Runnable::run);
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "IFO": {//goto
                        String flag = iterator.next();
                        iterator.next();
                        String blockName = iterator.next();

                        Runnable stack = () -> {
                            Object flagObj = parseObj(objectsPool, flag);
                            if (flagObj instanceof Boolean && ((Boolean) flagObj))
                                if (codeBlocks.get(blockName) != null) {
                                    codeBlocks.get(blockName).forEach(Runnable::run);
                                }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "FLA": {
                        String name = iterator.next();
                        iterator.next();
                        String obj1 = iterator.next();
                        String ope = iterator.next();
                        String obj2 = iterator.next();

                        Runnable stack = () -> {
                            boolean flag = false;
                            Object o1 = parseObj(objectsPool, obj1);
                            Object o2 = parseObj(objectsPool, obj2);
                            if (o1 instanceof Number && o2 instanceof Number) {
                                Number number1 = getNumber(o1.toString());
                                Number number2 = getNumber(o2.toString());
                                if (number1 != null && number2 != null) switch (ope) {
                                    case ">": {
                                        flag = number1.doubleValue() > number2.doubleValue();
                                        break;
                                    }
                                    case "<": {
                                        flag = number1.doubleValue() < number2.doubleValue();
                                        break;
                                    }
                                    case ">=": {
                                        flag = number1.doubleValue() >= number2.doubleValue();
                                        break;
                                    }
                                    case "<=": {
                                        flag = number1.doubleValue() <= number2.doubleValue();
                                        break;
                                    }
                                    case "==": {
                                        flag = number1.doubleValue() == number2.doubleValue();
                                        break;
                                    }
                                    case "!=": {
                                        flag = number1.doubleValue() != number2.doubleValue();
                                        break;
                                    }
                                }
                            } else if (ope.equals("==")) {
                                if (o1 == o2) {
                                    flag = true;
                                } else if (o1 != null && o1.equals(o2)) {
                                    flag = true;
                                }
                            } else if (ope.equals("!=")) {
                                if (o1 != null) {
                                    if (o1 != o2 && !o1.equals(o2)) flag = true;
                                } else if (o2 != null) flag = true;
                            }
                            objectsPool.put(name, flag ? Boolean.TRUE : Boolean.FALSE);
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                    case "WHO": {
                        String flag = iterator.next();
                        iterator.next();
                        String blockName = iterator.next();

                        Runnable stack = () -> {
                            CopyOnWriteArrayList<Runnable> block = codeBlocks.get(blockName);
                            if (block != null) {
                                Object flagObj = parseObj(objectsPool, flag);
                                while (flagObj instanceof Boolean && ((Boolean) flagObj)) {
                                    block.forEach(Runnable::run);
                                    flagObj = parseObj(objectsPool, flag);
                                }
                            }
                        };
                        if (stackIn.equals("Main")) stacks.add(stack);
                        else codeBlocks.get(blockIn).add(stack);
                        break;
                    }
                }
            }
        }
    }
}
