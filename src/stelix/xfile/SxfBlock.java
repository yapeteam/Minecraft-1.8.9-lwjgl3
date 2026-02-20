package stelix.xfile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SxfBlock implements ISxfObject {

    private LinkedHashMap<String, Object> variables = new LinkedHashMap<>();
    private List<ISxfObject> unmanagedObjects = new ArrayList<>();


    public LinkedHashMap<String, Object> variables() {
        return variables;
    }

    public <X extends SxfStruct> X unmanagedAsStruct(int index) {
        return (X) unmanagedObjects.get(index);
    }

    public <X extends SxfBlock> X unmanagedAsBlock(int index) {
        return (X) unmanagedObjects.get(index);
    }

    public <X> X variable(String name) {
        return (X) variables.getOrDefault(name, null);
    }

    public <X extends SxfBlock> X variableAsBlock(String name) {
        return variable(name);
    }

    public <X extends SxfStruct> X variableAsStruct(String name) {
        return variable(name);
    }

    public List<ISxfObject> unmanagedObjects() {
        return unmanagedObjects;
    }
}
